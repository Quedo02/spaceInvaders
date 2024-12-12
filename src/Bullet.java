import java.awt.*;
import javax.swing.ImageIcon;

public class Bullet {
    private int x, y;
    private Image bulletImage;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.bulletImage = new ImageIcon(getClass().getResource("/resources/bullet.png")).getImage();
    }

    public void update() {
        y -= 10; // Movimiento hacia arriba
    }

    public void draw(Graphics g) {
        g.drawImage(bulletImage, x, y, 5, 10, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }

    public int getY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }
}
