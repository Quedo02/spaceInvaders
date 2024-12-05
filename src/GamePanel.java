import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isRunning = true; // Definición del estado del juego

    public GamePanel() {
        setBackground(Color.BLACK);
        player = new Player(375, 500);
        spawnEnemies();

        addKeyListener(this);
        setFocusable(true);
    }

    public boolean isRunning() { // Método getter para isRunning
        return isRunning;
    }

    public void spawnEnemies() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                enemies.add(new Enemy(50 + i * 100, 50 + j * 50));
            }
        }
    }

    public void updateGame() {
        player.update();
        bullets.forEach(Bullet::update);
        enemies.forEach(Enemy::update);

        // Check collisions
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (bullet.getBounds().intersects(enemies.get(j).getBounds())) {
                    bullets.remove(i);
                    enemies.remove(j);
                    break;
                }
            }
        }

        // Game Over condition
        if (enemies.stream().anyMatch(e -> e.getY() > 500)) {
            isRunning = false; // Cambiar el estado del juego
            SwingUtilities.invokeLater(() -> new GameOver());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        bullets.forEach(b -> b.draw(g));
        enemies.forEach(e -> e.draw(g));
    }

    public void addBullet() {
        bullets.add(new Bullet(player.getX() + 20, player.getY()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> player.setDirection(-1);
            case KeyEvent.VK_RIGHT -> player.setDirection(1);
            case KeyEvent.VK_SPACE -> addBullet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.setDirection(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
