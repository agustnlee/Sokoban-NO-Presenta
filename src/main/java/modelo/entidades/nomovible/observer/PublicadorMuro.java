package modelo.entidades.nomovible.observer;

import java.util.ArrayList;
import java.util.List;

public class PublicadorMuro {


    private List<SubscriptorMuro> subscriptores;

    public PublicadorMuro() {
        this.subscriptores = new ArrayList<>();
    }

    public void suscribir(SubscriptorMuro s) {
        if (!this.subscriptores.contains(s)) {
            this.subscriptores.add(s);
        }
    }

    public void desuscribir(SubscriptorMuro s) {
        this.subscriptores.remove(s);
    }

    public void notificarSubscriptores(boolean estadoCandado) {
        for (SubscriptorMuro s : this.subscriptores) {
            s.actualizarMuro(estadoCandado);
        }
    }
}
