import java.awt.*;
import javax.swing.ImageIcon;

public class Enemy {
    private int x, y;
    private int dx = 2, dy = 1;
    private Image enemyImage;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.enemyImage = new ImageIcon(getClass().getResource("/resources/enemy.png")).getImage();
    }

    public void update(Rectangle bounds) {
        x += dx;
        y += dy;

        if (x <= 0 || x >= bounds.width - 40) {
            dx = -dx;
        }

        if (y <= 0 || y >= bounds.height - 40) {
            dy = -dy;
        }
    }

    public void reverseVerticalDirection() {
        dy = -dy;
    }

    public void draw(Graphics g) {
        g.drawImage(enemyImage, x, y, 40, 20, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 20);
    }

    public int getY() {
        return y;
    }
}
