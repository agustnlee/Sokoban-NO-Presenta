package vista.juego;

import modelo.dto.DTOCelda;
import modelo.dto.DTOResultado;
import modelo.entidades.Coordenada;
import vista.utils.RenderizadorCelda;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Utiliza DTOResultado del MOdelo, refresh completo
public class PanelMapa extends JPanel {

    private final ContenedorMapa contenedor;
    private Map<Coordenada, DTOCelda> celdas = new HashMap<>();
    private int filas;
    private int columnas;

    public PanelMapa(ContenedorMapa contenedor) {
        this.contenedor = contenedor;
        setOpaque(false);
    }

    public void aplicarEstado(DTOResultado resultado, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = indexarCeldas(resultado.getCeldas());

        contenedor.recalcularDimensiones(filas, columnas);
        repaint();
    }

    private Map<Coordenada, DTOCelda> indexarCeldas(List<DTOCelda> lista) {
        Map<Coordenada, DTOCelda> mapa = new HashMap<>();
        for (DTOCelda c : lista) {
            mapa.put(c.getCoordenada(), c);
        }
        return mapa;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (celdas.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tamCelda = contenedor.getTamCelda();
        int offsetX = contenedor.getOffsetX();
        int offsetY = contenedor.getOffsetY();

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                DTOCelda celda = celdas.get(new Coordenada(fila, columna));
                if (celda == null) continue;

                float px = offsetX + columna * tamCelda;
                float py = offsetY + fila * tamCelda;
                RenderizadorCelda.dibujarCelda(g2d, celda, px, py, tamCelda);
            }
        }

        g2d.dispose();
    }
}