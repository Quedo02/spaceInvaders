import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
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

    private final Random random = new Random();

    public GamePanel(JFrame parentFrame) {// Constructor que inicia el panel del juego
        setFocusable(true);
        addKeyListener(this);

        try {//En esta area de carga el fondo y la musica de fondo del juego
            var imagePath = getClass().getResource("/resources/background.png");
            background = new ImageIcon(imagePath).getImage();
            playMusic("/resources/musica.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //H¿Generacion del jugador en la posicion inical y creacion de los enemigos.
        player = new Player(375, 500);
        spawnEnemies();
    }

    public boolean isRunning() {//verificacion de que el gameplay esta en funcionamiento.
        return isRunning;
    }

    private void spawnEnemies() {//Constructor que genera los enemigos en posiciones aleatorias, un enemigo por oleada
        enemies.clear();
        int maxEnemies = Math.min(wave, 15);//Se genera un enemigo equivalente a la oleada llegando a un limite de 15
        for (int i = 0; i < maxEnemies; i++) {//ciclp donde se generan  los enemigos en posiciones aleatorias limitadas por el maximo de enemigos por oleada
            int x = random.nextInt(750); // Ajustado para que los enemigos no se salgan del borde derecho
            int y = random.nextInt(300); // Enemigos aparecen en la mitad superior
            enemies.add(new Enemy(x, y));//se crea el enemigo en las posiciones determinadas anteriormente
        }
    }

    public void updateGame() {// funcion que actualiza las posiciones de los objetos permitiendo visualizar los movimientos
        player.update();//LLamado de funcion de redibujado de jugador
        bullets.forEach(Bullet::update);//LLamado de funcion de redibujado de cada enemigo

        Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());//Determinar los limites de colision de los objetos

        for (Enemy enemy : enemies) {//Permite modificar cada enemigo para que pueda rebotar
            enemy.update(bounds);
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {//funcion que se encarga de la creacion de las balas y su comportamiento
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (bullet.getBounds().intersects(enemies.get(j).getBounds())) {//si la bala choca con un enemigo elimina tanto la bala como el enemigo
                    bullets.remove(i);
                    enemies.remove(j);
                    collisionCount++;//se aumenta el contador de colision/puntaje
                    playSound("/resources/collision.wav");//reproduce sonido de colision.
                    break;
                }
            }
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {//en caso de que el enemigo toque al jugador 
            if (enemies.get(i).getBounds().intersects(player.getBounds())) {
                enemies.remove(i);
                lives--;//contador de vidas se disminuye
                playSound("/resources/collision.wav");//reproduccion de sonido de colision
                if (lives <= 0) {//si el contador de vidas es igual o menor a 0 
                    isRunning = false;//pasa el juego a pausa
                    SwingUtilities.invokeLater(() -> new GameOver((Game) SwingUtilities.getWindowAncestor(this), collisionCount, wave));//envia los valores de colision, puntaje a la pantalla de game over
                    stopMusic();//para la musica
                }
            }
        }

        if (enemies.isEmpty()) {//si no hay enemigos
            wave++;//aumenta el contador de la oleada.
            spawnEnemies();//llama a la funcion de crear enemigos
        }
    }

    @Override
    protected void paintComponent(Graphics g) {// funcion que se encarga de actualizar la ventana de juego
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);//genera el fondo
        player.draw(g);
        for (Enemy enemy : enemies) {//actualizacion para cada enemigo
            enemy.draw(g);//dibuja al enemigo
        }
        for (Bullet bullet : bullets) {//actualza por cada bala
            bullet.draw(g);//dibuja a la bala
        }
        //Se dibuja los indicadores de puntaje, vidas y oleada
        g.setColor(Color.WHITE);
        g.drawString("Naves destruidas: " + collisionCount, 10, 20);
        g.drawString("Vidas: " + lives, 10, 40);
        g.drawString("Oleada: " + wave, 10, 60);
    }

    public void addBullet() {//funcion que crea la bala en la posicion del jugador
        bullets.add(new Bullet(player.getX() + 20, player.getY()));
        playSound("/resources/disparo.wav");//reproduce sonido de disparo
    }

    @Override
    public void keyPressed(KeyEvent e) {//funcion para detectar las flechas y la barra espaciadora para el movimiento y disparo
        switch (e.getKeyCode()) {
            //Cada flecha modifica el la posicion en una coordenada
            case KeyEvent.VK_LEFT -> player.setDirection(-1, 0);
            case KeyEvent.VK_RIGHT -> player.setDirection(1, 0);
            case KeyEvent.VK_UP -> player.setDirection(0, -1);
            case KeyEvent.VK_DOWN -> player.setDirection(0, 1);
            //la barra espaciadora invoca la funcion de agregar bala
            case KeyEvent.VK_SPACE -> addBullet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//funcion al detectar el dejar de presionar no modifica la direccion
        player.setDirection(0, 0);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void playSound(String soundFile) {//funcion encargada de reproducir los sonidos
        try {
            var soundURL = getClass().getResource(soundFile);//se encarga de guardar la direccion del sonido en una variable
            if (soundURL != null) {//si se encuentra el sonido reproduce el sonido
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMusic(String musicFile) {//funcion encargada de reproducir la musica de fondo en bucle
        try {
            var musicURL = getClass().getResource(musicFile);//recibe la direccion de la musica y la pone en una variable 
            if (musicURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicURL);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);//genera el ciclo de reproduccion de la musica 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {//funcion que para el ciclo de la musica
        if (musicClip != null) {
            musicClip.stop();
        }
    }
}
