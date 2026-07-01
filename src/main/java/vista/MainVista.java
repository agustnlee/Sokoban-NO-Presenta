package vista;

import vista.overlays.OverlayCuentaAtras;
import vista.overlays.OverlayDerrota;
import vista.overlays.OverlayPausa;
import vista.overlays.OverlayVictoria;
import vista.paginas.PaginaInicio;
import vista.paginas.PaginaJuego;
import vista.paginas.PaginaNiveles;
import vista.utils.UtilVisibilidad;
import vista.sonido.GestorSonido;
import vista.sonido.Sonido;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainVista {

    private static final int ANCHO = 1920;
    private static final int ALTO  = 1080;

    private static final Integer CAPA_PAGINAS  = JLayeredPane.DEFAULT_LAYER;
    private static final Integer CAPA_OVERLAYS = JLayeredPane.MODAL_LAYER;

    private final JFrame       frame;
    private final JLayeredPane layers;

    private final PaginaInicio   paginaInicio;
    private final PaginaNiveles  paginaNiveles;
    private final PaginaJuego    paginaJuego;

    private final OverlayPausa       overlayPausa;
    private final OverlayVictoria    overlayVictoria;
    private final OverlayDerrota     overlayDerrota;
    private final OverlayCuentaAtras overlayCuentaAtras;

    public MainVista(int cantidadNiveles, List<String> pathsScreenshots) {
        this.frame = new JFrame("Sokoban");
        this.frame.setSize(ANCHO, ALTO);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setIconImage(
            vista.utils.CargadorImagen.getInstancia().cargar("/imagenes/ui/icono.png")
        );
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalida();
            }
        });

        this.layers = frame.getLayeredPane();
        this.layers.setBounds(0, 0, ANCHO, ALTO);

        paginaInicio  = new PaginaInicio();
        paginaNiveles = new PaginaNiveles(cantidadNiveles, pathsScreenshots);
        paginaJuego   = new PaginaJuego();

        overlayPausa       = new OverlayPausa(ANCHO, ALTO);
        overlayVictoria    = new OverlayVictoria(ANCHO, ALTO);
        overlayDerrota      = new OverlayDerrota(ANCHO, ALTO);
        overlayCuentaAtras = new OverlayCuentaAtras(ANCHO, ALTO);

        montarPaginas();
        montarOverlays();

        UtilVisibilidad.mostrar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        UtilVisibilidad.ocultar(overlayPausa);
        UtilVisibilidad.ocultar(overlayVictoria);
        UtilVisibilidad.ocultar(overlayDerrota);
        UtilVisibilidad.ocultar(overlayCuentaAtras);


        this.frame.revalidate();
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    public void mostrarInicio() {
        UtilVisibilidad.mostrar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        ocultarTodosLosOverlays();
        GestorSonido.getInstancia().reproducir(Sonido.FONDO_MISTERIO);
    }

    public void mostrarNiveles() {
        UtilVisibilidad.ocultar(paginaInicio);
        UtilVisibilidad.mostrar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        ocultarTodosLosOverlays();
        GestorSonido.getInstancia().reproducir(Sonido.FONDO_NIVELES);
    }

    public void mostrarJuego() {
        UtilVisibilidad.ocultar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.mostrar(paginaJuego);
        ocultarTodosLosOverlays();
        GestorSonido.getInstancia().reproducir(Sonido.FONDO_JUEGO);
    }
    
    public void mostrarPausa() {
        UtilVisibilidad.mostrar(overlayPausa);
        GestorSonido.getInstancia().detenerMusica();
    }

    public void ocultarPausa() {
        UtilVisibilidad.ocultar(overlayPausa);
        GestorSonido.getInstancia().reproducir(Sonido.FONDO_JUEGO);
    }

    public void mostrarVictoria(int puntaje) {
        overlayVictoria.setPuntaje(puntaje);
        UtilVisibilidad.mostrar(overlayVictoria);
        GestorSonido.getInstancia().detenerMusica();
        GestorSonido.getInstancia().reproducir(Sonido.VICTORIA);
    }

    public void ocultarVictoria() {
        UtilVisibilidad.ocultar(overlayVictoria);
    }

    public void mostrarDerrota(String motivo) {
        overlayDerrota.setMotivo(motivo);
        UtilVisibilidad.mostrar(overlayDerrota);
        GestorSonido.getInstancia().detenerMusica();
        GestorSonido.getInstancia().reproducir(Sonido.DERROTA);
    }

    public void ocultarDerrota() {
        UtilVisibilidad.ocultar(overlayDerrota);
    }

    public void ocultarTodosLosOverlays() {
        UtilVisibilidad.ocultar(overlayPausa);
        UtilVisibilidad.ocultar(overlayVictoria);
        UtilVisibilidad.ocultar(overlayDerrota);
        UtilVisibilidad.ocultar(overlayCuentaAtras);
    }

    public JFrame          getFrame()              { return frame; }
    public PaginaInicio    getPaginaInicio()        { return paginaInicio; }
    public PaginaNiveles   getPaginaNiveles()       { return paginaNiveles; }
    public PaginaJuego     getPaginaJuego()         { return paginaJuego; }
    public OverlayPausa    getOverlayPausa()        { return overlayPausa; }
    public OverlayVictoria getOverlayVictoria()     { return overlayVictoria; }
    public OverlayDerrota  getOverlayDerrota()      { return overlayDerrota; }
    public OverlayCuentaAtras getOverlayCuentaAtras() { return overlayCuentaAtras; }

    private void montarPaginas() {
        paginaInicio .setBounds(0, 0, ANCHO, ALTO);
        paginaNiveles.setBounds(0, 0, ANCHO, ALTO);
        paginaJuego  .setBounds(0, 0, ANCHO, ALTO);

        layers.add(paginaInicio,   CAPA_PAGINAS);
        layers.add(paginaNiveles,  CAPA_PAGINAS);
        layers.add(paginaJuego,    CAPA_PAGINAS);
    }

    private void montarOverlays() {
        overlayPausa.setBounds(0, 0, ANCHO, ALTO);
        overlayVictoria.setBounds(0, 0, ANCHO, ALTO);
        overlayDerrota.setBounds(0, 0, ANCHO, ALTO);
        overlayCuentaAtras.setBounds(0, 0, ANCHO, ALTO);

        layers.add(overlayPausa,       CAPA_OVERLAYS);
        layers.add(overlayVictoria,    CAPA_OVERLAYS);
        layers.add(overlayDerrota,     CAPA_OVERLAYS);
        layers.add(overlayCuentaAtras, CAPA_OVERLAYS);
    }

    private void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(
            frame,
            "¿Seguro que querés salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}