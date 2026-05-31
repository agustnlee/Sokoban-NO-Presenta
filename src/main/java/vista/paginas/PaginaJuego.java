package vista.paginas;

import vista.juego.PanelHUD;
import vista.juego.PanelMapa;
import vista.utils.ImagenFondo;

import javax.swing.JPanel;
import java.awt.Dimension;

public class PaginaJuego extends JPanel {

    private static final int ANCHO      = 1920;
    private static final int ALTO       = 1080;

    // Mapa: 10 cols x 5 filas, celda de 100px → 1000x500
    // Centrado vertical y con margen izquierdo para el HUD
    private static final int FILAS      = 5;
    private static final int COLUMNAS   = 10;
    private static final int TAM_CELDA  = 100;

    private static final int MAPA_X     = 160;  // margen izquierdo
    private static final int MAPA_Y     = (ALTO - FILAS * TAM_CELDA) / 2; // centrado vertical

    private static final int HUD_X      = MAPA_X + COLUMNAS * TAM_CELDA + 40;
    private static final int HUD_Y      = 0;
    private static final int HUD_ANCHO  = ANCHO - HUD_X - 20;
    private static final int HUD_ALTO   = ALTO;

    // Sub-componentes

    private final PanelMapa  panelMapa;
    private final PanelHUD   panelHUD;

    public PaginaJuego() {
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setSize(ANCHO, ALTO);
        setOpaque(false);

        // 1. Fondo general (marco completo, decoraciones, zona mapa)
        ImagenFondo fondo = new ImagenFondo("/imagenes/fondos/juego.png");
        fondo.setBounds(0, 0, ANCHO, ALTO);
        add(fondo);

        // 2. Panel mapa — encima del fondo
        panelMapa = new PanelMapa(FILAS, COLUMNAS, TAM_CELDA);
        panelMapa.setBounds(MAPA_X, MAPA_Y, COLUMNAS * TAM_CELDA, FILAS * TAM_CELDA);
        add(panelMapa);

        // 3. HUD — lateral derecho
        panelHUD = new PanelHUD(HUD_ANCHO, HUD_ALTO);
        panelHUD.setBounds(HUD_X, HUD_Y, HUD_ANCHO, HUD_ALTO);
        add(panelHUD);
    }

    // Getters — el controlador accede a los sub-componentes para conectar todo

    public PanelMapa getPanelMapa() { return panelMapa; }
    public PanelHUD  getPanelHUD()  { return panelHUD;  }

    // Delegados convenientes al HUD


    public void setNivel      (int v) { panelHUD.setNivel(v);       }
    public void setMovimientos(int v) { panelHUD.setMovimientos(v); }
    public void setEmpujes    (int v) { panelHUD.setEmpujes(v);     }
    public void resetearHUD   ()      { panelHUD.resetearContadores(); }
}