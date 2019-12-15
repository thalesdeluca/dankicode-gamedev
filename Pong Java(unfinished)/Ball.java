import java.awt.Graphics;
import java.awt.Color;

public class Ball {
  public int x, y;
  public static int speedX = 0;
  public static int speedY = 0;
  public boolean isLaunch;

  public Ball() {
    x = Game._WIDTH / 2;
    y = Game._HEIGHT / 2;
  }

  public void hit() {
    speedX = (Math.abs(speedX) + 1) * (speedX > 0 ? -1 : 1);
    speedY = (Math.abs(speedX) + 1) * (speedY > 0 ? -1 : 1);
  }

  public void reset() {
    speedX = 0;
    speedY = 0;
    isLaunch = false;
  }

  public void update() {
    if (!isLaunch) {
      isLaunch = true;
      speedX = -2;
    }
    if (x >= Game._WIDTH || x <= 0) {
      hit();
    }
    if (y >= Game._HEIGHT || y <= 0) {
      hit();
    }
    x += speedX;
    y += speedY;
  }

  public void render(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillOval(x, y, 16, 16);
  }
}