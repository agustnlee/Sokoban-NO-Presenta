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

public class OverlayPausa extends OverlayOscuro {

    private static final int ANCHO_BOTON = 200;
    private static final int ALTO_BOTON  = 80;

    private final BotonImagen btnReanudar;
    private final BotonImagen btnReiniciar;
    private final BotonImagen btnMenu;

    private Runnable onReanudar;
    private Runnable onReiniciar;
    private Runnable onMenu;

    public OverlayPausa(int ancho, int alto) {
        super(ancho, alto);

        btnReanudar  = crearBoton("reanudar");
        btnReiniciar = crearBoton("reiniciar"); //TODO CHEQUEAR PATH
        btnMenu = crearBoton("volver");

        int panelAncho = 300;
        int panelAlto  = 400;

        JPanel contenido = construirContenido();
        contenido.setBounds(
            (ancho - panelAncho) / 2,
            (alto  - panelAlto)  / 2,
            panelAncho,
            panelAlto
        );
        add(contenido);

        configurarListeners();
    }

    public void setOnReanudar(Runnable r)  { this.onReanudar = r; }
    public void setOnReiniciar(Runnable r) { this.onReiniciar = r; }
    public void setOnMenu(Runnable r)      { this.onMenu = r; }

    private JPanel construirContenido() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("PAUSA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        btnReanudar.setAlignmentX(CENTER_ALIGNMENT);
        btnReiniciar.setAlignmentX(CENTER_ALIGNMENT);
        btnMenu.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(30));
        panel.add(btnReanudar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnReiniciar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnMenu);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private BotonImagen crearBoton(String nombre) {
        BotonImagen btn = new BotonImagen(
            "/imagenes/ui/btn_" + nombre + ".png",
            "/imagenes/ui/btn_" + nombre + "_hover.png"
        );
        btn.setPreferredSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        btn.setMinimumSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        btn.setMaximumSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        return btn;
    }

    private void configurarListeners() {
        btnReanudar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onReanudar != null) onReanudar.run();
            }
        });
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