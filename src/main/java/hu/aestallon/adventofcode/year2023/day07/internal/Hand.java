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

package hu.aestallon.adventofcode.year2023.day07.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public final class Hand implements Comparable<Hand> {

  private static final Logger log = LoggerFactory.getLogger(Hand.class);

  public static Hand parse(String s) {
    return parse(s, Rank::parse);
  }

  public static Hand parseWithJokers(String s) {
    return parse(s, Rank::parseWithJoker);
  }

  private static Hand parse(String s, IntFunction<Rank> rankParser) {
    final String[] parts = s.split("\\s");
    final String cardsAsStr = parts[0].trim();
    final Card[] cards = cardsAsStr.chars()
        .mapToObj(rankParser)
        .map(Card::new)
        .toArray(Card[]::new);
    if (cards.length != 5) {
      log.error("While parsing [ {} ] could not find exactly 5 cards!: [ {} ]", s, cards);
    }

    final int bid = Integer.parseInt(parts[1].trim());
    return new Hand(cards, bid);
  }

  private static HandType determineType(Card[] cards) {
    final var countByRank = Arrays.stream(cards).collect(groupingBy(Card::rank, counting()));
    final var arity = countByRank.size() - (countByRank.containsKey(Rank.JOKER) ? 1 : 0);
    final var maxOccurrence = countByRank.entrySet().stream()
        .filter(it -> Rank.JOKER != it.getKey())
        .mapToLong(Map.Entry::getValue)
        .max()
        .orElse(0);
    final var jokerCount = countByRank.getOrDefault(Rank.JOKER, 0L).intValue();
    return switch (jokerCount) {
      case 5, 4 -> HandType.FIVE_OF_A_KIND;
      case 3 -> (arity == 1) ? HandType.FIVE_OF_A_KIND : HandType.FOUR_OF_A_KIND;
      case 2 -> switch (arity) {
        case 1 -> HandType.FIVE_OF_A_KIND;
        case 2 -> HandType.FOUR_OF_A_KIND;
        case 3 -> HandType.THREE_OF_A_KIND;
        default -> throw new AssertionError("Illegal card amount: " + cards.length);
      };
      case 1 -> switch (arity) {
        case 1 -> HandType.FIVE_OF_A_KIND;
        case 2 -> (maxOccurrence == 3) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
        case 3 -> HandType.THREE_OF_A_KIND;
        case 4 -> HandType.ONE_PAIR;
        default -> throw new AssertionError("Illegal card amount: " + cards.length);
      };
      case 0 -> switch (arity) {
        case 1 -> HandType.FIVE_OF_A_KIND;
        case 2 -> (maxOccurrence == 4) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
        case 3 -> (maxOccurrence == 3) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
        case 4 -> HandType.ONE_PAIR;
        default -> HandType.HIGH_CARD;
      };
      default -> throw new AssertionError("Illegal jokerCount: " + jokerCount);
    };
  }

  private final Card[]   cards;
  private final int      bid;
  private final HandType type;

  private Hand(Card[] cards, int bid) {
    this.cards = cards;
    this.bid = bid;
    this.type = determineType(cards);
  }

  public int bid() {
    return bid;
  }

  @Override
  public int compareTo(Hand that) {
    final int typePrecedence = this.type.precedence() - that.type.precedence();
    if (typePrecedence != 0) {
      return typePrecedence;
    }

    return IntStream.range(0, cards.length)
        .map(i -> this.cards[i].compareTo(that.cards[i]))
        .filter(i -> i != 0)
        .findFirst()
        .orElse(0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    Hand hand = (Hand) o;
    return bid == hand.bid && Arrays.equals(cards, hand.cards);
  }

  @Override
  public int hashCode() {
    return 31 * bid + Arrays.hashCode(cards);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Hand.class.getSimpleName() + "[ ", " ]")
        .add("cards=" + Arrays.toString(cards))
        .add("bid=" + bid)
        .toString();
  }

}
