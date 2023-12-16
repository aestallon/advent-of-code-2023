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

package hu.aestallon.adventofcode.year2023.day16.internal;

import java.util.List;

enum Element {

  NONE {
    @Override
    List<Light> handle(Light light) {
      return List.of(light.move());
    }
  },
  MIRROR_A { // \
    @Override
    List<Light> handle(Light light) {
      return List.of(switch (light.d()) {
        case N -> light.copy(Direction.W).move();
        case E -> light.copy(Direction.S).move();
        case S -> light.copy(Direction.E).move();
        case W -> light.copy(Direction.N).move();
      });
    }
  },
  MIRROR_B { // /
    @Override
    List<Light> handle(Light light) {
      return List.of(switch (light.d()) {
        case N -> light.copy(Direction.E).move();
        case E -> light.copy(Direction.N).move();
        case S -> light.copy(Direction.W).move();
        case W -> light.copy(Direction.S).move();
      });
    }
  },
  SPLITTER_H { // -
    @Override
    List<Light> handle(Light light) {
      return switch (light.d()) {
        case N, S -> List.of(light.copy(Direction.W).move(), light.copy(Direction.E).move());
        case W, E -> List.of(light.move());
      };
    }
  },
  SPLITTER_V { // |
    @Override
    List<Light> handle(Light light) {
      return switch (light.d()) {
        case W, E -> List.of(light.copy(Direction.N).move(), light.copy(Direction.S).move());
        case N, S -> List.of(light.move());
      };
    }
  };

  static Element parse(int codePoint) {
    return switch (codePoint) {
      case '.' -> NONE;
      case '\\' -> MIRROR_A;
      case '/' -> MIRROR_B;
      case '|' -> SPLITTER_V;
      case '-' -> SPLITTER_H;
      default -> throw new IllegalArgumentException((char) codePoint + " is not a valid Element!");
    };
  }

  abstract List<Light> handle(Light light);

}
