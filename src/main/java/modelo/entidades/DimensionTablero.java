package modelo.entidades;

public enum DimensionTablero {
 
    ESTANDAR(20, 20); // TODO MODIFICAR EN BASE VISTA
 
    private final int filas;
    private final int columnas;
 
    DimensionTablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
    }
 
    public int getFilas() {
        return filas;
    }
 
    public int getColumnas() {
        return columnas;
    }
}