package vista.sonido;

import javax.sound.sampled.*;
import java.net.URL;

public class GestorSonido {

    private static GestorSonido instancia;
    private Clip musicaActual; // Un solo canal para música

    private GestorSonido() {}

    public static GestorSonido getInstancia() {
        if (instancia == null) {
            instancia = new GestorSonido();
        }
        return instancia;
    }

    /**
     * Método central: detecta si es loop (música) o efecto y delega.
     */
    public void reproducir(Sonido sonido) {
        if (sonido.isLoop()) {
            reproducirMusica(sonido);
        } else {
            reproducirEfecto(sonido);
        }
    }

    public void detenerMusica() {
        if (musicaActual != null) {
            if (musicaActual.isRunning()) {
                musicaActual.stop();
            }
            musicaActual.close();
            musicaActual = null; // Seteamos en null para limpiar la referencia
        }
    }

    private void reproducirMusica(Sonido sonido) {
        if (musicaActual != null) {
            musicaActual.stop();
            musicaActual.close();
        }

        musicaActual = cargarClip(sonido);

        if (musicaActual != null) {
            musicaActual.loop(Clip.LOOP_CONTINUOUSLY);
            musicaActual.start();
        }
    }


    private void reproducirEfecto(Sonido sonido) {
        Clip efecto = cargarClip(sonido);

        if (efecto != null) {
            // Listener que cierra el clip automáticamente cuando termina de sonar
            // Esto permite que el recolector de basura limpie la memoria
            efecto.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    efecto.close();
                }
            });
            efecto.start();
        }
    }

    private Clip cargarClip(Sonido sonido) {
        try {
            URL url = getClass().getResource(sonido.getPath());
            if (url == null) return null;

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (Exception e) {
            System.err.println("Error cargando sonido: " + sonido.getPath());
            return null;
        }
    }
}