package vista.datos;

public enum TipoCeldaVisual {

    // ENUMERACION PARA TIPO DE RENDERIZADO VISUAL
    // Pasadas de paint componente, de primero a ultimo en orden 

    // Casilla (Fondo)
    VACIO,
    DESTINO,
    RESBALADIZO,
    CANDADO,
    MURO_ABIERTO,

    // Entidades sólidas (Intermedio)
    PARED,
    MURO_CERRADO,

    // Cajas (Intermedio)
    CAJA_NORMAL,
    CAJA_NORMAL_EN_DESTINO,
    CAJA_FRAGIL,
    CAJA_FRAGIL_EN_DESTINO,
    CAJA_LLAVE,
    CAJA_LLAVE_EN_CANDADO,

    // Escalera (Intermedio)
    ESCALERA,

    // Jugador (Frente)
    JUGADOR
}