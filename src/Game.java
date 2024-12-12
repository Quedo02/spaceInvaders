import javax.swing.*;

public class Game extends JFrame {
    public Game() {
        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);

        // Iniciar el hilo del juego
        new Thread(new GameThread(panel)).start();
    }
}
