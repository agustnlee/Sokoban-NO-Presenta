package vista.overlays;

import vista.utils.BotonImagen;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverlayVictoria extends OverlayOscuro {

    private static final int ANCHO_BOTON  = 200;
    private static final int ALTO_BOTON   = 80;
    private static final int PANEL_ANCHO  = 400;
    private static final int PANEL_ALTO   = 450;

    private final JLabel      lblPuntaje;
    private final BotonImagen btnSiguiente;
    private final BotonImagen btnMenu;

    private Runnable onSiguiente;
    private Runnable onMenu;

    public OverlayVictoria(int ancho, int alto) {
        super(ancho, alto);

        lblPuntaje   = new JLabel("Puntaje: 0", JLabel.CENTER);
        btnSiguiente = crearBoton("siguiente");
        btnMenu = crearBoton("volver");

        JPanel contenido = construirContenido();
        contenido.setBounds(
            (ancho - PANEL_ANCHO) / 2,
            (alto  - PANEL_ALTO)  / 2,
            PANEL_ANCHO,
            PANEL_ALTO
        );
        add(contenido);

        configurarListeners();
    }


    // publicos

    /** Llamado por el controlador al completar el nivel */
    public void setPuntaje(int puntaje) {
        lblPuntaje.setText("Puntaje: " + puntaje);
    }

    public void setOnSiguiente(Runnable r) { this.onSiguiente = r; }
    public void setOnMenu     (Runnable r) { this.onMenu      = r; }

    // Privados
  

    private JPanel construirContenido() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(20, 60, 20, 210));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("¡VICTORIA!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(new Color(255, 220, 50));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        lblPuntaje.setFont(new Font("Arial", Font.PLAIN, 28));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setAlignmentX(CENTER_ALIGNMENT);

        btnSiguiente.setAlignmentX(CENTER_ALIGNMENT);
        btnMenu.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblPuntaje);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnSiguiente);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnMenu);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private BotonImagen crearBoton(String nombre) {
        BotonImagen btn = new BotonImagen(
            "/imagenes/ui/btn_" + nombre + ".png", //TODO CHEQUEAR PATH IMAGEN
            "/imagenes/ui/btn_" + nombre + "_hover.png"
        );
        btn.setPreferredSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        btn.setMinimumSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        btn.setMaximumSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        return btn;
    }

    private void configurarListeners() {
        btnSiguiente.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onSiguiente != null) onSiguiente.run();
            }
        });
        btnMenu.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onMenu != null) onMenu.run();
            }
        });
    }
}