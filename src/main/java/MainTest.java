
import vista.MainVista;

import javax.swing.SwingUtilities;

public class MainTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainVista mainVista = new MainVista(10, null);
            mainVista.mostrarJuego();

            var contenedorMapa = mainVista.getPaginaJuego().getContenedorMapa();
            contenedorMapa.aplicarEstado(GeneradorDTOTest.generarTableroDePrueba());
        });
    }
}