package vista.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class CargadorFuente {

    private static final Map<String, Font> cache = new HashMap<>();

    private CargadorFuente() {}

    public static Font cargar(String path, int estilo, float tamano) {
        Font base = cache.get(path);

        if (base == null) {
            try (InputStream is = CargadorFuente.class.getResourceAsStream(path)) {
                if (is == null) {
                    return new Font("SansSerif", estilo, (int) tamano);
                }
                base = Font.createFont(Font.TRUETYPE_FONT, is);
                cache.put(path, base);
            } catch (IOException | FontFormatException e) {
                return new Font("SansSerif", estilo, (int) tamano);
            }
        }

        return base.deriveFont(estilo, tamano);
    }
}