package vista.juego;

import vista.sonido.GestorSonido;
import vista.sonido.Sonido;
import vista.utils.CargadorImagen;
import vista.utils.EscaladorImagen;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BotonNivel extends JPanel {

    private static final int ANCHO         = 225;
    private static final int ALTO          = 225;
    private static final Color COLOR_HOVER = new Color(255, 255, 255, 60);
    private static final Color COLOR_BORDE = new Color(255, 255, 255, 180);
    private static final Color COLOR_TEXTO = Color.WHITE;

    private final int           numeroNivel;
    private final BufferedImage screenshot;
    private boolean             mouseOver = false;
    private Runnable            onClick;


    public BotonNivel(int numeroNivel, String pathScreenshot) {
        this.numeroNivel = numeroNivel;
        this.screenshot  = cargarScreenshot(pathScreenshot);

        setPreferredSize(new Dimension(ANCHO, ALTO));
        setMinimumSize(new Dimension(ANCHO, ALTO));
        setMaximumSize(new Dimension(ANCHO, ALTO));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
                GestorSonido.getInstancia().reproducir(Sonido.HOVER_BOTON_NIVEL);
            }
            @Override public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }
            @Override public void mouseClicked(MouseEvent e) {
                GestorSonido.getInstancia().reproducir(Sonido.CLICK_BOTON_NIVEL);
                if (onClick != null) onClick.run();
            }
        });
    }

    public void setOnClick(Runnable r) { this.onClick = r; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        // Screenshot de fondo
        if (screenshot != null) {
            g2d.drawImage(screenshot, 0, 0, ANCHO, ALTO, null);
        } else {
            g2d.setColor(new Color(50, 50, 50));
            g2d.fillRect(0, 0, ANCHO, ALTO);
        }

        if (mouseOver) {
            g2d.setColor(COLOR_HOVER);
            g2d.fillRect(0, 0, ANCHO, ALTO);
        }

        // Borde
        g2d.setColor(COLOR_BORDE);
        g2d.drawRect(0, 0, ANCHO - 1, ALTO - 1);

        String texto = "Nivel " + numeroNivel;
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, ALTO - 36, ANCHO, 36);
        FontMetrics fm = g2d.getFontMetrics();
        int tx = (ANCHO - fm.stringWidth(texto)) / 2;
        int ty = ALTO - 36 + fm.getAscent() + (36 - fm.getHeight()) / 2;
        g2d.setColor(COLOR_TEXTO);
        g2d.drawString(texto, tx, ty);

        g2d.dispose();
    }

    private BufferedImage cargarScreenshot(String path) {
        if (path == null || path.isBlank()) return null;
        BufferedImage img = CargadorImagen.getInstancia().cargar(path);
        if (img == null) return null;
        return EscaladorImagen.escalar(img, ANCHO, ALTO);
    }
}