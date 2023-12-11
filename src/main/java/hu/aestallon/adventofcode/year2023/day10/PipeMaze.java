/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.adventofcode.year2023.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

final class PipeMaze {

  record Coordinate(int row, int col) {
    static Coordinate of(int row, int col) {
      return new Coordinate(row, col);
    }

    boolean outOfBounds(int rows, int cols) {
      return !(0 <= row && rows > row && 0 <= col && cols > col);
    }
  }

  private enum Symbol {
    NS('|', c -> Coordinate.of(c.row - 1, c.col), c -> Coordinate.of(c.row + 1, c.col)),
    WE('-', c -> Coordinate.of(c.row, c.col - 1), c -> Coordinate.of(c.row, c.col + 1)),
    NE('L', c -> Coordinate.of(c.row - 1, c.col), c -> Coordinate.of(c.row, c.col + 1)),
    NW('J', c -> Coordinate.of(c.row - 1, c.col), c -> Coordinate.of(c.row, c.col - 1)),
    WS('7', c -> Coordinate.of(c.row, c.col - 1), c -> Coordinate.of(c.row + 1, c.col)),
    SE('F', c -> Coordinate.of(c.row, c.col + 1), c -> Coordinate.of(c.row + 1, c.col)),
    NONE('.', c -> Coordinate.of(-1, -1), c -> Coordinate.of(-1, -1)),
    START('S', c -> Coordinate.of(-1, -1), c -> Coordinate.of(-1, -1));

    private final int                       codePoint;
    private final UnaryOperator<Coordinate> connectionA;
    private final UnaryOperator<Coordinate> connectionB;

    Symbol(int codePoint, UnaryOperator<Coordinate> connectionA,
           UnaryOperator<Coordinate> connectionB) {
      this.codePoint = codePoint;
      this.connectionA = connectionA;
      this.connectionB = connectionB;
    }

    static Symbol fromCodePoint(final int codePoint) {
      return Arrays.stream(Symbol.values())
          .filter(it -> codePoint == it.codePoint)
          .findFirst()
          .orElseThrow();
    }
  }

  private record PipeSegment(Symbol symbol, Coordinate coordinate) {

    static PipeSegment at(Symbol[][] symbols, Coordinate coordinate) {
      return new PipeSegment(symbols[coordinate.row][coordinate.col], coordinate);
    }

    private Stream<PipeSegment> connections(Symbol[][] symbols) {
      if (Symbol.NONE == symbol) {
        return Stream.empty();
      }

      final int rows = symbols.length;
      final int cols = symbols[0].length;

      if (Symbol.START == symbol) {
        return getStartConnections(symbols);
      }

      final Function<Coordinate, Optional<PipeSegment>> findSegment =
          it -> it.outOfBounds(rows, cols)
              ? Optional.empty()
              : Optional.of(PipeSegment.at(symbols, it));
      Optional<PipeSegment> segmentA = symbol.connectionA.andThen(findSegment).apply(coordinate);
      Optional<PipeSegment> segmentB = symbol.connectionB.andThen(findSegment).apply(coordinate);
      return Stream.concat(segmentA.stream(), segmentB.stream());
    }

    private Stream<PipeSegment> getStartConnections(Symbol[][] symbols) {
      final Set<PipeSegment> connections = new HashSet<>();

      if (coordinate.row > 0) {
        final var top = PipeSegment.at(symbols, Coordinate.of(coordinate.row - 1, coordinate.col));
        if (EnumSet.of(Symbol.NS, Symbol.SE, Symbol.WS).contains(top.symbol)) {
          connections.add(top);
        }
      }

      if (coordinate.col > 0) {
        final var left = PipeSegment.at(symbols, Coordinate.of(coordinate.row, coordinate.col - 1));
        if (EnumSet.of(Symbol.NE, Symbol.SE, Symbol.WE).contains(left.symbol)) {
          connections.add(left);
        }
      }

      if (coordinate.col < symbols[0].length - 1) {
        final var right = PipeSegment.at(symbols, Coordinate.of(
            coordinate.row,
            coordinate.col + 1));
        if (EnumSet.of(Symbol.NW, Symbol.WE, Symbol.WS).contains(right.symbol)) {
          connections.add(right);
        }
      }

      if (coordinate.row < symbols.length - 1) {
        final var bottom = PipeSegment.at(symbols, Coordinate.of(
            coordinate.row + 1,
            coordinate.col));
        if (EnumSet.of(Symbol.NS, Symbol.NW, Symbol.NE).contains(bottom.symbol)) {
          connections.add(bottom);
        }
      }

      return connections.stream();
    }
  }

  static PipeMaze parse(List<String> lines) {
    final Symbol[][] symbols = lines.stream()
        .map(it -> it.chars()
            .mapToObj(Symbol::fromCodePoint)
            .toArray(Symbol[]::new))
        .toArray(Symbol[][]::new);
    return new PipeMaze(symbols);
  }

  private final Symbol[][]        symbols;
  private final List<PipeSegment> loop;

  private PipeMaze(Symbol[][] symbols) {
    this.symbols = symbols;
    this.loop = findLoop();
  }

  private List<PipeSegment> findLoop() {
    final var start = findStart();
    final List<PipeSegment> loop = new LinkedList<>();
    loop.add(start);

    var currSegment = start.connections(symbols).findFirst().orElseThrow();
    do {
      final var nextSegment = currSegment.connections(symbols)
          .filter(it -> !loop.getLast().equals(it))
          .findFirst()
          .orElseThrow();

      loop.add(currSegment);
      currSegment = nextSegment;
    } while (!currSegment.equals(start));

    return loop;
  }

  long solvePart1() {
    return loop.size() / 2;
  }

  long solvePart2() {
    long res = 0L;

    final var loopCoords = loop.stream()
        .map(PipeSegment::coordinate)
        .collect(toSet());
    for (int r = 0; r < symbols.length; r++) {
      final var rowSymbols = symbols[r];
      final var loopIndices = new ArrayList<Integer>();
      for (int c = 0; c < symbols[r].length; c++) {
        if (loopCoords.contains(Coordinate.of(r, c))) {
          loopIndices.add(c);
          continue;
        }
        res += loopIndices.stream()
                   .map(i -> rowSymbols[i])
                   .filter(EnumSet.of(Symbol.NS, Symbol.NW, Symbol.NE)::contains)
                   .count() & 1;
      }
    }
    return res;
  }

  private PipeSegment findStart() {
    for (int row = 0; row < symbols.length; row++) {
      for (int col = 0; col < symbols[row].length; col++) {
        if (Symbol.START == symbols[row][col]) {
          return new PipeSegment(Symbol.START, new Coordinate(row, col));
        }
      }
    }
    throw new IllegalStateException("No START PipeSegment is present!");
  }

}
