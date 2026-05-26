package vista;

import vista.utils.UtilVisibilidad;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainVista {

    private static final int ANCHO = 1920;
    private static final int ALTO  = 1080;

    private final JFrame frame;

    // Páginas
    

    // Overlays
    

    public MainVista() {
        this.frame = new JFrame("Sokoban");
        this.frame.setSize(ANCHO, ALTO);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // TODO: Settear icono
        this.frame.setLocationRelativeTo(null);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalida();
            }
        });

        // Páginas instanciacion

        JLayeredPane layers = frame.getLayeredPane();

        // TODO: usar modal layer y palette layer para layered pane

        // TODO: falta seteo inicial de visibilidad con UtilVisibilidad.mostrar(Jcomp);

        this.frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(
            frame,
            "¿Seguro que querés salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Getters páginas

    // Getters overlays

    public JFrame getFrame() { return frame; }
}