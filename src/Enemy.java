import java.awt.*;
import javax.swing.ImageIcon;

public class Enemy {
    private int x, y;
    private int dx = 2, dy = 1;
    private Image enemyImage;

    public Enemy(int x, int y) {//constructor de los enemigos
        this.x = x;
        this.y = y;
        this.enemyImage = new ImageIcon(getClass().getResource("/resources/enemy.png")).getImage();
    }

    public void update(Rectangle bounds) {// actualiza la posicion del enemigo determinada por los limites del panel
        x += dx;
        y += dy;

        if (x <= 0 || x >= bounds.width - 40) {//en caso de llegar a un limite horizontal cambia el valor del desplazamiento al inverso
            dx = -dx;
        }

        if (y <= 0 || y >= bounds.height - 40) {//en caso de llegar a un limite vertical cambia el valor del desplazamiento al inverso
            dy = -dy;
        }
    }

    public void reverseVerticalDirection() {//invierte la direccion del enemigo en vertical
        dy = -dy;
    }

    public void draw(Graphics g) {//funcion para dibujar al enemigo
        g.drawImage(enemyImage, x, y, 40, 20, null);
    }

    public Rectangle getBounds() {//funcion que determina la hitbox
        return new Rectangle(x, y, 40, 20);
    }

    public int getY() {//retorna la coordenada vertical del enemigo
        return y;
    }
}
