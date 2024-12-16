import javax.swing.*;

public class Game extends JFrame {
    private GamePanel gamePanel;

    public Game() {//Constructor que inicializa la ventana del juego y comienza el ciclo del juego
        setTitle("Space Shooter");//da nombre al juego
        setSize(800, 600);//determina el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Mostrar la ventana primero
        setVisible(true);

        // Iniciar el juego
        startGame();
    }

    public void startGame() {
        if (gamePanel != null) {
            remove(gamePanel); // Eliminar el panel del juego anterior, si existe
        }

        gamePanel = new GamePanel(this);//permite crear el nuevo panel del juego 
        add(gamePanel);
        revalidate();
        repaint();

        // Iniciar el ciclo del juego en un hilo separado
        Thread gameThread = new Thread(() -> {
            while (gamePanel.isRunning()) {
                gamePanel.updateGame();
                gamePanel.repaint();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void restartGame() {//funcion que permite reiniciar el juego
        startGame();
    }

    public static void main(String[] args) {//Inicia el juego mostrando el menu principal
        SwingUtilities.invokeLater(Game::new);
    }
}
