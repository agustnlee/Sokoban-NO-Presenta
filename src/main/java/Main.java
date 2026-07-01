import controlador.ControladorPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControladorPrincipal::new);
    }
}


