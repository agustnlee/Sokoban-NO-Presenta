package vista.juego;

import vista.utils.BotonImagen;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelDirecciones extends JPanel {

    private static final int TAM_BOTON = 80; // px

    private final BotonImagen btnArriba;
    private final BotonImagen btnAbajo;
    private final BotonImagen btnIzquierda;
    private final BotonImagen btnDerecha;

    private Runnable onArriba;
    private Runnable onAbajo;
    private Runnable onIzquierda;
    private Runnable onDerecha;

    public PanelDirecciones() {
        setOpaque(false);
        setLayout(new GridBagLayout());


        // TODO: cambiar PATH imagenes o agregar imagense

        btnArriba    = new BotonImagen(
            "/imagenes/hud/flecha_arriba.png",
            "/imagenes/hud/flecha_arriba_hover.png"
        );
        btnAbajo     = new BotonImagen(
            "/imagenes/hud/flecha_abajo.png",
            "/imagenes/hud/flecha_abajo_hover.png"
        );
        btnIzquierda = new BotonImagen(
            "/imagenes/hud/flecha_izquierda.png",
            "/imagenes/hud/flecha_izquierda_hover.png"
        );
        btnDerecha   = new BotonImagen(
            "/imagenes/hud/flecha_derecha.png",
            "/imagenes/hud/flecha_derecha_hover.png"
        );

        configurarTamanos();
        configurarLayout();
        configurarListeners();
    }

    // Setters de callbacks — el controlador los conecta

    public void setOnArriba   (Runnable r) { this.onArriba    = r; }
    public void setOnAbajo    (Runnable r) { this.onAbajo     = r; }
    public void setOnIzquierda(Runnable r) { this.onIzquierda = r; }
    public void setOnDerecha  (Runnable r) { this.onDerecha   = r; }

    
    // Helpers privados

    private void configurarTamanos() {
        Dimension dim = new Dimension(TAM_BOTON, TAM_BOTON);
        for (BotonImagen btn : new BotonImagen[]{
                btnArriba, btnAbajo, btnIzquierda, btnDerecha}) {
            btn.setPreferredSize(dim);
            btn.setMinimumSize(dim);
            btn.setMaximumSize(dim);
        }
    }

    private void configurarLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        // Fila 0, col 1 → arriba
        gbc.gridx = 1; gbc.gridy = 0;
        add(btnArriba, gbc);

        // Fila 1, col 0 → izquierda
        gbc.gridx = 0; gbc.gridy = 1;
        add(btnIzquierda, gbc);

        // Fila 1, col 2 → derecha
        gbc.gridx = 2; gbc.gridy = 1;
        add(btnDerecha, gbc);

        // Fila 2, col 1 → abajo
        gbc.gridx = 1; gbc.gridy = 2;
        add(btnAbajo, gbc);
    }

    private void configurarListeners() {
        btnArriba.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onArriba != null) onArriba.run();
            }
        });
        btnAbajo.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onAbajo != null) onAbajo.run();
            }
        });
        btnIzquierda.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onIzquierda != null) onIzquierda.run();
            }
        });
        btnDerecha.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onDerecha != null) onDerecha.run();
            }
        });
    }
}