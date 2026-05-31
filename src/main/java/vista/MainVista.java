package vista;

import vista.overlays.OverlayDerrota;
import vista.overlays.OverlayPausa;
import vista.overlays.OverlayVictoria;
import vista.paginas.PaginaInicio;
import vista.paginas.PaginaJuego;
import vista.paginas.PaginaNiveles;
import vista.utils.UtilVisibilidad;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainVista {


    private static final int ANCHO = 1920;
    private static final int ALTO  = 1080;

    // Capas del JLayeredPane
    private static final Integer CAPA_PAGINAS  = JLayeredPane.DEFAULT_LAYER;     // 0
    private static final Integer CAPA_OVERLAYS = JLayeredPane.MODAL_LAYER;       // 200

    private final JFrame       frame;
    private final JLayeredPane layers;


    // Páginas
    private final PaginaInicio   paginaInicio;
    private final PaginaNiveles  paginaNiveles;
    private final PaginaJuego    paginaJuego;


    // Overlays
    private final OverlayPausa   overlayPausa;
    private final OverlayVictoria overlayVictoria;
    private final OverlayDerrota overlayDerrota;

    // Constructor

    /**
     * @param cantidadNiveles   total de niveles del juego
     * @param pathsScreenshots  paths a los screenshots de cada nivel (puede ser null)
     */
    public MainVista(int cantidadNiveles, List<String> pathsScreenshots) {
        this.frame = new JFrame("Sokoban");
        this.frame.setSize(ANCHO, ALTO);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalida();
            }
        });

        this.layers = frame.getLayeredPane();

        // instancia de paginas
        paginaInicio  = new PaginaInicio();
        paginaNiveles = new PaginaNiveles(cantidadNiveles, pathsScreenshots);
        paginaJuego   = new PaginaJuego();

        // instancia de overlays
        overlayPausa   = new OverlayPausa(ANCHO, ALTO);
        overlayVictoria = new OverlayVictoria(ANCHO, ALTO);
        overlayDerrota = new OverlayDerrota(ANCHO, ALTO);

        // montarlos en el layared pane
        montarPaginas();
        montarOverlays();

        // visibilidad inicial
        UtilVisibilidad.mostrar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        UtilVisibilidad.ocultar(overlayPausa);
        UtilVisibilidad.ocultar(overlayVictoria);
        UtilVisibilidad.ocultar(overlayDerrota);

        // mostrar frame
        this.frame.setVisible(true);
        this.frame.revalidate();
        this.frame.repaint();
    }

    // Navegación — llamada por el controlador

    public void mostrarInicio() {
        UtilVisibilidad.mostrar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        ocultarTodosLosOverlays();
    }

    public void mostrarNiveles() {
        UtilVisibilidad.ocultar(paginaInicio);
        UtilVisibilidad.mostrar(paginaNiveles);
        UtilVisibilidad.ocultar(paginaJuego);
        ocultarTodosLosOverlays();
    }

    public void mostrarJuego() {
        UtilVisibilidad.ocultar(paginaInicio);
        UtilVisibilidad.ocultar(paginaNiveles);
        UtilVisibilidad.mostrar(paginaJuego);
        ocultarTodosLosOverlays();
    }

  
    // Overlays — llamada por el controlador
    public void mostrarPausa() {
        UtilVisibilidad.mostrar(overlayPausa);
    }

    public void ocultarPausa() {
        UtilVisibilidad.ocultar(overlayPausa);
    }

    public void mostrarVictoria(int puntaje) {
        overlayVictoria.setPuntaje(puntaje);
        UtilVisibilidad.mostrar(overlayVictoria);
    }

    public void ocultarVictoria() {
        UtilVisibilidad.ocultar(overlayVictoria);
    }

    public void mostrarDerrota(String motivo) {
        overlayDerrota.setMotivo(motivo);
        UtilVisibilidad.mostrar(overlayDerrota);
    }

    public void ocultarDerrota() {
        UtilVisibilidad.ocultar(overlayDerrota);
    }

    public void ocultarTodosLosOverlays() {
        UtilVisibilidad.ocultar(overlayPausa);
        UtilVisibilidad.ocultar(overlayVictoria);
        UtilVisibilidad.ocultar(overlayDerrota);
    }


    // Getters — para conectar en controler compoentes y callbacks de funciones


    public JFrame          getFrame()          { return frame;          }
    public PaginaInicio    getPaginaInicio()    { return paginaInicio;   }
    public PaginaNiveles   getPaginaNiveles()   { return paginaNiveles;  }
    public PaginaJuego     getPaginaJuego()     { return paginaJuego;    }
    public OverlayPausa    getOverlayPausa()    { return overlayPausa;   }
    public OverlayVictoria getOverlayVictoria() { return overlayVictoria;}
    public OverlayDerrota  getOverlayDerrota()  { return overlayDerrota; }

    // Privados


    private void montarPaginas() {
        paginaInicio .setBounds(0, 0, ANCHO, ALTO);
        paginaNiveles.setBounds(0, 0, ANCHO, ALTO);
        paginaJuego  .setBounds(0, 0, ANCHO, ALTO);

        layers.add(paginaInicio,   CAPA_PAGINAS);
        layers.add(paginaNiveles,  CAPA_PAGINAS);
        layers.add(paginaJuego,    CAPA_PAGINAS);
    }

    private void montarOverlays() {
        overlayPausa   .setBounds(0, 0, ANCHO, ALTO);
        overlayVictoria.setBounds(0, 0, ANCHO, ALTO);
        overlayDerrota .setBounds(0, 0, ANCHO, ALTO);

        layers.add(overlayPausa,    CAPA_OVERLAYS);
        layers.add(overlayVictoria, CAPA_OVERLAYS);
        layers.add(overlayDerrota,  CAPA_OVERLAYS);
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