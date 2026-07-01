package modelo.dto;

import java.util.List;

public class DTOResultado {

    private final List<DTOCelda> celdas;
    private final boolean victoria;
    private final boolean derrota;
    private final int puntaje;
    private final int undos;
    private final int movidas;

    public DTOResultado(List<DTOCelda> celdas, boolean victoria, boolean derrota, int puntaje, int undos, int movidas) {
        this.celdas = celdas;
        this.victoria = victoria;
        this.derrota = derrota;
        this.puntaje = puntaje;
        this.undos = undos;
        this.movidas = movidas;
    }

    public List<DTOCelda> getCeldas() { return celdas; }
    public boolean isVictoria() { return victoria; }
    public boolean isDerrota() { return derrota; }
    public int getPuntaje() { return puntaje; }
    public int getUndos() { return undos; }
    public int getMovidas() { return movidas; }
}