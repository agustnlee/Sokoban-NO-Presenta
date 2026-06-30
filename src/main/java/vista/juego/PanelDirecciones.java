package vista.juego;

import vista.utils.BotonImagen;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelDirecciones extends JPanel {

    private static final int TAM_BOTON = 80;

    private final BotonImagen btnArriba;
    private final BotonImagen btnAbajo;
    private final BotonImagen btnIzquierda;
    private final BotonImagen btnDerecha;

    private Runnable onArriba;
    private Runnable onAbajo;
    private Runnable onIzquierda;
    private Runnable onDerecha;

    public PanelDirecciones() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setFocusable(true);

        btnArriba    = new BotonImagen("/imagenes/hud/flecha_arriba.png", "/imagenes/hud/flecha_arriba_hover.png"); //TODO Chequear PATH
        btnAbajo     = new BotonImagen("/imagenes/hud/flecha_abajo.png", "/imagenes/hud/flecha_abajo_hover.png");
        btnIzquierda = new BotonImagen("/imagenes/hud/flecha_izquierda.png", "/imagenes/hud/flecha_izquierda_hover.png");
        btnDerecha   = new BotonImagen("/imagenes/hud/flecha_derecha.png", "/imagenes/hud/flecha_derecha_hover.png");

        configurarTamanos();
        configurarLayout();
        configurarListenersMouse();
        configurarListenerTeclado();
    }

    public void setOnArriba(Runnable r)    { this.onArriba = r; }
    public void setOnAbajo(Runnable r)     { this.onAbajo = r; }
    public void setOnIzquierda(Runnable r) { this.onIzquierda = r; }
    public void setOnDerecha(Runnable r)   { this.onDerecha = r; }

    public void solicitarFoco() {
        requestFocusInWindow();
    }

    private void configurarTamanos() {
        Dimension dim = new Dimension(TAM_BOTON, TAM_BOTON);
        for (BotonImagen btn : new BotonImagen[]{btnArriba, btnAbajo, btnIzquierda, btnDerecha}) {
            btn.setPreferredSize(dim);
            btn.setMinimumSize(dim);
            btn.setMaximumSize(dim);
        }
    }

    private void configurarLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridx = 1; gbc.gridy = 0;
        add(btnArriba, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(btnIzquierda, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        add(btnDerecha, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(btnAbajo, gbc);
    }

    private void configurarListenersMouse() {
        btnArriba.addMouseListener(clickHandler(() -> onArriba));
        btnAbajo.addMouseListener(clickHandler(() -> onAbajo));
        btnIzquierda.addMouseListener(clickHandler(() -> onIzquierda));
        btnDerecha.addMouseListener(clickHandler(() -> onDerecha));
    }

    private MouseAdapter clickHandler(java.util.function.Supplier<Runnable> callback) {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                Runnable r = callback.get();
                if (r != null) r.run();
            }
        };
    }

    private void configurarListenerTeclado() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    if (onArriba != null) onArriba.run(); break;
                    case KeyEvent.VK_DOWN:  if (onAbajo != null) onAbajo.run(); break;
                    case KeyEvent.VK_LEFT:  if (onIzquierda != null) onIzquierda.run(); break;
                    case KeyEvent.VK_RIGHT: if (onDerecha != null) onDerecha.run(); break;
                    default: break;
                }
            }
        });
    }
}