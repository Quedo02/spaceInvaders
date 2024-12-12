import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {
    public GameOver(GamePanel gamePanel, int score, int wave) {
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

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
        restartButton.addActionListener(e -> dispose());
        panel.add(restartButton);

        add(panel);
        setVisible(true);
    }
}
