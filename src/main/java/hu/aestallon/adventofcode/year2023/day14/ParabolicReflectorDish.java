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

package hu.aestallon.adventofcode.year2023.day14;

import hu.aestallon.adventofcode.year2023.day14.internal.Direction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

final class ParabolicReflectorDish {

  final long cycleNr;

  private final int cols;
  private final int rows;

  private final Set<Rock<?>>  rocks;
  private final Set<Position> positions;

  ParabolicReflectorDish(List<String> lines) {
    this.cycleNr = 0L;

    this.cols = lines.getFirst().length();
    this.rows = lines.size();

    this.rocks = new HashSet<>();
    this.positions = new TreeSet<>();
    for (int row = 0; row < lines.size(); row++) {
      final char[] line = lines.get(row).toCharArray();
      for (int col = 0; col < line.length; col++) {
        final char c = line[col];
        if (c == '#' || c == 'O') {
          final var p = new Position(row, col);
          final var r = switch (c) {
            case '#' -> new CubeRock(p);
            case 'O' -> new RoundRock(p);
            default -> throw new AssertionError(); // cannot happen
          };
          positions.add(p);
          rocks.add(r);
        }
      }
    }
  }

  private ParabolicReflectorDish(long cycleNr, int rows, int cols, Set<Rock<?>> rocks) {
    this.cycleNr = cycleNr;

    this.rows = rows;
    this.cols = cols;
    this.rocks = rocks;
    this.positions = rocks.stream().map(it -> it.position).collect(Collectors.toSet());
  }

  ParabolicReflectorDish tilt(Direction direction) {
    return tilt(cycleNr, direction);
  }

  private ParabolicReflectorDish tilt(long cycleNr, Direction direction) {
    final Set<Rock<?>> rolledRocks = rocks.stream()
        .sorted(comparator(direction))
        .map(it -> it.roll(direction))
        .map(it -> (Rock<?>) it)
        .collect(Collectors.toSet());
    return new ParabolicReflectorDish(cycleNr, rows, cols, rolledRocks);
  }

  ParabolicReflectorDish cycle() {
    return tilt(Direction.NORTH)
        .tilt(Direction.WEST)
        .tilt(Direction.SOUTH)
        .tilt(cycleNr + 1, Direction.EAST);
  }

  private Comparator<Rock<?>> comparator(Direction direction) {
    final Comparator<Rock<?>> n = Comparator.comparingInt(r -> r.position.row);
    final Comparator<Rock<?>> s = (r1, r2) -> r2.position.row - r1.position.row;
    final Comparator<Rock<?>> w = Comparator.comparingInt(r -> r.position.col);
    final Comparator<Rock<?>> e = (r1, r2) -> r2.position.col - r1.position.col;
    return switch (direction) {
      case NORTH -> n;
      case SOUTH -> s;
      case WEST -> w;
      case EAST -> e;
    };
  }

  long currentLoad(Direction direction) {
    return rocks.stream().mapToLong(it -> it.score(direction)).sum();
  }

  @Override
  public String toString() {
    final String[][] arr = new String[rows][cols];
    for (var r : arr) {
      Arrays.fill(r, ".");
    }
    rocks.forEach(it -> arr[it.position.row][it.position.col] = it.sign());
    return Arrays.stream(arr)
        .map(r -> String.join("", r))
        .collect(joining("\n"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    ParabolicReflectorDish that = (ParabolicReflectorDish) o;
    return Objects.equals(rocks, that.rocks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rocks);
  }

  private final class Position implements Comparable<Position> {

    final int row;
    final int col;

    private Position(int row, int col) {
      this.row = row;
      this.col = col;
    }

    private Position farthestFree(Direction direction) {
      Position p = this;
      while (p.row >= 0 && p.col >= 0 && p.row < rows && p.col < cols) {
        Position pRolled = p.apply(direction);
        if (positions.contains(pRolled) || pRolled.outOfBounds()) {
          break;
        } else {
          p = pRolled;
        }
      }
      positions.remove(this);
      positions.add(p);
      return p;
    }

    private Position apply(Direction direction) {
      return new Position(row + direction.dRow(), col + direction.dCol());
    }

    private boolean outOfBounds() {
      return row < 0 || col < 0 || row >= rows || col >= cols;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {return true;}
      if (o == null || getClass() != o.getClass()) {return false;}
      Position position = (Position) o;
      return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }

    @Override
    public String toString() {
      return "[" + row + "," + col + "]";
    }

    @Override
    public int compareTo(Position that) {
      final int dRow = this.row - that.row;
      return (dRow == 0)
          ? (this.col - that.col)
          : dRow;
    }

  }

  private static sealed abstract class Rock<T extends Rock<T>> permits RoundRock, CubeRock {
    protected final Position position;

    protected Rock(Position position) {
      this.position = position;
    }

    protected abstract T roll(Direction direction);

    protected abstract String sign();

    protected abstract int score(Direction direction);

    @Override
    public boolean equals(Object o) {
      if (this == o) {return true;}
      if (o == null || getClass() != o.getClass()) {return false;}
      Rock<?> rock = (Rock<?>) o;
      return Objects.equals(position, rock.position);
    }

    @Override
    public int hashCode() {
      return Objects.hash(position);
    }

    @Override
    public String toString() {
      return sign() + position.toString();
    }

  }

  private final class RoundRock extends Rock<RoundRock> {

    private RoundRock(Position position) {
      super(position);
    }

    @Override
    protected RoundRock roll(Direction direction) {
      return new RoundRock(position.farthestFree(direction));
    }

    @Override
    protected String sign() {
      return "O";
    }

    @Override
    protected int score(Direction direction) {
      return switch (direction) {
        case NORTH -> rows - position.row;
        case SOUTH -> position.row + 1;
        case WEST -> cols - position.col;
        case EAST -> position.col + 1;
      };
    }
  }

  private final class CubeRock extends Rock<CubeRock> {

    private CubeRock(Position position) {
      super(position);
    }

    @Override
    protected CubeRock roll(Direction direction) {
      return this;
    }

    @Override
    protected String sign() {
      return "#";
    }

    @Override
    protected int score(Direction direction) {
      return 0;
    }

  }

}
