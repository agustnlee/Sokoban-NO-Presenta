package vista.juego;

import modelo.dto.DTOResultado;
import vista.utils.BotonImagen;
import vista.utils.CargadorFuente;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelHUD extends JPanel {

    private static final String PATH_FUENTE = "/fuentes/special_elite.ttf";

    private final EtiquetaContador lblNivel;
    private final EtiquetaContador lblPuntaje;
    private final EtiquetaContador lblMovimientos;
    private final EtiquetaContador lblUndos;

    private final BotonImagen btnPausa;
    private final BotonImagen btnUndo;

    private final PanelDirecciones panelDirecciones;

    private Runnable onPausa;
    private Runnable onUndo;

    public PanelHUD(int ancho, int alto) {
        setOpaque(false);
        setPreferredSize(new Dimension(ancho, alto));
        setSize(ancho, alto);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //TODO CHECK PATH

        lblNivel       = new EtiquetaContador("Nivel", 1);
        lblPuntaje     = new EtiquetaContador("Puntaje", 1000);
        lblMovimientos = new EtiquetaContador("Movimientos", 0);
        lblUndos       = new EtiquetaContador("Undos", 0);

        aplicarFuente(lblNivel, lblPuntaje, lblMovimientos, lblUndos);

        btnPausa     = crearBoton("/imagenes/hud/pausa.png", "/imagenes/hud/pausa_hover.png");
        btnUndo      = crearBoton("/imagenes/hud/undo.png", "/imagenes/hud/undo_hover.png");

        panelDirecciones = new PanelDirecciones();

        ensamblar();
        configurarListeners();
    }

    public void actualizar(DTOResultado resultado) {
        lblPuntaje.setValor(resultado.getPuntaje());
        lblMovimientos.setValor(resultado.getMovidas());
        lblUndos.setValor(resultado.getUndos());
    }

    public void setNivel(int v) {
        lblNivel.setValor(v);
    }

    public void resetearContadores() {
        lblPuntaje.setValor(1000);
        lblMovimientos.resetear();
        lblUndos.resetear();
    }

    public void setOnPausa(Runnable r)     { this.onPausa = r; }
    public void setOnUndo(Runnable r)      { this.onUndo = r; }

    public void setOnArriba(Runnable r)    { panelDirecciones.setOnArriba(r); }
    public void setOnAbajo(Runnable r)     { panelDirecciones.setOnAbajo(r); }
    public void setOnIzquierda(Runnable r) { panelDirecciones.setOnIzquierda(r); }
    public void setOnDerecha(Runnable r)   { panelDirecciones.setOnDerecha(r); }

    public PanelDirecciones getPanelDirecciones() { return panelDirecciones; }

    public void setInputHabilitado(boolean habilitado) {
        panelDirecciones.setEnabled(habilitado);
        btnUndo.setEnabled(habilitado);
    }

    private void aplicarFuente(EtiquetaContador... labels) {
        Font fuente = CargadorFuente.cargar(PATH_FUENTE, Font.PLAIN, 22f);
        for (EtiquetaContador lbl : labels) {
            lbl.setFont(fuente);
        }
    }

    private void ensamblar() {
        int gap = 20;

        add(Box.createVerticalStrut(gap));
        add(centrar(lblNivel));
        add(Box.createVerticalStrut(gap));
        add(separador());
        add(Box.createVerticalStrut(gap));

        add(centrar(lblPuntaje));
        add(Box.createVerticalStrut(gap / 2));
        add(centrar(lblMovimientos));
        add(Box.createVerticalStrut(gap / 2));
        add(centrar(lblUndos));
        add(Box.createVerticalStrut(gap));

        add(separador());
        add(Box.createVerticalStrut(gap));

        JPanel filaBotones = new JPanel();
        filaBotones.setOpaque(false);
        filaBotones.add(btnPausa);
        filaBotones.add(btnUndo);
        add(centrar(filaBotones));

        add(Box.createVerticalStrut(gap));
        add(separador());
        add(Box.createVerticalStrut(gap));

        add(centrar(panelDirecciones));
        add(Box.createVerticalGlue());
    }

    private void configurarListeners() {
        btnPausa.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { if (onPausa != null) onPausa.run(); }
        });
        btnUndo.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { if (onUndo != null) onUndo.run(); }
        });
    }

    private BotonImagen crearBoton(String pathNormal, String pathHover) {
        BotonImagen btn = new BotonImagen(pathNormal, pathHover);
        Dimension dim = new Dimension(60, 60);
        btn.setPreferredSize(dim);
        btn.setMinimumSize(dim);
        btn.setMaximumSize(dim);
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