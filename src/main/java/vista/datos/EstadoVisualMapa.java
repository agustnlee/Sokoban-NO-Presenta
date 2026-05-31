package vista.datos;

public class EstadoVisualMapa {


    // TODO: en vista parecio sensato usar doble matriz para el tema del mapa, matchear con backend

    private final TipoCeldaVisual[][] suelo;      // capa fija: vacio, etc.
    private final EntidadVisual[][]   entidades;  // capa dinámica: cajas, jugador, escalera, entre otros
    private final int filas;
    private final int columnas;

    public EstadoVisualMapa(TipoCeldaVisual[][] suelo, EntidadVisual[][] entidades) {
        this.suelo     = suelo;
        this.entidades = entidades;
        this.filas     = suelo.length;
        this.columnas  = suelo[0].length;
    }

    public TipoCeldaVisual getSuelo(int fila, int col)     { return suelo[fila][col]; }
    public EntidadVisual   getEntidad(int fila, int col)   { return entidades[fila][col]; }
    public int             getFilas()                      { return filas; }
    public int             getColumnas()                   { return columnas; }


    // Devuelve null si no hay entidad en esa celda
    public boolean hayEntidad(int fila, int col) {
        return entidades[fila][col] != null;
    }
}