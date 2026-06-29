package vista.juego;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 Contenedor que delimita el área del mapa y calcula el tamaño de celda
  en base al espacio disponible
 */
public class ContenedorMapa extends JPanel {

    private int filas;
    private int columnas;
    private int tamCelda;

    private static final Color COLOR_FONDO = new Color(20, 20, 20, 160);

    public ContenedorMapa(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        setOpaque(false);
        setLayout(null);
    }

    public void recalcularDimensiones(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tamCelda = calcularTamCelda();
    }

    private int calcularTamCelda() {
        if (filas == 0 || columnas == 0) return 0;
        int tamPorAncho = getWidth() / columnas;
        int tamPorAlto = getHeight() / filas;
        return Math.min(tamPorAncho, tamPorAlto);
    }

    public int getTamCelda() {
        return tamCelda;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getOffsetX() {
        return (getWidth() - columnas * tamCelda) / 2;
    }

    public int getOffsetY() {
        return (getHeight() - filas * tamCelda) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}