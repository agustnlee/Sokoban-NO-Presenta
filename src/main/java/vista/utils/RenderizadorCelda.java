package vista.utils;

import modelo.dto.DTOCelda;
import modelo.entidades.TipoEntidad;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.awt.BasicStroke;
import java.awt.Stroke;

public final class RenderizadorCelda {

    private static final Map<TipoEntidad, String> PATHS_SUELO = new EnumMap<>(TipoEntidad.class); //TODO CHEQUEAR PATH
    static {
        PATHS_SUELO.put(TipoEntidad.VACIA,        "/imagenes/celdas/vacio.png"); 
        PATHS_SUELO.put(TipoEntidad.RESBALADIZA,  "/imagenes/celdas/resbaladizo.png");
        PATHS_SUELO.put(TipoEntidad.PARED,        "/imagenes/celdas/pared.png");
        PATHS_SUELO.put(TipoEntidad.MURO_ABIERTO, "/imagenes/celdas/muro_abierto.png");
        PATHS_SUELO.put(TipoEntidad.MURO_CERRADO, "/imagenes/celdas/muro_cerrado.png");
        PATHS_SUELO.put(TipoEntidad.CANDADO,      "/imagenes/celdas/candado.png");
    }

    private static final String DESTINO_ABIERTA = "/imagenes/celdas/destino_abierta.png";
    private static final String DESTINO_CERRADA = "/imagenes/celdas/destino_cerrada.png";

    private static final String CANDADO_CON_LLAVE = "/imagenes/celdas/candado_activado.png";

    private static final String CAJA_NORMAL = "/imagenes/entidades/bolsa_dinero.png";
    private static final String CAJA_FRAGIL = "/imagenes/entidades/cajafragil.png";
    private static final String CAJA_LLAVE  = "/imagenes/entidades/caja_herramientas.png";
    private static final String ESCALERA    = "/imagenes/entidades/escalera.png";
    private static final String JUGADOR     = "/imagenes/entidades/jugador.png";

    private static final Map<String, BufferedImage> cache = new HashMap<>();
    private static int tamCeldaCache = -1;

    private RenderizadorCelda() {}

    public static void dibujarCelda(Graphics2D g, DTOCelda celda, float px, float py, int tamCelda) {
        String pathSuelo = resolverPathSuelo(celda);
        dibujarPath(g, pathSuelo, px, py, tamCelda);

        String pathEntidad = resolverPathEntidad(celda);
        if (pathEntidad != null) {
            dibujarPath(g, pathEntidad, px, py, tamCelda);
        }

        if (celda.getTipoEntidad() == TipoEntidad.CAJA_FRAGIL && !celda.isCajaBloqueada()) {
            dibujarNumeroEmpujes(g, celda.getEmpujesRestantes(), px, py, tamCelda);
        }

        dibujarBorde(g, px, py, tamCelda);
    }

    private static void dibujarBorde(Graphics2D g, float px, float py, int tamCelda) {
        Stroke anterior = g.getStroke();

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(0.5f)); 
        g.drawRect((int) px, (int) py, tamCelda - 1, tamCelda - 1);

        g.setStroke(anterior);
    }


    private static String resolverPathSuelo(DTOCelda celda) {
        TipoEntidad tipoCasilla = celda.getTipoCasilla();

        if (tipoCasilla == TipoEntidad.DESTINO) {
            return celda.isCajaBloqueada() ? DESTINO_CERRADA : DESTINO_ABIERTA;
        }
        if (tipoCasilla == TipoEntidad.CANDADO) {
            boolean llaveActivada = celda.getTipoEntidad() == TipoEntidad.CAJA_LLAVE
                    && celda.isCajaBloqueada();
            return llaveActivada ? CANDADO_CON_LLAVE : PATHS_SUELO.get(TipoEntidad.CANDADO);
        }

        return PATHS_SUELO.get(tipoCasilla);
    }

    private static String resolverPathEntidad(DTOCelda celda) {
        TipoEntidad tipoEntidad = celda.getTipoEntidad();
        if (tipoEntidad == null) {
            return null;
        }

        switch (tipoEntidad) {
            case JUGADOR:
                return JUGADOR;
            case ESCALERA:
                return ESCALERA;
            case CAJA:
                return celda.isCajaBloqueada() ? null : CAJA_NORMAL;
            case CAJA_FRAGIL:
                return celda.isCajaBloqueada() ? null : CAJA_FRAGIL;
            case CAJA_LLAVE:
                return celda.isCajaBloqueada() ? null : CAJA_LLAVE;
            default:
                return null;
        }
    }

    private static void dibujarNumeroEmpujes(Graphics2D g, int numero, float px, float py, int tamCelda) {
        String texto = String.valueOf(numero);
        Font font = new Font("Arial", Font.BOLD, tamCelda / 3);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        int textX = (int) px + (tamCelda - fm.stringWidth(texto)) / 2;
        int textY = (int) py + (tamCelda - fm.getHeight()) / 2 + fm.getAscent();

        g.setColor(Color.BLACK);
        g.drawString(texto, textX + 1, textY + 1);

        g.setColor(Color.WHITE);
        g.drawString(texto, textX, textY);
    }

    private static void dibujarPath(Graphics2D g, String path, float px, float py, int tamCelda) {
        if (path == null) return;
        BufferedImage img = obtenerImagen(path, tamCelda);
        if (img != null) {
            g.drawImage(img, (int) px, (int) py, tamCelda, tamCelda, null);
        }
    }

    private static BufferedImage obtenerImagen(String path, int tamCelda) {
        invalidarCacheSiCambioTamano(tamCelda);
        String clave = path + "@" + tamCelda;
        if (cache.containsKey(clave)) return cache.get(clave);

        BufferedImage original = CargadorImagen.getInstancia().cargar(path);
        if (original == null) return null;

        BufferedImage escalada = EscaladorImagen.escalar(original, tamCelda, tamCelda);
        cache.put(clave, escalada);
        return escalada;
    }

    private static void invalidarCacheSiCambioTamano(int nuevoTamCelda) {
        if (nuevoTamCelda != tamCeldaCache) {
            cache.clear();
            tamCeldaCache = nuevoTamCelda;
        }
    }
}