import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Canvas implements Runnable, KeyListener {

  private static final long serialVersionUID = 1654653165L;
  private boolean isRunning;
  private static JFrame screen;
  private Thread thread;

  public static final int _WIDTH = 640;
  public static final int _HEIGHT = 320;

  private double maxFPS = 60.0;
  public static double deltaTime = 0;

  public BufferedImage layer = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);

  private Player player;
  private Enemy enemy;
  private Ball ball;

  public Game() {
    player = new Player();
    enemy = new Enemy();
    ball = new Ball();

    this.addKeyListener(this);
    screen = new JFrame("Pong Java");
    screen.setResizable(false);
    screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    screen.setLocationRelativeTo(null);
    screen.add(this);
    screen.setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
    screen.pack();
    screen.setVisible(true);
  }

  public static void main(String arg[]) {
    Game game = new Game();
    game.start();
  }

  public synchronized void start() {
    isRunning = true;
    thread = new Thread(this);
    thread.start();
  }

  public synchronized void stop() {
    try {
      thread.join();
    } catch (InterruptedException e) {
      System.out.println("Could not stop");
      isRunning = false;
    }

  }

  public void update() {
    player.update();
    enemy.update();
    ball.update();
  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();
    if (bs == null) {
      this.createBufferStrategy(3);
      return;
    }
    Graphics g = layer.getGraphics();
    g.setColor(Color.black);
    g.fillRect(0, 0, _WIDTH, _HEIGHT);

    player.render(g);
    enemy.render(g);
    ball.render(g);

    g = bs.getDrawGraphics();

    g.drawImage(layer, 0, 0, _WIDTH, _HEIGHT, null);
    bs.show();
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double ns = 1000000000 / maxFPS;
    double delta = 0;

    while (isRunning) {
      long now = System.nanoTime();
      delta = (now - lastTime) / ns;
      deltaTime = delta;

      if (delta >= 1) {
        update();
        render();
        delta = 0;
        lastTime = now;
      }

    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      player.up = false;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      player.down = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      player.up = true;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      player.down = true;
    }
  }
}