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

package hu.aestallon.adventofcode.year2023.day16.visualisation;

import hu.aestallon.adventofcode.year2023.day16.internal.Contraption;
import hu.aestallon.adventofcode.year2023.day16.internal.Light;
import hu.aestallon.adventofcode.year2023.day16.internal.Position;
import hu.aestallon.adventofcode.year2023.util.AocIO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

final class Demo extends JFrame {

  public static final int SCALE = 5;
  public static final int SPEED = 10;
  public static final int DELAY = 1_000;

  private final ContraptionSimulator simulator;
  private final int                  speed;

  private Timer   timer;
  private boolean simulationComplete;

  public Demo(ContraptionSimulator simulator, int speed) {
    this.simulator = simulator;
    this.speed = speed;
    this.simulationComplete = false;

    init();
    startSimulation();
  }

  private void init() {
    setTitle("The Floor Will Be Lava");
    setSize(simulator.width() * SCALE, simulator.height() * SCALE);
    setMenuBar(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Canvas canvas = new Canvas();
    add(canvas);
    pack();

    timer = new Timer(speed, e -> {
      if (!simulationComplete) {
        simulator.step();
        canvas.setPs(simulator.positions());
        canvas.repaint();

        if (simulator.isComplete()) {
          simulationComplete = true;
          timer.stop();
          Timer restartTimer = new Timer(DELAY, e1 -> {
            if (simulator.hasNext()) {
              simulator.next();
              canvas.clearCoordinates();
              canvas.repaint();
              startSimulation();

            } else {

              System.exit(0);
            }


          });
          restartTimer.setRepeats(false);
          restartTimer.start();
        }
      }
    });
  }

  private void startSimulation() {
    simulationComplete = false;
    timer.start();
  }

  public static final class ContraptionSimulator {

    private final List<Contraption.ContraptionRunner> runners;
    private       int                                 idx;

    private ContraptionSimulator(Contraption contraption) {
      runners = contraption.prepare().map(contraption::newRunner).toList();
    }

    public boolean hasNext() {
      return idx < runners.size() - 1;
    }

    public void next() {
      idx++;
    }

    public boolean isComplete() {
      return runners.get(idx).isComplete();
    }

    public void step() {
      runners.get(idx).step();
    }

    public int height() {
      return runners.get(idx).height();
    }

    public int width() {
      return runners.get(idx).width();
    }

    public java.util.List<Position> positions() {
      return runners.get(idx).knownLights().stream().map(Light::p).toList();
    }
  }

  private class Canvas extends JPanel {
    private java.util.List<Position> ps;

    public Canvas() {
      setPreferredSize(new Dimension(Demo.this.getWidth(), Demo.this.getHeight()));
    }

    public void setPs(java.util.List<Position> ps) {
      this.ps = ps;
    }

    public void clearCoordinates() {
      this.ps = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, getWidth(), getHeight());

      if (ps != null) {
        g.setColor(Color.YELLOW);
        ps.forEach(it -> g.fillRect(it.c() * SCALE, it.r() * SCALE, SCALE, SCALE));
      }
    }
  }

  public static void main(String[] args) {
    final var lines = AocIO.lines("16", "input16.txt");
    final var contraption = Contraption.parse(lines);
    final var simulator = new ContraptionSimulator(contraption);

    SwingUtilities.invokeLater(() -> new Demo(simulator, SPEED).setVisible(true));
  }

}

