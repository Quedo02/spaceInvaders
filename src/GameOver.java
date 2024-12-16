import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {
    public GameOver(Game game, int score, int wave) {//constructor de la ventana que muestra las estadisticas del juego
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(gameOverLabel);

        JLabel scoreLabel = new JLabel("Puntuación: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(scoreLabel);

        JLabel waveLabel = new JLabel("Oleada alcanzada: " + wave, SwingConstants.CENTER);
        waveLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(waveLabel);

        JButton restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(e -> {//boton para reiniciar el juego al presionarlo
            dispose();
            game.restartGame(); // Llamar al reinicio del juego desde la clase principal
        });
        panel.add(restartButton);

        add(panel);
        setVisible(true);
    }
}
