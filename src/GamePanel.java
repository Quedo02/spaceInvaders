import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isRunning = true;
    private int collisionCount = 0;
    private int wave = 1;
    private int lives = 3;
    private Image background;
    private Clip musicClip;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        try {
            var imagePath = getClass().getResource("/resources/background.png");
            background = new ImageIcon(imagePath).getImage();
            playMusic("/resources/musica.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeGame();
    }

    private void initializeGame() {
        player = new Player(375, 500);
        collisionCount = 0;
        wave = 1;
        lives = 3;
        spawnEnemies();
        isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void restartGame() {
        initializeGame();
        playMusic("/resources/musica.wav");
    }

    private void spawnEnemies() {
        enemies.clear();
        for (int i = 0; i < wave && i < 15; i++) {
            enemies.add(new Enemy((i * 50) % 800, (i / 15) * 50));
        }
    }

    public void updateGame() {
        player.update();
        bullets.forEach(Bullet::update);
        Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());

        for (Enemy enemy : enemies) {
            enemy.update(bounds);
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (bullet.getBounds().intersects(enemies.get(j).getBounds())) {
                    bullets.remove(i);
                    enemies.remove(j);
                    collisionCount++;
                    playSound("/resources/disparo.wav");
                    break;
                }
            }
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).getBounds().intersects(player.getBounds())) {
                enemies.remove(i);
                lives--;
                if (lives == 0) {
                    isRunning = false;
                    SwingUtilities.invokeLater(() -> new GameOver(this, collisionCount, wave));
                    stopMusic();
                }
            }
        }

        if (enemies.isEmpty()) {
            wave++;
            spawnEnemies();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        player.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Naves destruidas: " + collisionCount, 10, 20);
        g.drawString("Vidas: " + lives, 10, 40);
        g.drawString("Oleada: " + wave, 10, 60);
    }

    public void addBullet() {
        bullets.add(new Bullet(player.getX() + 20, player.getY()));
        playSound("/resources/disparo.wav");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> player.setDirection(-1, 0);
            case KeyEvent.VK_RIGHT -> player.setDirection(1, 0);
            case KeyEvent.VK_UP -> player.setDirection(0, -1);
            case KeyEvent.VK_DOWN -> player.setDirection(0, 1);
            case KeyEvent.VK_SPACE -> addBullet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.setDirection(0, 0);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void playSound(String soundFile) {
        try {
            var soundURL = getClass().getResource(soundFile);
            if (soundURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMusic(String musicFile) {
        try {
            var musicURL = getClass().getResource(musicFile);
            if (musicURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicURL);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
        }
    }
}
