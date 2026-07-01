package vista.utils;

import javax.swing.JComponent;

public final class UtilVisibilidad {

    private UtilVisibilidad() {}

    public static void mostrar(JComponent componente) {
        componente.setVisible(true);
        componente.setEnabled(true);
    }

    public static void ocultar(JComponent componente) {
        componente.setVisible(false);
        componente.setEnabled(false);
    }
}