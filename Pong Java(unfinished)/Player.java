import java.awt.Color;
import java.awt.Graphics;

public class Player {
  private int y;
  private final int x = 30;
  private final int HEIGHT = 60;
  private final int WIDTH = 8;

  private final int speed = 4;

  public boolean up;
  public boolean down;

  public Player() {
    y = (int) ((Game._HEIGHT / 2.0) + (HEIGHT / 3.0));
  }

  public void update() {
    if (up && y > 0) {
      y -= speed;
    } else if (down && y < Game._HEIGHT + (HEIGHT / 2)) {
      y += speed;
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, WIDTH, HEIGHT);
  }
}