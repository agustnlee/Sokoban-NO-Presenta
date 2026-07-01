package vista.sonido;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class GestorSonido {

    private static GestorSonido instancia;
    private Clip musicaActual;
    private Mixer softwareMixer;

    private final Map<Sonido, byte[]>       cachePCMBytes  = new EnumMap<>(Sonido.class);
    private final Map<Sonido, AudioFormat>  cachePCMFormat = new EnumMap<>(Sonido.class);

    private GestorSonido() {
        softwareMixer = buscarSoftwareMixer();
    }

    public static GestorSonido getInstancia() {
        if (instancia == null) instancia = new GestorSonido();
        return instancia;
    }

    public void precargar() {
        for (Sonido s : Sonido.values()) {
            if (!s.isLoop()) precargarEfecto(s);
        }
    }

    public void reproducir(Sonido sonido) {
        if (sonido.isLoop()) reproducirMusica(sonido);
        else reproducirEfecto(sonido);
    }

    public void detenerMusica() {
        if (musicaActual != null) {
            if (musicaActual.isRunning()) musicaActual.stop();
            musicaActual.close();
            musicaActual = null;
        }
    }


    private void reproducirMusica(Sonido sonido) {
        if (musicaActual != null) {
            musicaActual.stop();
            musicaActual.close();
        }
        musicaActual = clipDesdeRecurso(sonido);
        if (musicaActual != null) {
            musicaActual.loop(Clip.LOOP_CONTINUOUSLY);
            musicaActual.start();
        }
    }

    private void reproducirEfecto(Sonido sonido) {
        byte[]      bytes  = cachePCMBytes.get(sonido);
        AudioFormat format = cachePCMFormat.get(sonido);

        if (bytes == null || format == null) {
            System.err.println("Efecto no precargado: " + sonido.getPath());
            return;
        }

        try {
            AudioInputStream stream = new AudioInputStream(
                new ByteArrayInputStream(bytes), format,
                bytes.length / format.getFrameSize()
            );
            Clip clip = abrirClip(stream);
            if (clip == null) return;
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) clip.close();
            });
            clip.start();
        } catch (Exception e) {
            System.err.println("Error reproduciendo: " + sonido.getPath() + " — " + e.getMessage());
        }
    }

    private void precargarEfecto(Sonido sonido) {
        try {
            URL url = getClass().getResource(sonido.getPath());
            if (url == null) {
                System.err.println("Recurso no encontrado: " + sonido.getPath());
                return;
            }

            AudioInputStream original = AudioSystem.getAudioInputStream(url);
            AudioInputStream pcm      = aPCM(original);
            AudioFormat      formato  = pcm.getFormat();

            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] chunk = new byte[4096];
            int n;
            while ((n = pcm.read(chunk)) != -1) buf.write(chunk, 0, n);
            pcm.close();

            cachePCMBytes.put(sonido,  buf.toByteArray());
            cachePCMFormat.put(sonido, formato);

        } catch (Exception e) {
            System.err.println("Error precargando: " + sonido.getPath() + " — " + e.getMessage());
        }
    }


    private Clip clipDesdeRecurso(Sonido sonido) {
        try {
            URL url = getClass().getResource(sonido.getPath());
            if (url == null) return null;
            AudioInputStream original = AudioSystem.getAudioInputStream(url);
            AudioInputStream pcm      = aPCM(original);
            return abrirClip(pcm);
        } catch (Exception e) {
            System.err.println("Error cargando música: " + sonido.getPath());
            return null;
        }
    }

    private Clip abrirClip(AudioInputStream stream) {
        try {
            Clip clip;
            if (softwareMixer != null) {
                DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
                clip = (Clip) softwareMixer.getLine(info);
            } else {
                clip = AudioSystem.getClip();
            }
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            // fallback sin mixer
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                return clip;
            } catch (Exception ex) {
                System.err.println("No se pudo abrir clip: " + ex.getMessage());
                return null;
            }
        }
    }

    private AudioInputStream aPCM(AudioInputStream original) {
        AudioFormat src = original.getFormat();
        AudioFormat pcm = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            src.getSampleRate(), 16,
            src.getChannels(),
            src.getChannels() * 2,
            src.getSampleRate(), false
        );
        if (AudioSystem.isConversionSupported(pcm, src)) {
            return AudioSystem.getAudioInputStream(pcm, original);
        }
        return original;
    }

    private Mixer buscarSoftwareMixer() {
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            if (info.getName().toLowerCase().contains("java sound audio engine")) {
                try { return AudioSystem.getMixer(info); }
                catch (Exception ignored) {}
            }
        }
        return null;
    }
}