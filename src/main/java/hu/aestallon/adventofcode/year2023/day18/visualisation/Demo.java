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

package hu.aestallon.adventofcode.year2023.day18.visualisation;

import hu.aestallon.adventofcode.year2023.day18.LavaductLagoon;
import hu.aestallon.adventofcode.year2023.util.AocIO;

import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

  public static final int SCALE = 2;

  private final LavaductLagoon lagoon;

  public Demo(LavaductLagoon lagoon) {
    this.lagoon = lagoon;
    init();
  }

  private void init() {
    setTitle("Lavaduct Lagoon");
    setSize(lagoon.width() * SCALE, lagoon.height() * SCALE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Canvas canvas = new Canvas();
    add(canvas);
    pack();
  }

  private final class Canvas extends JPanel {

    private Canvas() {
      setPreferredSize(new Dimension(Demo.this.getWidth(), Demo.this.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0, 0, getWidth(), getHeight());
      Color[][] colours = lagoon.colours();
      for (int r = 0; r < colours.length; r++) {
        for (int c = 0; c < colours[r].length; c++) {
          final Color colour = colours[r][c];
          if (colour == null) {
            if (lagoon.inside(r, c)) {
              g.setColor(Color.WHITE);
            } else {
              g.setColor(Color.LIGHT_GRAY);
            }
          } else {
            g.setColor(colour);
          }
          g.fillRect(c * SCALE, r * SCALE, SCALE, SCALE);
        }
      }
    }

  }

  public static void main(String[] args) {
    final var lines = AocIO.lines("18", "input18.txt");
    final var lagoon = new LavaductLagoon(lines);

    SwingUtilities.invokeLater(() -> new Demo(lagoon).setVisible(true));
  }

}
