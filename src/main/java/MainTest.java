import vista.MainVista;
import javax.swing.SwingUtilities;

public class MainTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainVista mainVista = new MainVista(10, null);

            // =========================
            // TRANSICIONES ENTRE PÁGINAS
            // =========================
            mainVista.mostrarInicio();
            // mainVista.mostrarNiveles();
            // mainVista.mostrarJuego();

            // =========================
            // TABLERO DE PRUEBA
            // =========================
            mainVista.mostrarJuego();
            var contenedorMapa = mainVista.getPaginaJuego().getContenedorMapa();
            contenedorMapa.aplicarEstado(
                    GeneradorDTOTest.generarTableroDePrueba()
            );

            // =========================
            // OVERLAYS
            // =========================
            mainVista.mostrarPausa();
            // mainVista.mostrarVictoria(12345);
            // mainVista.mostrarDerrota("Te quedaste sin movimientos");

            // =========================
            // CUENTA ATRÁS
            // =========================
            // mainVista.getOverlayCuentaAtras().iniciar(() ->
            //     System.out.println("Cuenta atrás terminada")
            // );

            // =========================
            // TRANSICIÓN MANO
            // =========================
            // var panelTransicion = mainVista.getPaginaJuego().getPanelTransicion();
            // panelTransicion.iniciar(
            //     () -> System.out.println("Punto medio"),
            //     () -> System.out.println("Transición terminada")
            // );

            // =========================
            // OCULTAR TODOS
            // =========================
            // mainVista.ocultarTodosLosOverlays();
        });
    }
}