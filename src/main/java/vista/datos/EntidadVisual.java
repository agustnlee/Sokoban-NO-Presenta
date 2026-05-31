package vista.datos;

public class EntidadVisual {

    // A diferencia del modelo, la vista no necesita ID, para el tema del rollback de una vez se renderiza el mapa con el estado post-undo
    
    private TipoCeldaVisual tipo;
    private int frameAnim;          // 0 o 1, para animacion
    private int empujesRestantes;   // para caja frágil, -1 si no aplica
    private float pixelX;           // posición actual en px (durante sliding animacion)
    private float pixelY;
    private boolean animando;

    public EntidadVisual(TipoCeldaVisual tipo) {
        this.tipo             = tipo;
        this.frameAnim        = 0;
        this.empujesRestantes = -1;
        this.animando         = false;
    }

    public EntidadVisual(TipoCeldaVisual tipo, int empujesRestantes) {
        this(tipo);
        this.empujesRestantes = empujesRestantes;
    }

    // Getters
    public TipoCeldaVisual getTipo()           { return tipo; }
    public int             getFrameAnim()      { return frameAnim; }
    public int             getEmpujesRestantes(){ return empujesRestantes; }
    public float           getPixelX()         { return pixelX; }
    public float           getPixelY()         { return pixelY; }
    public boolean         isAnimando()        { return animando; }

    // Setters
    public void setTipo(TipoCeldaVisual tipo)             { this.tipo = tipo; }
    public void setFrameAnim(int frameAnim)               { this.frameAnim = frameAnim; }
    public void setEmpujesRestantes(int empujesRestantes) { this.empujesRestantes = empujesRestantes; }
    public void setPixelX(float pixelX)                   { this.pixelX = pixelX; }
    public void setPixelY(float pixelY)                   { this.pixelY = pixelY; }
    public void setAnimando(boolean animando)              { this.animando = animando; }
}