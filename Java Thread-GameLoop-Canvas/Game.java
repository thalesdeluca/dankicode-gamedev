import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;

public class Game extends Canvas implements Runnable {
  private boolean isRunning;
  private Thread thread;
  private static JFrame screen;

  private final int WIDTH = 512;
  private final int HEIGHT = 320;
  private int scale = 1;
  private double maxFPS = 60.0;
  private BufferedImage image;

  public double deltaTime = 0;

  public Game() {
    screen = new JFrame();
    screen.add(this);
    screen.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
    screen.setLocationRelativeTo(null);
    screen.setResizable(false);
    screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    screen.setVisible(true);
    screen.pack();

    image = new BufferedImage(160, 120, BufferedImage.TYPE_INT_RGB);
  }

  public synchronized void start() {
    thread = new Thread(this);
    isRunning = true;
    thread.start();
  }

  public synchronized void stop() {
    try {
      thread.join();
    } catch (InterruptedException e) {
      isRunning = false;
    }
  }

  public void update() {

  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();
    if (bs == null) {
      this.createBufferStrategy(3);
      return;
    }
    Graphics g = image.getGraphics();
    g.setColor(new Color(0, 0, 130));
    g.fillRect(0, 0, 160, 120);
    g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, WIDTH * scale, HEIGHT * scale, null);
    bs.show();
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double ticks = maxFPS;
    double ns = 1000000000.0 / ticks;
    double delta = 0;
    int frames = 0;
    double timer = System.currentTimeMillis();

    while (isRunning) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;

      lastTime = now;
      if (delta >= 1) {
        deltaTime = delta;
        update();
        render();
        frames++;
        delta = 0;
      }

      if (System.currentTimeMillis() - timer >= 1000) {
        System.out.println("FPS: " + frames);
        frames = 0;
        timer += 1000;
      }
    }
    stop();
  }

  public void setMaxFPS(double FPS) {
    this.maxFPS = FPS;
  }

  public double getMaxFPS() {
    return this.maxFPS;
  }
}