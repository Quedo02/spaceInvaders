import javax.swing.*;

public class GameOver extends JFrame {
    public GameOver() {
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
