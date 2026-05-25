package vista.utils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class CargadorImagen {

    private static final String PATH_ERROR = "/imagenes/estados/error.png";
    private static CargadorImagen instancia;

    private CargadorImagen() {}

    public static CargadorImagen getInstancia() {
        if (instancia == null) {
            instancia = new CargadorImagen();
        }
        return instancia;
    }

    public BufferedImage cargar(String path) {
        BufferedImage imagen = leerDesdeDisco(path);
        if (imagen == null) {
            imagen = leerDesdeDisco(PATH_ERROR);
        }
        return imagen;
    }

    private BufferedImage leerDesdeDisco(String path) {
        try {
            URL url = CargadorImagen.class.getResource(path);
            if (url == null) return null;
            return convertirArgb(ImageIO.read(url));
        } catch (IOException e) {
            return null;
        }
    }

    private BufferedImage convertirArgb(BufferedImage src) {
        BufferedImage argb = new BufferedImage(
            src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = argb.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return argb;
    }
}