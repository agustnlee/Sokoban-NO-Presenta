package vista.utils;

import vista.sonido.GestorSonido;
import vista.sonido.Sonido;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BotonImagen extends JPanel {

    private BufferedImage imagenNormal;
    private BufferedImage imagenHover;
    private boolean mouseOver = false;


    // default
    public BotonImagen(String pathNormal, String pathHover) {
        this(pathNormal, pathHover, Sonido.CLICK_BOTON, Sonido.HOVER_BOTON);
    }

    public BotonImagen(String pathNormal, String pathHover, Sonido sonidoClick, Sonido sonidoHover) {
        setOpaque(false);
        setLayout(null);

        CargadorImagen cargador = CargadorImagen.getInstancia();
        this.imagenNormal = cargador.cargar(pathNormal);
        this.imagenHover  = cargador.cargar(pathHover);

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
                if (isEnabled() && sonidoHover != null)
                    GestorSonido.getInstancia().reproducir(sonidoHover);
            }
            @Override public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }
            @Override public void mouseClicked(MouseEvent e) {
                if (isEnabled() && sonidoClick != null)
                    GestorSonido.getInstancia().reproducir(sonidoClick);
            }
        });
    }

    public void setImagenes(String pathNormal, String pathHover) {
        CargadorImagen cargador = CargadorImagen.getInstancia();
        this.imagenNormal = cargador.cargar(pathNormal);
        this.imagenHover  = cargador.cargar(pathHover);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = mouseOver ? imagenHover : imagenNormal;
        if (img != null) g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}