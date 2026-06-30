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

public class OverlayDerrota extends OverlayOscuro {

    private static final int ANCHO_BOTON = 200;
    private static final int ALTO_BOTON  = 80;
    private static final int PANEL_ANCHO = 400;
    private static final int PANEL_ALTO  = 420;

    private final JLabel      lblMotivo;
    private final BotonImagen btnReiniciar;
    private final BotonImagen btnMenu;

    private Runnable onReiniciar;
    private Runnable onMenu;

    public OverlayDerrota(int ancho, int alto) {
        super(ancho, alto);

        lblMotivo    = new JLabel("", JLabel.CENTER);
        btnReiniciar = crearBoton("reiniciar");
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

    
 
    public void setMotivo(String motivo) {
        lblMotivo.setText(motivo);
    }

    public void setOnReiniciar(Runnable r) { this.onReiniciar = r; }
    public void setOnMenu     (Runnable r) { this.onMenu      = r; }



    private JPanel construirContenido() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(60, 10, 10, 210));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("DERROTA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(new Color(220, 50, 50));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        lblMotivo.setFont(new Font("Arial", Font.PLAIN, 22));
        lblMotivo.setForeground(new Color(200, 200, 200));
        lblMotivo.setAlignmentX(CENTER_ALIGNMENT);

        btnReiniciar.setAlignmentX(CENTER_ALIGNMENT);
        btnMenu.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblMotivo);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnReiniciar);
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
        btnReiniciar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onReiniciar != null) onReiniciar.run();
            }
        });
        btnMenu.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onMenu != null) onMenu.run();
            }
        });
    }
}