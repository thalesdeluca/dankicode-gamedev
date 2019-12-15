import java.awt.Graphics;
import java.awt.Color;

public class Enemy {
  private int y;
  private final int x = Game._WIDTH - 30;
  private final int HEIGHT = 60;
  private final int WIDTH = 8;

  private final int speed = 4;

  public Enemy() {
    y = (int) ((Game._HEIGHT / 2.0) + (HEIGHT / 3.0));
  }

  public void update() {
    if (Ball.speedY < 0 && y > 0) {
      y -= speed;
    } else if (Ball.speedY > 0 && y < Game._HEIGHT + (HEIGHT / 2)) {
      y += speed;
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, WIDTH, HEIGHT);
  }
}