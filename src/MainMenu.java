import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {//cpnstructor que crea la interfaz del menu 
        setTitle("Space Invaders");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {//genera el boton que permite iniciar el juego.
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game();
                dispose(); // Cerrar el menú principal
            }
        });

        add(startButton);
        setVisible(true);
    }

    public static void main(String[] args) {//inicia el programa con el menu principal
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}
