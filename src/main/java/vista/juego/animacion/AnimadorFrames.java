package vista.juego.animacion;

import javax.swing.Timer;
import java.awt.image.BufferedImage;

public class AnimadorFrames {

    private final BufferedImage[] frames;   // exactamente 2 frames (para movimiento jugador y fondo por ejemplo)
    private int     frameActual  = 0;
    private boolean corriendo    = false;
    private final Timer timer;

    /**
     * @param frame0       imagen en reposo / frame par
     * @param frame1       imagen alternada / frame impar
     * @param intervaloMs  cada cuántos ms cambia el frame (ej: 300)
     */
    public AnimadorFrames(BufferedImage frame0, BufferedImage frame1, int intervaloMs) {
        this.frames = new BufferedImage[]{ frame0, frame1 };
        this.timer  = new Timer(intervaloMs, e -> {
            frameActual = (frameActual + 1) % 2;
        });
        this.timer.setRepeats(true);
    }

    public void start() {
        if (!corriendo) {
            corriendo = true;
            timer.start();
        }
    }

    public void stop() {
        corriendo    = false;
        frameActual  = 0;
        timer.stop();
    }

    /** Detiene sin resetear al frame 0, útil para pausar mid-anim */
    public void pausar() {
        corriendo = false;
        timer.stop();
    }

    public void reanudar() {
        if (!corriendo) {
            corriendo = true;
            timer.start();
        }
    }

    public BufferedImage getFrameActual() {
        return frames[frameActual];
    }

    public boolean isCorriendo() { return corriendo; }
}