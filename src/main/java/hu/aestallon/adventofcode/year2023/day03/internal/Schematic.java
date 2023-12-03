package hu.aestallon.adventofcode.year2023.day03.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

public class Schematic {

  private static boolean isDigit(final char c) {
    return Character.isDigit(c);
  }

  private static boolean isSymbol(final char c) {
    return !(isDigit(c) || '.' == c);
  }

  private final char[][]  raw;
  private final Token[][] tokens;

  public Schematic(char[][] raw) {
    Objects.requireNonNull(raw, "raw must not be null!");
    if (raw.length == 0 || Arrays.stream(raw).anyMatch(arr -> arr == null || arr.length == 0)) {
      throw new IllegalArgumentException(
          "Raw array is empty or contains empty rows! " + Arrays.toString(raw));
    }
    this.raw = Objects.requireNonNull(raw, "raw must not be null!");
    this.tokens = new Token[raw.length][];
    initTokens();
  }

  private void initTokens() {
    final List<Token> currentRowTokens = new ArrayList<>();
    final List<Character> currentNum = new ArrayList<>();
    for (int row = 0; row < raw.length; row++) {
      for (int col = 0; col < raw[row].length; col++) {
        final char c = raw[row][col];
        if (isDigit(c)) {
          currentNum.add(c);
        } else {
          if (!currentNum.isEmpty()) {
            currentRowTokens.add(new Number(currentNum, new Coordinate(row, col - 1)));
            currentNum.clear();
          }

          if (isSymbol(c)) {
            currentRowTokens.add(new Symbol(c, new Coordinate(row, col)));
          }
        }
      }

      // handle number termination at the end of a row:
      if (!currentNum.isEmpty()) {
        currentRowTokens.add(new Number(currentNum, new Coordinate(row, raw[row].length - 1)));
        currentNum.clear();
      }

      final Token[] rowTokens = currentRowTokens.toArray(Token[]::new);
      tokens[row] = rowTokens;
      currentRowTokens.clear();
    }
  }

  public Stream<Number> numbersAdjacentToSymbols() {
    return tokens(Number.class).filter(Number::isAdjacentToSymbols);
  }

  public Stream<Gear> gears() {
    return tokens(Symbol.class).flatMap(s -> s.asGear().stream());
  }

  private <T extends Token> Stream<T> tokens(Class<T> tokenType) {
    return Arrays.stream(tokens)
        .flatMap(Arrays::stream)
        .filter(tokenType::isInstance)
        .map(tokenType::cast);
  }

  private record Coordinate(int row, int col) {}

  public sealed interface Token permits Number, Symbol {
    boolean isAdjacent(Token that);

    @SuppressWarnings("unused")
    Set<Token> adjacentTo();
  }

  public final class Number implements Token {
    private final int  row;
    private final int  startCol;
    private final int  endCol;
    private final long value;

    private Number(final List<Character> characters, Coordinate endCoordinate) {
      row = endCoordinate.row();
      startCol = endCoordinate.col() - characters.size() + 1;
      endCol = endCoordinate.col();
      value = characters.stream()
          .map(String::valueOf)
          .collect(
              collectingAndThen(
                  joining(),
                  Long::parseLong));
    }

    public boolean isAdjacentToSymbols() {
      return adjacentTo().stream().anyMatch(t -> t instanceof Symbol);
    }

    public long longVal() {
      return value;
    }

    @Override
    public boolean isAdjacent(Token that) {
      if (this.equals(that)) {
        return false;
      }

      return switch (that) {
        case Symbol s -> s.isAdjacent(this);
        case Number n -> Math.abs(row - n.row) < 2
                         && n.endCol >= (startCol - 1)
                         && n.startCol <= (endCol + 1);
      };
    }

    @Override
    public Set<Token> adjacentTo() {
      return IntStream
          .rangeClosed(
              Math.max(row - 1, 0),
              Math.min(row + 1, Schematic.this.tokens.length - 1))
          .mapToObj(i -> Schematic.this.tokens[i])
          .flatMap(Arrays::stream)
          .filter(t -> t.isAdjacent(this))
          .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {return true;}
      if (o == null || getClass() != o.getClass()) {return false;}
      Number number = (Number) o;
      return row == number.row && startCol == number.startCol && endCol == number.endCol && value == number.value;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, startCol, endCol, value);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", Number.class.getSimpleName() + "[ ", " ]")
          .add("row=" + row)
          .add("startCol=" + startCol)
          .add("endCol=" + endCol)
          .add("value=" + value)
          .toString();
    }
  }

  public final class Symbol implements Token {
    private final char       value;
    private final Coordinate position;

    private Symbol(char value, Coordinate position) {
      this.value = value;
      this.position = position;
    }

    private Optional<Gear> asGear() {
      if (value != '*') {
        return Optional.empty();
      }

      final Set<Number> connectedParts = adjacentTo().stream()
          .filter(t -> t instanceof Number)
          .map(Number.class::cast)
          .collect(Collectors.toSet());
      if (connectedParts.size() != 2) {
        return Optional.empty();
      }

      return Optional.of(new Gear(this, connectedParts));
    }

    @Override
    public boolean isAdjacent(Token that) {
      if (this.equals(that)) {
        return false;
      }

      return switch (that) {
        case Symbol s -> Math.abs(this.position.col - s.position.col) < 2
                         && Math.abs(this.position.row - s.position.row) < 2;
        case Number n -> Math.abs(this.position.row - n.row) < 2
                         && position.col >= (n.startCol - 1)
                         && position.col <= (n.endCol + 1);
      };
    }

    @Override
    public Set<Token> adjacentTo() {
      return IntStream
          .rangeClosed(
              Math.max(position.row - 1, 0),
              Math.min(position.row + 1, Schematic.this.tokens.length - 1))
          .mapToObj(i -> Schematic.this.tokens[i])
          .flatMap(Arrays::stream)
          .filter(t -> t.isAdjacent(this))
          .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {return true;}
      if (o == null || getClass() != o.getClass()) {return false;}
      Symbol symbol = (Symbol) o;
      return value == symbol.value && Objects.equals(position, symbol.position);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, position);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", Symbol.class.getSimpleName() + "[ ", " ]")
          .add("value=" + value)
          .add("position=" + position)
          .toString();
    }
  }

  public record Gear(Symbol gearSymbol, Set<Number> parts) {}

}
