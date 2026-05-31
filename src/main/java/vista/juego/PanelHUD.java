package vista.juego;

import vista.utils.BotonImagen;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelHUD extends JPanel {


    // Contadores 
    // TODO: CHEQUEUAR SI AGREGAR MAS LBLS DEPENDIENDO LOGICA DE PUNTAJE
    private final EtiquetaContador lblMovimientos;
    private final EtiquetaContador lblEmpujes;
    private final EtiquetaContador lblNivel;


    // Botones de acción
    private final BotonImagen btnPausa;
    private final BotonImagen btnUndo;
    private final BotonImagen btnReiniciar;
    private final BotonImagen btnMenu;

    // Direcciones
    private final PanelDirecciones panelDirecciones;

    // Callbacks — conectados por el controlador
    private Runnable onPausa;
    private Runnable onUndo;
    private Runnable onReiniciar;
    private Runnable onMenu;

    

    /**
     * @param ancho  ancho del panel en px
     * @param alto   alto del panel en px
     */
    public PanelHUD(int ancho, int alto) {
        setOpaque(false);
        setPreferredSize(new Dimension(ancho, alto));
        setSize(ancho, alto);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Contadores
        lblNivel       = new EtiquetaContador("Nivel",       1);
        lblMovimientos = new EtiquetaContador("Movimientos", 0);
        lblEmpujes     = new EtiquetaContador("Empujes",     0);

        // Botones 
        // TODO: CHEUQEAR PATH IMAGENES O AGREGAR IMAGENES
        btnPausa     = crearBoton("/imagenes/hud/pausa.png",
                                  "/imagenes/hud/pausa_hover.png");
        btnUndo      = crearBoton("/imagenes/hud/undo.png",
                                  "/imagenes/hud/undo_hover.png");
        btnReiniciar = crearBoton("/imagenes/hud/reiniciar.png",
                                  "/imagenes/hud/reiniciar_hover.png");
        btnMenu      = crearBoton("/imagenes/hud/menu.png",
                                  "/imagenes/hud/menu_hover.png");

        // Direcciones
        panelDirecciones = new PanelDirecciones();

        ensamblar();
        configurarListeners();
    }


    // SEtters publigocs
    public void setNivel      (int v) { lblNivel.setValor(v);       }
    public void setMovimientos(int v) { lblMovimientos.setValor(v); }
    public void setEmpujes    (int v) { lblEmpujes.setValor(v);     }

    public void resetearContadores() {
        lblMovimientos.resetear();
        lblEmpujes.resetear();
    }


    // callbacks de botones

    public void setOnPausa    (Runnable r) { this.onPausa     = r; }
    public void setOnUndo     (Runnable r) { this.onUndo      = r; }
    public void setOnReiniciar(Runnable r) { this.onReiniciar = r; }
    public void setOnMenu     (Runnable r) { this.onMenu      = r; }

    // Delega al PanelDirecciones
    public void setOnArriba   (Runnable r) { panelDirecciones.setOnArriba(r);    }
    public void setOnAbajo    (Runnable r) { panelDirecciones.setOnAbajo(r);     }
    public void setOnIzquierda(Runnable r) { panelDirecciones.setOnIzquierda(r); }
    public void setOnDerecha  (Runnable r) { panelDirecciones.setOnDerecha(r);   }


    // Getters de sub-componentes (por si el controlador necesita acceso fino)

    public PanelDirecciones getPanelDirecciones() { return panelDirecciones; }


    // helpers Privados


    private void ensamblar() {
        int gap = 20;

        add(Box.createVerticalStrut(gap));
        add(centrar(lblNivel));
        add(Box.createVerticalStrut(gap));

        add(separador());
        add(Box.createVerticalStrut(gap));

        add(centrar(lblMovimientos));
        add(Box.createVerticalStrut(gap / 2));
        add(centrar(lblEmpujes));
        add(Box.createVerticalStrut(gap));

        add(separador());
        add(Box.createVerticalStrut(gap));

        // Botones de acción en fila
        JPanel filaBotones = new JPanel();
        filaBotones.setOpaque(false);
        filaBotones.add(btnPausa);
        filaBotones.add(btnUndo);
        filaBotones.add(btnReiniciar);
        filaBotones.add(btnMenu);
        add(centrar(filaBotones));

        add(Box.createVerticalStrut(gap));
        add(separador());
        add(Box.createVerticalStrut(gap));

        add(centrar(panelDirecciones));
        add(Box.createVerticalGlue());
    }

    private void configurarListeners() {
        btnPausa.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onPausa != null) onPausa.run();
            }
        });
        btnUndo.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onUndo != null) onUndo.run();
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

    private BotonImagen crearBoton(String pathNormal, String pathHover) {
        BotonImagen btn = new BotonImagen(pathNormal, pathHover);
        btn.setPreferredSize(new Dimension(60, 60));
        btn.setMinimumSize(new Dimension(60, 60));
        btn.setMaximumSize(new Dimension(60, 60));
        return btn;
    }

    private JPanel centrar(java.awt.Component comp) {
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }

    private JSeparator separador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 80));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }
}