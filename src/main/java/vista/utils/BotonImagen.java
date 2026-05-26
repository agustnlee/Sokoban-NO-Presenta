package vista.utils;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BotonImagen extends JPanel {

    private BufferedImage imagenNormal;
    private BufferedImage imagenHover;
    private boolean mouseOver = false;

    public BotonImagen(String pathNormal, String pathHover) {
        setOpaque(false);
        setLayout(null);
        CargadorImagen cargador = CargadorImagen.getInstancia();
        this.imagenNormal = cargador.cargar(pathNormal);
        this.imagenHover  = cargador.cargar(pathHover);

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { mouseOver = true;  repaint(); }
            @Override public void mouseExited (MouseEvent e) { mouseOver = false; repaint(); }
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