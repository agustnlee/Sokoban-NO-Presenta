package vista.juego;

import vista.utils.CargadorImagen;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PanelTransicionMano extends JPanel {

    private static final int DURACION_ENTRADA_MS = 350;
    private static final int DURACION_GARABATO_MS = 450;
    private static final int DURACION_SALIDA_MS = 350;
    private static final int INTERVALO_TICK_MS = 16;

    private enum Fase { INACTIVO, ENTRANDO, GARABATO, SALIENDO }

    private final BufferedImage imagenMano;
    private final BufferedImage imagenGarabato;

    private Fase fase = Fase.INACTIVO;
    private float progresoMs = 0f;
    private Timer timer;

    private Runnable onPuntoMedio;
    private Runnable onTerminado;
    private boolean puntoMedioDisparado;

    public PanelTransicionMano() {
        setOpaque(false);
        CargadorImagen cargador = CargadorImagen.getInstancia();
        this.imagenMano = cargador.cargar("/imagenes/transicion/mano.png");
        this.imagenGarabato = cargador.cargar("/imagenes/transicion/garabato.png");
    }

    public void iniciar(Runnable onPuntoMedio, Runnable onTerminado) {
        if (fase != Fase.INACTIVO) return;

        this.onPuntoMedio = onPuntoMedio;
        this.onTerminado = onTerminado;
        this.puntoMedioDisparado = false;
        this.fase = Fase.ENTRANDO;
        this.progresoMs = 0f;

        setVisible(true);

        timer = new Timer(INTERVALO_TICK_MS, e -> tick());
        timer.setRepeats(true);
        timer.start();
    }

    private void tick() {
        progresoMs += INTERVALO_TICK_MS;

        switch (fase) {
            case ENTRANDO:
                if (progresoMs >= DURACION_ENTRADA_MS) {
                    fase = Fase.GARABATO;
                    progresoMs = 0f;
                    dispararPuntoMedio();
                }
                break;
            case GARABATO:
                if (progresoMs >= DURACION_GARABATO_MS) {
                    fase = Fase.SALIENDO;
                    progresoMs = 0f;
                }
                break;
            case SALIENDO:
                if (progresoMs >= DURACION_SALIDA_MS) {
                    finalizar();
                    return;
                }
                break;
            case INACTIVO:
                return;
        }

        repaint();
    }

    private void dispararPuntoMedio() {
        if (!puntoMedioDisparado) {
            puntoMedioDisparado = true;
            if (onPuntoMedio != null) onPuntoMedio.run();
        }
    }

    private void finalizar() {
        timer.stop();
        fase = Fase.INACTIVO;
        setVisible(false);
        Runnable callback = onTerminado;
        onTerminado = null;
        if (callback != null) callback.run();
    }

    public boolean estaActivo() {
        return fase != Fase.INACTIVO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fase == Fase.INACTIVO) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        float t;
        int manoX;

        switch (fase) {
            case ENTRANDO:
                t = easeOut(progresoMs / DURACION_ENTRADA_MS);
                manoX = (int) (ancho - t * ancho);
                dibujarMano(g2d, manoX, ancho, alto);
                break;
            case GARABATO:
                dibujarMano(g2d, 0, ancho, alto);
                if (imagenGarabato != null) {
                    g2d.drawImage(imagenGarabato, 0, 0, ancho, alto, null);
                }
                break;
            case SALIENDO:
                t = easeIn(progresoMs / DURACION_SALIDA_MS);
                manoX = (int) (t * ancho);
                dibujarMano(g2d, manoX, ancho, alto);
                break;
            case INACTIVO:
                break;
        }

        g2d.dispose();
    }

    private void dibujarMano(Graphics2D g2d, int x, int ancho, int alto) {
        if (imagenMano != null) {
            g2d.drawImage(imagenMano, x, 0, ancho, alto, null);
        }
    }

    private float easeOut(float t) { return 1 - (1 - t) * (1 - t); }
    private float easeIn(float t) { return t * t; }
}