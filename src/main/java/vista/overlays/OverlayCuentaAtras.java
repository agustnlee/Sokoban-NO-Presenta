package vista.overlays;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class OverlayCuentaAtras extends OverlayOscuro {

    private static final int DURACION_NUMERO_MS = 700;
    private static final String[] SECUENCIA = {"5", "4", "3", "2", "1", "¡Recrear!"};

    private int indiceActual;
    private Timer timer;
    private Runnable onTerminado;

    public OverlayCuentaAtras(int ancho, int alto) {
        super(ancho, alto);
    }

    public void iniciar(Runnable onTerminado) {
        this.onTerminado = onTerminado;
        this.indiceActual = 0;

        setVisible(true);
        repaint();

        timer = new Timer(DURACION_NUMERO_MS, e -> avanzar());
        timer.setRepeats(true);
        timer.start();
    }

    private void avanzar() {
        indiceActual++;
        if (indiceActual >= SECUENCIA.length) {
            finalizar();
            return;
        }
        repaint();
    }

    private void finalizar() {
        timer.stop();
        setVisible(false);
        Runnable callback = onTerminado;
        onTerminado = null;
        if (callback != null) callback.run();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (indiceActual >= SECUENCIA.length) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String texto = SECUENCIA[indiceActual];
        boolean esUltimo = indiceActual == SECUENCIA.length - 1;

        Font fuente = new Font("Arial", Font.BOLD, esUltimo ? 64 : 120);
        g2d.setFont(fuente);
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(texto, x, y);

        g2d.dispose();
    }
}