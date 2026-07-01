package vista.juego;

import modelo.dto.DTOCelda;
import modelo.dto.DTOResultado;
import modelo.entidades.DimensionTablero;
import vista.utils.RenderizadorCelda;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public class PanelMapa extends JPanel {

    private final ContenedorMapa contenedor;
    private DimensionTablero dimension;
    private DTOCelda[][] celdas;

    public PanelMapa(ContenedorMapa contenedor, DimensionTablero dimension) {
        this.contenedor = contenedor;
        this.dimension = dimension;
        this.celdas = new DTOCelda[dimension.getFilas()][dimension.getColumnas()];
        setOpaque(false);
    }

    public void aplicarEstado(DTOResultado resultado) {
        volcarCeldas(resultado.getCeldas());
        repaint();
    }

    public void recalcularDimension(DimensionTablero dimension) {
        this.dimension = dimension;
        this.celdas = new DTOCelda[dimension.getFilas()][dimension.getColumnas()];
        contenedor.recalcularDimensiones(dimension);
    }

    private void volcarCeldas(List<DTOCelda> lista) {
        for (DTOCelda c : lista) {
            int fila = c.getCoordenada().getFila();
            int columna = c.getCoordenada().getColumna();
            celdas[fila][columna] = c;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (celdas == null) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tamCelda = contenedor.getTamCelda();
        int offsetX = contenedor.getOffsetX();
        int offsetY = contenedor.getOffsetY();

        for (int fila = 0; fila < dimension.getFilas(); fila++) {
            for (int columna = 0; columna < dimension.getColumnas(); columna++) {
                DTOCelda celda = celdas[fila][columna];
                if (celda == null) continue;

                float px = offsetX + columna * tamCelda;
                float py = offsetY + fila * tamCelda;
                RenderizadorCelda.dibujarCelda(g2d, celda, px, py, tamCelda);
            }
        }

        g2d.dispose();
    }
}