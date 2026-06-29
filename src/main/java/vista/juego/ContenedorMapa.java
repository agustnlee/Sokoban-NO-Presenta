package vista.juego;

import modelo.dto.DTOResultado;
import modelo.entidades.DimensionTablero;

import javax.swing.JPanel;
import java.awt.Graphics;

public class ContenedorMapa extends JPanel {

    private DimensionTablero dimension;
    private int tamCelda;
    private final PanelMapa panelMapa;

    public ContenedorMapa(DimensionTablero dimension) {
        this.dimension = dimension;
        setOpaque(false);
        setLayout(null);

        this.panelMapa = new PanelMapa(this, dimension);
        add(panelMapa);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (panelMapa != null) {
            panelMapa.setBounds(0, 0, width, height);
            recalcularDimensiones(dimension);
        }
    }

    public void recalcularDimensiones(DimensionTablero dimension) {
        this.dimension = dimension;
        this.tamCelda = calcularTamCelda();
        panelMapa.repaint();
    }

    public void aplicarEstado(DTOResultado resultado) {
        panelMapa.aplicarEstado(resultado);
    }

    public PanelMapa getPanelMapa() { return panelMapa; }

    private int calcularTamCelda() {
        int filas = dimension.getFilas();
        int columnas = dimension.getColumnas();
        if (filas == 0 || columnas == 0) return 0;
        int tamPorAncho = getWidth() / columnas;
        int tamPorAlto = getHeight() / filas;
        return Math.min(tamPorAncho, tamPorAlto);
    }

    public int getTamCelda() { return tamCelda; }
    public int getFilas() { return dimension.getFilas(); }
    public int getColumnas() { return dimension.getColumnas(); }

    public int getOffsetX() {
        return (getWidth() - dimension.getColumnas() * tamCelda) / 2;
    }

    public int getOffsetY() {
        return (getHeight() - dimension.getFilas() * tamCelda) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}