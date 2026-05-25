package vista.overlays;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class OverlayOscuro extends JPanel {
    // clase padre par amontar overlays

    private static final Color COLOR_FONDO = new Color(0, 0, 0, 180);

    public OverlayOscuro(int ancho, int alto) {
        this.setBounds(0, 0, ancho, alto);
        this.setOpaque(false);
        this.setLayout(null);
        bloquearInputsDebajo();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(COLOR_FONDO);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintChildren(g);
    }

    private void bloquearInputsDebajo() {
        this.addMouseListener(new MouseAdapter() {});
        this.addMouseMotionListener(new MouseMotionAdapter() {});
    }
}
