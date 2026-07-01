package vista.juego;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class EtiquetaContador extends JLabel {

    private final String textoBase;
    private int valor;

    /**
     * @param textoBase  etiqueta fija  (ej: "Movimientos")
     * @param valorInicial valor inicial del contador
     */
    public EtiquetaContador(String textoBase, int valorInicial) {
        this.textoBase = textoBase;
        this.valor     = valorInicial;

        setFont(new Font("Arial", Font.BOLD, 24));
        setForeground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        actualizar();
    }

    public void setValor(int valor) {
        this.valor = valor;
        actualizar();
    }

    public int getValor() { return valor; }

    public void incrementar() { setValor(valor + 1); }

    public void resetear() { setValor(0); }

    private void actualizar() {
        setText(textoBase + ": " + valor);
    }
}