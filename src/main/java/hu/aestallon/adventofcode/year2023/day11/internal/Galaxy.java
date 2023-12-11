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

package hu.aestallon.adventofcode.year2023.day11.internal;

public record Galaxy(Position position) implements Comparable<Galaxy> {

  @Override
  public int compareTo(Galaxy that) {
    return this.position.compareTo(that.position);
  }

  public long distanceTo(Galaxy that) {
    final long dX = this.position.x() - that.position.x();
    final long dY = this.position.y() - that.position.y();
    return Math.abs(dX) + Math.abs(dY);
  }

}
