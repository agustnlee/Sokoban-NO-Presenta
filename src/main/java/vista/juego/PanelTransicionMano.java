package vista.juego;

import vista.utils.CargadorImagen;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PanelTransicionMano extends JPanel {

    private static final int DURACION_ENTRADA_MS = 800;
    private static final int DURACION_GARABATO_MS = 1500;
    private static final int DURACION_SALIDA_MS = 800;
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
        if (fase != Fase.INACTIVO) {
            return;
        }

        this.onPuntoMedio = onPuntoMedio;
        this.onTerminado = onTerminado;
        this.puntoMedioDisparado = false;

        fase = Fase.ENTRANDO;
        progresoMs = 0f;

        setVisible(true);

        timer = new Timer(INTERVALO_TICK_MS, e -> tick());
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
            if (onPuntoMedio != null) {
                onPuntoMedio.run();
            }
        }
    }

    private void finalizar() {
        if (timer != null) {
            timer.stop();
        }

        fase = Fase.INACTIVO;
        setVisible(false);

        Runnable callback = onTerminado;
        onTerminado = null;

        if (callback != null) {
            callback.run();
        }
    }

    public boolean estaActivo() {
        return fase != Fase.INACTIVO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fase == Fase.INACTIVO) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        float t;
        int manoY;

        switch (fase) {

            case ENTRANDO:
                // Desde abajo hasta su posición final.
                t = easeOut(progresoMs / DURACION_ENTRADA_MS);
                manoY = (int) (alto - t * alto);

                dibujarMano(g2d, 0, manoY, ancho, alto);
                break;

            case GARABATO:
                if (imagenGarabato != null) {
                    g2d.drawImage(imagenGarabato, 0, 0, ancho, alto, null);
                }

                dibujarMano(g2d, 0, 0, ancho, alto);
                break;

            case SALIENDO:
                // Baja hasta desaparecer.
                t = easeIn(progresoMs / DURACION_SALIDA_MS);
                manoY = (int) (t * alto);

                dibujarMano(g2d, 0, manoY, ancho, alto);
                break;

            case INACTIVO:
                break;
        }

        g2d.dispose();
    }

    private void dibujarMano(Graphics2D g2d, int x, int y, int ancho, int alto) {
        if (imagenMano != null) {
            g2d.drawImage(imagenMano, x, y, ancho, alto, null);
        }
    }

    private float easeOut(float t) {
        return 1f - (1f - t) * (1f - t);
    }

    private float easeIn(float t) {
        return t * t;
    }
}