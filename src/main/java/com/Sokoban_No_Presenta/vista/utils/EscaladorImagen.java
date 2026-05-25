package vista.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class EscaladorImagen {

    private EscaladorImagen() {}

    public static BufferedImage escalar(BufferedImage src, int ancho, int alto) {
        BufferedImage resultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resultado.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                           RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(src, 0, 0, ancho, alto, null);
        g.dispose();
        return resultado;
    }
}