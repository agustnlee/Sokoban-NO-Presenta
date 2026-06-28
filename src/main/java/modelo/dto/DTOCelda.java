package modelo.dto;

import modelo.entidades.Coordenada;
import modelo.entidades.TipoEntidad;

public class DTOCelda {

    private final Coordenada coordenada;
    private final TipoEntidad tipoEntidad;
    private final TipoEntidad tipoCasilla;
    private final int empujesRestantes;
    private final boolean cajaBloqueada;

    public DTOCelda(Coordenada coordenada, TipoEntidad tipoEntidad, TipoEntidad tipoCasilla) {
        this(coordenada, tipoEntidad, tipoCasilla, -1, false);
    }

    public DTOCelda(Coordenada coordenada, TipoEntidad tipoEntidad, TipoEntidad tipoCasilla, int empujesRestantes, boolean cajaBloqueada) {
        this.coordenada = coordenada;
        this.tipoEntidad = tipoEntidad;
        this.tipoCasilla = tipoCasilla;
        this.empujesRestantes = empujesRestantes;
        this.cajaBloqueada = cajaBloqueada;
    }

    public Coordenada getCoordenada() { return coordenada; }
    public TipoEntidad getTipoEntidad() { return tipoEntidad; }
    public TipoEntidad getTipoCasilla() { return tipoCasilla; }
    public int getEmpujesRestantes() { return empujesRestantes; }
    public boolean isCajaBloqueada() { return cajaBloqueada; }
}