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

import java.util.Set;

public record WorkflowLabel(String s) {

  public static final WorkflowLabel ACCEPTED = of("A");
  public static final WorkflowLabel REJECTED = of("R");
  public static final WorkflowLabel IN       = of("in");

  public static final Set<WorkflowLabel> FINAL_LABELS = Set.of(ACCEPTED, REJECTED);

  public static WorkflowLabel of(String s) {
    return new WorkflowLabel(s);
  }

}
