import java.awt.*;

public class Enemy {
    private int x, y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += 1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 20);
    }

    public int getY() {
        return y;
    }
}
