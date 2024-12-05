import java.awt.*;

public class Player {
    private int x, y, direction = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += direction * 5;
        x = Math.max(0, Math.min(760, x));
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 20);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
