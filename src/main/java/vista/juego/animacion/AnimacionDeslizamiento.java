package vista.juego.animacion;

import vista.datos.EntidadVisual;

public class AnimacionDeslizamiento {

    private static final float DURACION_MS = 200f;

    private final EntidadVisual entidad;
    private final float origenPx;
    private final float origenPy;
    private final float destinoPx;
    private final float destinoPy;
    private final Runnable onTerminado;

    private float progresoMs = 0f;
    private boolean terminada = false;

    public AnimacionDeslizamiento(EntidadVisual entidad, float origenPx,  float origenPy, float destinoPx, float destinoPy, Runnable onTerminado) {
        this.entidad     = entidad;
        this.origenPx    = origenPx;
        this.origenPy    = origenPy;
        this.destinoPx   = destinoPx;
        this.destinoPy   = destinoPy;
        this.onTerminado = onTerminado;

        entidad.setPixelX(origenPx);
        entidad.setPixelY(origenPy);
        entidad.setAnimando(true);
    }

    /**
     Avanza la animación en deltaMs milisegundos.
     pondera pixelX/pixelY de la entidad.
     Al terminar llama onTerminado y marca terminada=true.
     */

    public void tick(float deltaMs) {
        if (terminada) return;

        progresoMs = Math.min(progresoMs + deltaMs, DURACION_MS);
        float t = progresoMs / DURACION_MS; 
        t = easeInOut(t);

        entidad.setPixelX(origenPx + (destinoPx - origenPx) * t);
        entidad.setPixelY(origenPy + (destinoPy - origenPy) * t);

        if (progresoMs >= DURACION_MS) {
            entidad.setPixelX(destinoPx);
            entidad.setPixelY(destinoPy);
            entidad.setAnimando(false);
            terminada = true;
            if (onTerminado != null) onTerminado.run();
        }
    }

    // Suaviza el movimiento: arranca lento, acelera, frena (similar a css)
    private float easeInOut(float t) {
        return t * t * (3 - 2 * t);
    }

    public boolean isTerminada() { return terminada; }
    public EntidadVisual getEntidad() { return entidad; }
}