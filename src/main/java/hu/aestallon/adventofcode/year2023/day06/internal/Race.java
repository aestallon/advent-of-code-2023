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

package hu.aestallon.adventofcode.year2023.day06.internal;

public record Race(long time, long distance) {

  public long numberOfWaysToWin() {
    var x = (time - Math.sqrt((double) (time * time) - 4 * distance)) / 2;
    var y = (long) (x + 1L);
    var z = (long) (((double) time) / 2 - y + 1);
    return z * 2 - ((time % 2 == 0) ? 1 : 0);
  }

}
