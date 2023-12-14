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

package hu.aestallon.adventofcode.year2023.day14.internal;

public enum Direction {

  NORTH(-1, 0),
  SOUTH( 1, 0),
  WEST( 0, -1),
  EAST(0, 1);

  private final int dRow;
  private final int dCol;

  Direction(int dRow, int dCol) {
    this.dRow = dRow;
    this.dCol = dCol;
  }

  public int dRow() {
    return dRow;
  }

  public int dCol() {
    return dCol;
  }

}
