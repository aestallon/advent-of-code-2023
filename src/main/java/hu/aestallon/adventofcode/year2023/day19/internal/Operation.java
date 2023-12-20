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

package hu.aestallon.adventofcode.year2023.day19.internal;

import java.util.Optional;

final class Operation {

  static Operation parse(String s) {
    final int jumpToken = s.indexOf(':');
    if (jumpToken < 0) {
      return new Operation(WorkflowLabel.of(s), null);
    }
    return new Operation(
        WorkflowLabel.of(s.substring(jumpToken + 1)),
        Condition.parse(s.substring(0, jumpToken)));
  }

  private final WorkflowLabel next;
  private final Condition     condition;

  private Operation(WorkflowLabel next, Condition condition) {
    this.next = next;
    this.condition = condition;
  }

  Optional<WorkflowLabel> apply(MachinePart part) {
    return (condition == null || condition.test(part))
        ? Optional.of(next)
        : Optional.empty();
  }

}
