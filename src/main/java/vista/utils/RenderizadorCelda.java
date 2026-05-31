package vista.utils;

import vista.datos.TipoCeldaVisual;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public final class RenderizadorCelda {

    // Mapa tipo → path de imagen
    private static final Map<TipoCeldaVisual, String> PATHS =
            new EnumMap<>(TipoCeldaVisual.class);

    static {
        PATHS.put(TipoCeldaVisual.VACIO,                "/imagenes/celdas/vacio.png");
        PATHS.put(TipoCeldaVisual.DESTINO,              "/imagenes/celdas/destino.png");
        PATHS.put(TipoCeldaVisual.RESBALADIZO,          "/imagenes/celdas/resbaladizo.png");
        PATHS.put(TipoCeldaVisual.CANDADO,              "/imagenes/celdas/candado.png");
        PATHS.put(TipoCeldaVisual.MURO_ABIERTO,         "/imagenes/celdas/muro_abierto.png");
        PATHS.put(TipoCeldaVisual.PARED,                "/imagenes/celdas/pared.png");
        PATHS.put(TipoCeldaVisual.MURO_CERRADO,         "/imagenes/celdas/muro_cerrado.png");
        PATHS.put(TipoCeldaVisual.CAJA_NORMAL,          "/imagenes/entidades/caja_normal.png");
        PATHS.put(TipoCeldaVisual.CAJA_NORMAL_EN_DESTINO,  "/imagenes/entidades/caja_normal_destino.png");
        PATHS.put(TipoCeldaVisual.CAJA_FRAGIL,          "/imagenes/entidades/caja_fragil.png");
        PATHS.put(TipoCeldaVisual.CAJA_FRAGIL_EN_DESTINO,  "/imagenes/entidades/caja_fragil_destino.png");
        PATHS.put(TipoCeldaVisual.CAJA_LLAVE,           "/imagenes/entidades/caja_llave.png");
        PATHS.put(TipoCeldaVisual.CAJA_LLAVE_EN_CANDADO,"/imagenes/entidades/caja_llave_candado.png");
        PATHS.put(TipoCeldaVisual.ESCALERA,             "/imagenes/entidades/escalera.png");
    }

    // Cache de imágenes ya cargadas y escaladas al tamCelda actual
    private static final Map<TipoCeldaVisual, BufferedImage> cache =
            new EnumMap<>(TipoCeldaVisual.class);

    private static int tamCeldaCache = -1;

    private RenderizadorCelda() {}

    /**
     * Dibuja la imagen correspondiente al tipo en la posición px indicada.
     * px = col * tamCelda, py = fila * tamCelda (calculado externamente o aquí).
     */
    public static void dibujarEnPx(Graphics2D g, TipoCeldaVisual tipo,
                                   float px, float py, int tamCelda) {
        BufferedImage img = obtenerImagen(tipo, tamCelda);
        if (img != null) {
            g.drawImage(img, (int) px, (int) py, tamCelda, tamCelda, null);
        }
    }

    /**
     * Sobrecarga por fila/col: convierte a px internamente.
     */
    public static void dibujar(Graphics2D g, TipoCeldaVisual tipo,
                               int fila, int col, int tamCelda) {
        dibujarEnPx(g, tipo, col * tamCelda, fila * tamCelda, tamCelda);
    }

    /**
     * Dibuja el contador de empujes restantes centrado sobre la celda.
     * Se usa para cajas frágiles en pasada 2.
     */
    public static void dibujarContadorFragil(Graphics2D g, int empujesRestantes,
                                             float px, float py, int tamCelda) {
        String texto = String.valueOf(empujesRestantes);
        Font font = new Font("Arial", Font.BOLD, tamCelda / 3);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textX = (int) px + (tamCelda - fm.stringWidth(texto)) / 2;
        int textY = (int) py + (tamCelda - fm.getHeight()) / 2 + fm.getAscent();

        // Sombra para legibilidad
        g.setColor(Color.BLACK);
        g.drawString(texto, textX + 1, textY + 1);

        g.setColor(Color.WHITE);
        g.drawString(texto, textX, textY);
    }

    /**
     * Invalida el cache si cambió el tamaño de celda.
     */
    public static void invalidarCache(int nuevoTamCelda) {
        if (nuevoTamCelda != tamCeldaCache) {
            cache.clear();
            tamCeldaCache = nuevoTamCelda;
        }
    }

    // -------------------------------------------------------------------------

    private static BufferedImage obtenerImagen(TipoCeldaVisual tipo, int tamCelda) {
        invalidarCache(tamCelda);

        if (cache.containsKey(tipo)) return cache.get(tipo);

        String path = PATHS.get(tipo);
        if (path == null) return null;

        BufferedImage original = CargadorImagen.getInstancia().cargar(path);
        if (original == null) return null;

        BufferedImage escalada = EscaladorImagen.escalar(original, tamCelda, tamCelda);
        cache.put(tipo, escalada);
        return escalada;
    }
}