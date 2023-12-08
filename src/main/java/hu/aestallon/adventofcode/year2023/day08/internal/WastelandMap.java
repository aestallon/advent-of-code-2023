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

package hu.aestallon.adventofcode.year2023.day08.internal;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public final class WastelandMap {

  private record MapNode(String id, String left, String right) {
    private static final String  REGEX   = "^([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static MapNode parse(String s) {
      final Matcher m = PATTERN.matcher(s);
      if (!m.find()) {
        throw new IllegalArgumentException(s + " cannot be parsed as a MapNode!");
      }

      return new MapNode(m.group(1), m.group(2), m.group(3));
    }
  }

  public static WastelandMap parse(List<String> lines) {
    final var mapNodes = lines.stream()
        .map(MapNode::parse)
        .collect(toMap(
            MapNode::id,
            it -> it,
            (a, b) -> b,
            TreeMap::new));
    return new WastelandMap(mapNodes);
  }

  private static Turn[] parseTurns(String s) {
    return s.chars().mapToObj(Turn::parse).toArray(Turn[]::new);
  }

  private final Map<String, MapNode> nodes;

  private WastelandMap(Map<String, MapNode> nodes) {
    this.nodes = nodes;
  }

  public int stepsToTraverse(String from, String to, String stepsStr) {
    final var journey = new Journey(from, to);
    return journey.go(parseTurns(stepsStr));
  }

  public long ghostStepsToTraverse(String stepStr) {
    final Turn[] turns = parseTurns(stepStr);
    return nodes.keySet().stream()
        .filter(it -> it.endsWith("A"))
        .map(it -> new Journey(it, "Z"))
        .mapToInt(it -> it.go(turns))
        .mapToObj(BigInteger::valueOf)
        .collect(collectingAndThen(toList(), this::lcm));
  }

  private long lcm(List<BigInteger> bis) {
    BigInteger res = BigInteger.ONE;
    for (final BigInteger bi : bis) {
      res = (res.multiply(bi)).divide(res.gcd(bi));
    }
    return res.longValue();
  }

  private final class Journey {
    private final String from;
    private final String to;

    private Journey(String from, String to) {
      this.from = from;
      this.to = to;
    }

    private int go(Turn[] turns) {
      int stepsTaken = 0;
      String currNode = from;
      while (!currNode.endsWith(to)) {
        final Turn turn = turns[stepsTaken++ % turns.length];
        currNode = takeStep(currNode, turn);
      }
      return stepsTaken;
    }

    private String takeStep(String currNode, Turn turn) {
      final MapNode n = nodes.get(currNode);
      return (turn == Turn.LEFT) ? n.left : n.right;
    }
  }

}
