package modelo.entidades.movible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.strategy.caja.ComportamientoCaja;

public abstract class CajaAbstracta extends EntidadMovible {

    protected ComportamientoCaja estrategia;

    protected CajaAbstracta() { }

    public void setEstrategia(ComportamientoCaja estrategia) {
        this.estrategia = estrategia;
    }

    public ComportamientoCaja getEstrategia() {
        return estrategia;
    }

    // delegacion a estrategia 
    
    @Override
    public boolean cuentaParaVictoria() {
        return estrategia.cuentaParaVictoria();
    }

    @Override
    public boolean estaEnEstadoDerrota() {
        return estrategia.estaEnEstadoDerrota();
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA;
    }
}