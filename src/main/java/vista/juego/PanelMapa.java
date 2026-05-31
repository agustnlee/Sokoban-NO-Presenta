package vista.juego;

import vista.datos.EntidadVisual;
import vista.datos.EstadoVisualMapa;
import vista.datos.MovimientoVisual;
import vista.datos.TipoCeldaVisual;
import vista.juego.animacion.AnimacionDeslizamiento;
import vista.juego.animacion.AnimadorFrames;
import vista.juego.animacion.GestorAnimaciones;
import vista.utils.CargadorImagen;
import vista.utils.RenderizadorCelda;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PanelMapa extends JPanel {

    private static final int TAM_CELDA_DEFAULT = 100; // px por celda
    private static final int FPS               = 60;
    private static final int INTERVALO_ANIM_MS = 1000 / FPS;
    private static final int INTERVALO_WALK_MS = 300;

    private EstadoVisualMapa estado;
    private final int tamCelda;


    // Animación de entidades

    private final GestorAnimaciones gestor;
    private final Timer             timerRender;

    // AnimadorFrames por entidad animada
    private AnimadorFrames animJugador;
    private AnimadorFrames animEscalera;

    // Timestamps para deltaMs
    private long ultimoTick = -1;

    // Flag para bloquear input durante animaciones (consultado por controlador)
    private boolean bloqueado = false;

    // Callback que avisa al controlador que terminaron las animaciones
    private Runnable onAnimacionTerminada;


    // Constructor

    public PanelMapa(int filas, int columnas) {
        this(filas, columnas, TAM_CELDA_DEFAULT);
    }

    public PanelMapa(int filas, int columnas, int tamCelda) {
        this.tamCelda = tamCelda;

        int ancho = columnas * tamCelda;
        int alto  = filas   * tamCelda;

        setPreferredSize(new Dimension(ancho, alto));
        setSize(ancho, alto);
        setOpaque(false);
        setLayout(null);

        this.gestor = new GestorAnimaciones(tamCelda);

        // Timer principal: actualiza animaciones y repinta a ~60fps
        this.timerRender = new Timer(INTERVALO_ANIM_MS, e -> onTick());
        this.timerRender.setRepeats(true);

        iniciarAnimadoresDefault();
    }

    // metodos publico

    /**
     * Carga un estado completo del mapa (inicio de nivel o undo).
     * Detiene animaciones en curso y repinta.
     */
    public void aplicarEstado(EstadoVisualMapa estado) {
        this.estado = estado;
        gestor.cancelar();
        bloqueado = false;
        animJugador.stop();
        timerRender.stop();
        repaint();
    }

    /**
     * Recibe un movimiento del controlador, encola las animaciones
     * y bloquea el input hasta que terminen.
     */
    public void aplicarMovimiento(MovimientoVisual mv) {
        if (estado == null || bloqueado) return;

        bloqueado = true;
        animJugador.start();
        ultimoTick = System.currentTimeMillis();

        gestor.encolar(mv, estado, () -> {
            // Se ejecuta en el hilo del Timer (EDT) al terminar todo
            bloqueado = false;
            animJugador.stop();
            timerRender.stop();
            if (onAnimacionTerminada != null) onAnimacionTerminada.run();
            repaint();
        });

        timerRender.start();
    }

    public void setOnAnimacionTerminada(Runnable callback) {
        this.onAnimacionTerminada = callback;
    }

    public boolean isBloqueado() { return bloqueado; }

    public int getTamCelda() { return tamCelda; }

    // Render — 3 pasadas

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (estado == null) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                             RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int filas    = estado.getFilas();
        int columnas = estado.getColumnas();

        // --- Pasada 1: suelo 
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                TipoCeldaVisual tipo = estado.getSuelo(f, c);
                RenderizadorCelda.dibujar(g2d, tipo, f, c, tamCelda);
            }
        }

        // --- Pasada 2: entidades (cajas, escalera, paredes) 
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                EntidadVisual entidad = estado.getEntidad(f, c);
                if (entidad == null) continue;
                if (entidad.getTipo() == TipoCeldaVisual.JUGADOR) continue;

                float px, py;
                if (entidad.isAnimando()) {
                    px = entidad.getPixelX();
                    py = entidad.getPixelY();
                } else {
                    px = c * tamCelda;
                    py = f * tamCelda;
                }

                RenderizadorCelda.dibujarEnPx(g2d, entidad.getTipo(), px, py, tamCelda);

                // Número de empujes restantes sobre caja frágil
                if (esCajaFragil(entidad.getTipo()) && entidad.getEmpujesRestantes() >= 0) {
                    RenderizadorCelda.dibujarContadorFragil(
                        g2d, entidad.getEmpujesRestantes(), px, py, tamCelda
                    );
                }
            }
        }

        // --- Pasada 3: jugador --
        dibujarJugador(g2d, filas, columnas);

        g2d.dispose();
    }

    // Helpers privados


    private void onTick() {
        long ahora = System.currentTimeMillis();
        float deltaMs = (ultimoTick < 0) ? INTERVALO_ANIM_MS : (ahora - ultimoTick);
        ultimoTick = ahora;

        gestor.tick(deltaMs);
        repaint();
    }

    private void dibujarJugador(Graphics2D g2d, int filas, int columnas) {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                EntidadVisual entidad = estado.getEntidad(f, c);
                if (entidad == null) continue;
                if (entidad.getTipo() != TipoCeldaVisual.JUGADOR) continue;

                float px = entidad.isAnimando() ? entidad.getPixelX() : c * tamCelda;
                float py = entidad.isAnimando() ? entidad.getPixelY() : f * tamCelda;

                BufferedImage frameJugador = animJugador.getFrameActual();
                if (frameJugador != null) {
                    g2d.drawImage(frameJugador, (int) px, (int) py,
                                  tamCelda, tamCelda, null);
                }
                return; // solo hay un jugador
            }
        }
    }

    private void iniciarAnimadoresDefault() {
        CargadorImagen cargador = CargadorImagen.getInstancia(); //TODO CHEQUEAR PATH IMG

        BufferedImage jugador0 = cargador.cargar("/imagenes/entidades/jugador_0.png");
        BufferedImage jugador1 = cargador.cargar("/imagenes/entidades/jugador_1.png");
        animJugador = new AnimadorFrames(jugador0, jugador1, INTERVALO_WALK_MS);

        BufferedImage escalera0 = cargador.cargar("/imagenes/entidades/escalera.png");
        BufferedImage escalera1 = cargador.cargar("/imagenes/entidades/escalera.png");
        animEscalera = new AnimadorFrames(escalera0, escalera1, INTERVALO_WALK_MS);
    }

    private boolean esCajaFragil(TipoCeldaVisual tipo) {
        return tipo == TipoCeldaVisual.CAJA_FRAGIL
            || tipo == TipoCeldaVisual.CAJA_FRAGIL_EN_DESTINO;
    }
}