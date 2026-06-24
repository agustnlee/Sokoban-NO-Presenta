package modelo.entidades.nomovible.observer;

import java.util.ArrayList;
import java.util.List;
import modelo.entidades.nomovible.observer.PublicadorMuro;

public class PublicadorMuro {

    // Lista que mantiene a todos los muros vinculados a este publicador
    private List<SubscriptorMuro> subscriptores;

    public PublicadorMuro() {
        // Inicializamos la lista vacía al crear el publicador
        this.subscriptores = new ArrayList<>();
    }

    public void suscribir(SubscriptorMuro s) {
        // Agregamos un muro a la lista de oyentes (si no estaba ya)
        if (!this.subscriptores.contains(s)) {
            this.subscriptores.add(s);
        }
    }

    public void desuscribir(SubscriptorMuro s) {
        // Removemos el muro de la lista
        this.subscriptores.remove(s);
    }

    public void notificarSubscriptores(boolean estadoCandado) {
        // Recorremos todos los muros suscritos y les pasamos el nuevo estado de la cerradura.
        // true = se metió la llave (abrir muros). false = se sacó la llave por un undo (cerrar muros).
        for (SubscriptorMuro s : this.subscriptores) {
            s.actualizarMuro(estadoCandado);
        }
    }
}
