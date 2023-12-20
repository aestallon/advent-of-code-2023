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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Workflow {

  public static Workflow parse(String s) {
    final int startTokenIdx = s.indexOf('{');
    final String label = s.substring(0, startTokenIdx);
    final String[] steps = s.substring(startTokenIdx + 1, s.length() - 1).split(",");
    return new Workflow(
        WorkflowLabel.of(label),
        Arrays.stream(steps).map(Operation::parse).toList());
  }

  private final WorkflowLabel label;
  private final List<Operation> ops;

  private Workflow(WorkflowLabel label, List<Operation> ops) {
    this.label = Objects.requireNonNull(label);
    this.ops = ops;
  }

  public WorkflowLabel label() {
    return label;
  }

  public WorkflowLabel apply(MachinePart part) {
    return ops.stream().flatMap(it -> it.apply(part).stream()).findFirst().orElseThrow();
  }

  public Set<WorkflowInstruction> process(Bound bound) {

    return Collections.emptySet();
  }

}
