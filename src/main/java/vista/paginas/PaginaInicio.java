package vista.paginas;

import vista.utils.BotonImagen;
import vista.utils.ImagenFondo;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaginaInicio extends JPanel {

    private static final int ANCHO        = 1920;
    private static final int ALTO         = 1080;
    private static final int ANCHO_BOTON  = 200;
    private static final int ALTO_BOTON   = 100;

    // Sub-componentes
    private final BotonImagen btnJugar;
    private final BotonImagen btnNiveles;
    private final BotonImagen btnSalir;

    // Callbacks
    private Runnable onJugar;
    private Runnable onNiveles;
    private Runnable onSalir;

    public PaginaInicio() {
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setSize(ANCHO, ALTO);
        setOpaque(false);

        // Fondo
        ImagenFondo fondo = new ImagenFondo("/imagenes/fondos/inicio.png");
        fondo.setBounds(0, 0, ANCHO, ALTO);


        // Botones
        btnJugar   = crearBoton("jugar");
        btnNiveles = crearBoton("niveles");
        btnSalir   = crearBoton("salir");

        // Panel central con BoxLayout vertical
        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        btnJugar.setAlignmentX(CENTER_ALIGNMENT);
        btnNiveles.setAlignmentX(CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(CENTER_ALIGNMENT);

        panelCentral.add(Box.createVerticalGlue());
        panelCentral.add(Box.createVerticalStrut(40));
        panelCentral.add(btnJugar);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(btnNiveles);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(btnSalir);
        panelCentral.add(Box.createVerticalGlue());

        // Centrado horizontal: x = (1920 - 200) / 2 = 860
        int panelAncho = ANCHO_BOTON + 40;
        int panelAlto  = ALTO;
        panelCentral.setBounds((ANCHO - panelAncho) / 2, 0, panelAncho, panelAlto);

        add(panelCentral);
        add(fondo);

        configurarListeners();
    }

    // Callbacks


    public void setOnJugar  (Runnable r) { this.onJugar   = r; }
    public void setOnNiveles(Runnable r) { this.onNiveles = r; }
    public void setOnSalir  (Runnable r) { this.onSalir   = r; }


    // Privados
  

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
        btnJugar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onJugar != null) onJugar.run();
            }
        });
        btnNiveles.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onNiveles != null) onNiveles.run();
            }
        });
        btnSalir.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onSalir != null) onSalir.run();
            }
        });
    }
}