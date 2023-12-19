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

package hu.aestallon.adventofcode.year2023.day18;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public final class LavaductLagoon {

  private final Color[][] colours;

  public LavaductLagoon(List<String> lines) {
    int r = 0, c = 0;
    final Set<Cube> cubes = new HashSet<>();
    for (var line : lines) {
      final var instruction = DiggingInstruction.parse(line);
      final var evaluationResult = instruction.evaluate(r, c);
      r = evaluationResult.r;
      c = evaluationResult.c;
      cubes.addAll(evaluationResult.cubes);
    }

    final int minR = cubes.stream().mapToInt(Cube::r).min().orElse(0);
    final int minC = cubes.stream().mapToInt(Cube::c).min().orElse(0);

    final int maxR = cubes.stream().mapToInt(Cube::r).max().orElse(0);
    final int maxC = cubes.stream().mapToInt(Cube::c).max().orElse(0);
    colours = new Color[maxR - minR + 1][maxC - minC + 1];
    cubes.forEach(it -> colours[it.r - minR][it.c - minC] = it.colour);
  }

  public boolean inside(int r, int c) {
    if (r == 0 || c == 0 || r == colours.length - 1 || c == colours[0].length - 1) {
      return colours[r][c] != null;
    }

    if (colours[r][c] != null) {
      return true;
    }

    int passages = 0;
    for (int i = 0; i < c; i++) {
      if (colours[r][i] != null) {
        int nbCnt = 0;
        if (i > 0 && colours[r][i - 1] != null) {
          nbCnt++;
        }
        if (colours[r][i + 1] != null) {
          nbCnt++;
        }
        if (nbCnt == 0) {
          passages++;
        } else if (nbCnt == 1 && colours[r - 1][i] != null) {
          passages++;
        }
      }
    }
    return (passages & 1) == 1;
  }

  long solvePart1() {
    return IntStream.range(0, colours.length)
        .mapToLong(r -> IntStream.range(0, colours[r].length)
            .filter(c -> inside(r, c))
            .count())
        .sum();
  }

  private record Cube(int r, int c, Color colour) {}

  private enum Direction {

    N(-1, 0),
    E(0, 1),
    S(1, 0),
    W(0, -1);

    private static Direction parse(String s) {
      return switch (s) {
        case "N", "U" -> N;
        case "E", "R" -> E;
        case "S", "D" -> S;
        case "W", "L" -> W;
        default -> throw new IllegalArgumentException(s + " is not a valid Direction");
      };
    }

    private final int dR;
    private final int dC;

    Direction(int dR, int dC) {
      this.dR = dR;
      this.dC = dC;
    }

  }

  private record DiggingInstruction(Direction d, int length, Color colour) {

    private static DiggingInstruction parse(String s) {
      final var parts = s.split("\\s");

      final Direction d = Direction.parse(parts[0]);
      final int length = Integer.parseInt(parts[1]);
      final Color colour = Color.decode(parts[2].substring(1, parts[2].length() - 1));

      return new DiggingInstruction(d, length, colour);
    }

    private DiggingInstructionEvaluationResult evaluate(int r, int c) {
      final List<Cube> cubes = IntStream.rangeClosed(1, length)
          .mapToObj(i -> new Cube(r + i * d.dR, c + i * d.dC, colour))
          .toList();
      final Cube lastCube = cubes.getLast();
      return new DiggingInstructionEvaluationResult(new HashSet<>(cubes), lastCube.r, lastCube.c);
    }

  }

  private record DiggingInstructionEvaluationResult(Set<Cube> cubes, int r, int c) {}


  public int width() {
    return colours[0].length;
  }

  public int height() {
    return colours.length;
  }

  public Color[][] colours() {
    return colours;
  }

}
