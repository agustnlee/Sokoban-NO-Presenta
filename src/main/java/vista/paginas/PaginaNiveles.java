package vista.paginas;

import vista.juego.BotonNivel;
import vista.utils.BotonImagen;
import vista.utils.CargadorFuente;
import vista.utils.ImagenFondo;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PaginaNiveles extends JPanel {

    private static final int ANCHO           = 1920;
    private static final int ALTO            = 1080;
    private static final int ANCHO_TITULO    = 300;
    private static final int ALTO_TITULO     = 150;
    private static final int PADDING_GRILLA  = 40;

    private final List<BotonNivel> botonesNivel = new ArrayList<>();
    private final BotonImagen      btnVolver;

    private Runnable         onVolver;
    private java.util.function.IntConsumer onNivelSeleccionado;

    /**
     * @param cantidadNiveles  cuántos botones de nivel generar
     * @param pathsScreenshots paths a los screenshots, índice 0 = nivel 1
     */
    public PaginaNiveles(int cantidadNiveles, List<String> pathsScreenshots) {
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setSize(ANCHO, ALTO);
        setOpaque(false);

        // Fondo
        ImagenFondo fondo = new ImagenFondo("/imagenes/fondos/niveles.png");
        fondo.setBounds(0, 0, ANCHO, ALTO);



        // Título
        JLabel titulo = new JLabel("NIVELES", JLabel.CENTER);
        titulo.setFont(CargadorFuente.cargar("/fuentes/special_elite.ttf", Font.PLAIN, 48f));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds((ANCHO - ANCHO_TITULO) / 2, 25, ANCHO_TITULO, ALTO_TITULO);
        add(titulo);

        // Grilla de niveles
        JPanel grilla = construirGrilla(cantidadNiveles, pathsScreenshots);
        // Centrada horizontalmente, debajo del título
        int grillaY = 25 + ALTO_TITULO + PADDING_GRILLA;
        grilla.setBounds(
            (ANCHO - grilla.getPreferredSize().width)  / 2,
            grillaY,
            grilla.getPreferredSize().width,
            grilla.getPreferredSize().height
        );
        add(grilla);

        // Botón volver
        btnVolver = new BotonImagen(
            "/imagenes/hud/menu.png",
            "/imagenes/hud/menu_hover.png" //CHEQUEA PATH
        );
        btnVolver.setPreferredSize(new Dimension(160, 160));
        btnVolver.setBounds(60, ALTO - 200, 160, 160);
        add(btnVolver);

        add(fondo);

        configurarListeners();
    }

    // Callbacks


    public void setOnVolver             (Runnable r)                        { this.onVolver              = r; }
    public void setOnNivelSeleccionado  (java.util.function.IntConsumer c)  { this.onNivelSeleccionado   = c; }

    // Privados

    private JPanel construirGrilla(int cantidadNiveles, List<String> paths) {
        // FlowLayout con wrap automático
        JPanel grilla = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        grilla.setOpaque(false);

        for (int i = 0; i < cantidadNiveles; i++) {
            final int numeroNivel = i + 1;
            String path = (paths != null && i < paths.size()) ? paths.get(i) : null;
            BotonNivel btn = new BotonNivel(numeroNivel, path);
            btn.setOnClick(() -> {
                if (onNivelSeleccionado != null) onNivelSeleccionado.accept(numeroNivel);
            });
            botonesNivel.add(btn);
            grilla.add(btn);
        }

        grilla.setPreferredSize(grilla.getLayout() instanceof FlowLayout
            ? calcularTamanoGrilla(cantidadNiveles)
            : grilla.getPreferredSize()
        );

        return grilla;
    }

    private java.awt.Dimension calcularTamanoGrilla(int cantidad) {
        int porFila    = Math.min(cantidad, 5);
        int filas      = (int) Math.ceil((double) cantidad / porFila);
        int anchoTotal = porFila * (225 + 25) + 25;
        int altoTotal  = filas  * (225 + 25) + 25;
        return new java.awt.Dimension(anchoTotal, altoTotal);
    }

    private void configurarListeners() {
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onVolver != null) onVolver.run();
            }
        });
    }
}