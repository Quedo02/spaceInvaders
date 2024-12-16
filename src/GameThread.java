public class GameThread extends Thread {
    private GamePanel gamePanel;
    private boolean running = true;

    public GameThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {//metodo que ejecuta un hilo que actualiza el juego
        while (running) {
            try {
                gamePanel.updateGame();
                gamePanel.repaint();
                Thread.sleep(16); // Aproximadamente 60 FPS
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stopThread() {//Funcion que detiene la ejecucion del hilo anterior
        running = false;
        interrupt(); // Interrumpe el hilo en caso de que esté bloqueado
    }
}
