package vista.utils;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImagenFondo extends JPanel {

    private final BufferedImage imagen;

    public ImagenFondo(String path) {
        this.imagen = CargadorImagen.getInstancia().cargar(path);
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}