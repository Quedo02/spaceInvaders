public class GameThread implements Runnable {
    private final GamePanel panel;

    public GameThread(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (panel.isRunning()) {
            panel.updateGame();
            panel.repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
