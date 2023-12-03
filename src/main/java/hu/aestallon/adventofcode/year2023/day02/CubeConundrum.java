package hu.aestallon.adventofcode.year2023.day02;

import hu.aestallon.adventofcode.year2023.day02.internal.Game;
import hu.aestallon.adventofcode.year2023.day02.internal.Sample;

import java.util.List;

public final class CubeConundrum {

  public static CubeConundrum create(List<String> lines) {
    return new CubeConundrum(lines.stream()
        .map(Game::parse)
        .toList());
  }

  private final List<Game> games;

  private CubeConundrum(List<Game> games) {
    this.games = games;
  }

  int solvePart1(final Sample bound) {
    return games.stream()
        .filter(it -> it.isPossible(bound))
        .mapToInt(Game::id)
        .sum();
  }

  long solvePart2() {
    return games.stream()
        .map(Game::lowerBound)
        .mapToLong(Sample::power)
        .sum();
  }

}
