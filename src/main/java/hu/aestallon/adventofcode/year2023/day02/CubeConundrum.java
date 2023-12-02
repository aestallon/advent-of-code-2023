package hu.aestallon.adventofcode.year2023.day02;

import hu.aestallon.adventofcode.year2023.day02.internal.Game;
import hu.aestallon.adventofcode.year2023.day02.internal.Sample;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CubeConundrum {

  private static final Logger log = LoggerFactory.getLogger(CubeConundrum.class);

  public static void main(String[] args) {
    final var bound = Sample.of(12, 13, 14);
    final var games = AocIO.lines("02", "input02.txt").stream()
        .map(Game::parse)
        .toList();

    // part 1:
    final int sum = games.stream()
        .filter(it -> it.isPossible(bound))
        .mapToInt(Game::id)
        .sum();
    log.info("The sum of the possible games with bounds [ {} ] is [ {} ]", bound, sum);

    // part 2:
    final long powerSum = games.stream()
        .map(Game::lowerBound)
        .mapToLong(Sample::power)
        .sum();
    log.info("The sum of the power of the minimum enveloping sets is [ {} ]", powerSum);
  }

}
