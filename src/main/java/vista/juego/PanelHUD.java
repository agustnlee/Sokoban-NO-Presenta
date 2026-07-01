package vista.juego;

import modelo.dto.DTOResultado;
import vista.utils.CargadorFuente;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class PanelHUD extends JPanel {

    private static final String PATH_FUENTE = "/fuentes/special_elite.ttf";

    private final EtiquetaContador lblNivel;
    private final EtiquetaContador lblPuntaje;
    private final EtiquetaContador lblMovimientos;
    private final EtiquetaContador lblUndos;

    public PanelHUD(int ancho, int alto) {
        setOpaque(false);
        setPreferredSize(new Dimension(ancho, alto));
        setSize(ancho, alto);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lblNivel       = new EtiquetaContador("Nivel", 1);
        lblPuntaje     = new EtiquetaContador("Puntaje", 1000);
        lblMovimientos = new EtiquetaContador("Movimientos", 0);
        lblUndos       = new EtiquetaContador("Undos", 0);

        aplicarFuente(lblNivel, lblPuntaje, lblMovimientos, lblUndos);
        ensamblar();
    }

    public void actualizar(DTOResultado resultado) {
        lblPuntaje.setValor(resultado.getPuntaje());
        lblMovimientos.setValor(resultado.getMovidas());
        lblUndos.setValor(resultado.getUndos());
    }

    public void setNivel(int v) { lblNivel.setValor(v); }

    public void resetearContadores() {
        lblPuntaje.setValor(130);
        lblMovimientos.resetear();
        lblUndos.resetear();
    }

    private void aplicarFuente(EtiquetaContador... labels) {
        Font fuente = CargadorFuente.cargar(PATH_FUENTE, Font.PLAIN, 22f);
        for (EtiquetaContador lbl : labels) { lbl.setFont(fuente); lbl.setForeground(Color.BLACK); }
    }

    private void ensamblar() {
        int gap = 20;
        add(Box.createVerticalStrut(gap));
        add(centrar(lblNivel));
        add(Box.createVerticalStrut(10));
        add(separador());
        add(Box.createVerticalStrut(gap));
        add(centrar(lblPuntaje));
        add(Box.createVerticalStrut(gap / 2));
        add(centrar(lblMovimientos));
        add(Box.createVerticalStrut(gap / 2));
        add(centrar(lblUndos));
        add(Box.createVerticalGlue());
    }

    private JPanel centrar(java.awt.Component comp) {
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }

    private JSeparator separador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.BLACK);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        return sep;
    }
}