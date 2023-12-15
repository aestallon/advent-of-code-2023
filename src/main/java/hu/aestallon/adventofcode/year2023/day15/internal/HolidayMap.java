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

package hu.aestallon.adventofcode.year2023.day15.internal;

import java.util.LinkedHashMap;

public class HolidayMap {

  public static HolidayMap newInstance() {
    return new HolidayMap();
  }

  private static final class Box {
    LinkedHashMap<String, Integer> lensesByLabel = new LinkedHashMap<>();
  }

  private final Box[] boxes;

  private HolidayMap() {
    boxes = new Box[256];
    for (int i = 0; i < boxes.length; i++) {
      boxes[i] = new Box();
    }
  }

  public void put(Instruction instruction) {
    final int boxHash = HolidayHash.hash(instruction.label());
    final Box box = boxes[boxHash];
    switch (instruction.op()) {
      case ADD -> box.lensesByLabel.put(instruction.label(), instruction.focalLength());
      case REMOVE -> box.lensesByLabel.remove(instruction.label());
    }
  }

  public long focusingPower() {
    long result = 0;
    for (int i = 0; i < boxes.length; i++) {
      final Box box = boxes[i];
      final int[] focusLengths = box.lensesByLabel
          .sequencedValues().stream()
          .mapToInt(Integer::intValue)
          .toArray();
      for (int j = 0; j < focusLengths.length; j++) {
        result += (long) (i + 1) * (j + 1) * focusLengths[j];
      }
    }
    return result;
  }

}
