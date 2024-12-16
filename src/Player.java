import java.awt.*;
import javax.swing.ImageIcon;

public class Player {
    private int x, y, directionX = 0, directionY = 0;
    private final Image playerImage;

    public Player(int x, int y) {//constructor que permite la creacion del jugador
        this.x = x;
        this.y = y;
        this.playerImage = new ImageIcon(getClass().getResource("/resources/player.png")).getImage();
    }

    public void update() {//Funcion que actualiza la posicion del jugador
        x += directionX * 5;
        y += directionY * 5;
        x = Math.max(0, Math.min(760, x));
        y = Math.max(0, Math.min(580, y));
    }

    public void setDirection(int directionX, int directionY) {//establece la dirreccion del movimiento del jugador
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void draw(Graphics g) {//Dibuja en el panel
        g.drawImage(playerImage, x, y, 40, 20, null);
    }
    // retorna las coordenadas del jugador
    public int getX() {
        return x;
    }

    public int getY() {//
        return y;
    }
    //Retorna la hitbox del jugador
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 20);
    }
}
