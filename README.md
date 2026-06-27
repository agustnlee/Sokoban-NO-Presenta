# Sokoban - Equipo "NO Presenta" 🕵️‍♂️📦

**Trabajo Integrador - Proceso de Desarrollo de Software** **Profesor:** Jonathan Pepe  
**Universidad Argentina de la Empresa (UADE)**

Bienvenido a nuestra versión extendida del clásico videojuego de lógica y rompecabezas Sokoban.

En esta adaptación, el jugador asume el rol de un investigador privado que debe reconstruir la escena de un crimen, reproduciendo los movimientos exactos que realizó un sospechoso. A través de una estética en la que las transiciones de movimiento se ilustran como bocetos a mano alzada, deberás empujar cajas (evidencia, dinero, etc.) hacia sus destinos correctos utilizando la lógica y el pensamiento estratégico.

---

## 🚀 Cómo ejecutar el juego

El proyecto fue desarrollado en Java utilizando la biblioteca gráfica Swing para la interfaz 2D. Para ejecutarlo localmente:

1. Clona este repositorio en tu máquina local:
   `git clone https://github.com/agustnlee/Sokoban-NO-Presenta.git`
2. Abre el proyecto con tu IDE de preferencia (como IntelliJ IDEA o Eclipse).
3. Localiza y ejecuta la clase principal `MainVista.java` para iniciar la aplicación.

---

## 🎮 Cómo se juega (Controles)

La premisa básica es empujar las cajas hasta su lugar correcto completando el nivel de la manera más eficiente posible.

* **Movimiento:** Utiliza las **teclas direccionales (flechas)** del teclado para mover al investigador por el mapa. Alternativamente, puedes usar los botones visuales de dirección integrados en la interfaz (HUD).
* **Deshacer (Undo):** Si cometes un error, puedes presionar el botón de "Undo" en la interfaz para retroceder en el tiempo.
* **Reinicio:** Tienes a disposición un botón para reiniciar el nivel actual por completo.

---

## ✨ Lo que nos hace diferentes: Mecánicas Exclusivas

Tomamos la elegante simplicidad del Sokoban clásico de 1980 y expandimos su profundidad estratégica sumando nuevas entidades y mecánicas sin perder la esencia original.

Estas son las innovaciones principales desarrolladas por nuestro equipo:

* **Cajas Frágiles con Desgaste:** A diferencia de las normales, estas tienen un contador de resistencia visible con un máximo de 10 empujes. Si calculás mal tu ruta y el contador llega a cero, la evidencia se destruye y perdés el nivel instantáneamente.
* **Sistema de Llaves y Muros:** Incorporamos "Cajas Llave" que no sirven para completar el nivel, sino que son puramente instrumentales. Debes empujarlas estratégicamente hacia una "Casilla Candado"; al hacerlo, se desbloquean muros cerrados en el mapa, habilitando nuevos pasajes.
* **Físicas de Deslizamiento (Terreno Resbaladizo):** Al empujar una caja sobre estas casillas, pierdes el control manual: la caja continúa deslizándose automáticamente dentro del mismo turno hasta chocar con un obstáculo sólido o salir de la zona resbaladiza. El jugador empuja una vez, y el terreno hace el resto.
* **Escaleras Transitables:** Una mecánica completamente nueva diseñada para niveles complejos divididos por paredes continuas ("islas"). Puedes empujar una escalera contra un obstáculo sólido y luego caminar hacia ella para trepar y teletransportarte al primer espacio vacío del otro lado.
* **Seguro Antifrustración (Bloqueo en Destino):** En los puzzles ajustados, un empuje accidental sobre una caja ya acomodada puede arruinar todo el progreso. Implementamos un sistema donde las cajas se bloquean automáticamente al llegar a su destino (señalizado con un cambio visual), impidiendo que las muevas por error.
* **Deshacer (Undo) Estratégico:** Nuestro sistema de *Undo* guarda estados completos del tablero, con retroceso de 5 movimientos hacia atrás.

---

## 📜 Reglas de Juego y Entidades

El espacio de decisiones se ha enriquecido con nuevas mecánicas. Las reglas físicas fundamentales son: el jugador solo puede empujar cajas (nunca tirar de ellas), no puede empujar más de una caja a la vez, y nadie puede atravesar paredes o muros cerrados.

### Tipos de Cajas y Mecánicas
* **📦 Cajas Normales:** Al empujarlas, se desplazan exactamente un casillero. Una vez que llegan a una casilla de destino, quedan bloqueadas para evitar errores accidentales y no pueden volver a ser movidas.
* **💥 Cajas Frágiles:** Conservan el movimiento normal, pero tienen una resistencia limitada a un máximo de 10 empujes, visible en todo momento. Cada empuje válido reduce su resistencia.
* **🔑 Cajas Llave y Muros:** Existen muros cerrados que bloquean el paso de forma temporal. Para abrirlos, debes empujar una "Caja Llave" hacia una "Casilla Candado" (cerrojo). Al hacerlo, la caja se deposita permanentemente y los muros vinculados transicionan a estado abierto, permitiendo el paso.
* **🧊 Terreno Resbaladizo:** Si empujas cualquier caja hacia este tipo de casilla, la caja continuará deslizándose automáticamente en esa misma dirección. Solo se detendrá al chocar con un obstáculo sólido o al llegar a una casilla que no sea resbaladiza.
* **🪜 Escaleras:** Permiten cruzar paredes o cajas. Primero debes empujarla para dejarla apoyada contra el obstáculo. Luego, al intentar caminar hacia ella, el jugador trepará y aparecerá en el primer espacio vacío al otro lado (si hay lugar disponible).

### Sistema de Deshacer (Undo)
* El juego almacena los últimos 15 estados del nivel.
* Cada uso del botón Undo retrocede el tablero exactamente 5 movimientos hacia atrás.
* Se permite un máximo de 3 usos de forma consecutiva.
* ¡Atención! Cada uso del Undo genera una penalización sobre el puntaje final del nivel.

---

## 🗺️ Niveles y Progresión

El juego cuenta con una campaña principal de **10 niveles** diseñados secuencialmente. Cada nivel representa una nueva "escena del crimen" o pieza del caso que el investigador debe resolver, accesibles a través de un menú interactivo.

La dificultad presenta una curva progresiva diseñada para enseñar las mecánicas orgánicamente:
* **Niveles Iniciales:** Introducen las mecánicas básicas de empuje, el reconocimiento del espacio cerrado y el manejo cuidadoso de las cajas frágiles.
* **Niveles Intermedios:** Incorporan los muros cerrados y las cajas llave, obligando al jugador a planificar el orden exacto de sus movimientos para desbloquear áreas antes de acomodar la evidencia final.
* **Niveles Avanzados:** Combinan todos los elementos anteriores e introducen el terreno resbaladizo y las escaleras, creando puzzles complejos (incluyendo mapas divididos en "islas") que requieren precisión milimétrica y pensamiento lateral.

---

## 🏆 Condiciones de Victoria, Derrota y Puntaje

* **🏅 Victoria:** El nivel se completa y avanza automáticamente cuando **todas** las cajas normales y frágiles del mapa están posicionadas sobre casillas de destino. *(Las cajas llave no cuentan para la condición de victoria).*
* **💀 Derrota:** El nivel se pierde inmediatamente si el contador de resistencia de una Caja Frágil llega a cero y la caja se destruye.
* **💯 Puntaje:** Durante la partida, el HUD mostrará tu cantidad de movimientos y empujes. Al finalizar cada nivel, un sistema interno calculará tu puntaje final evaluando la cantidad de movimientos, los empujes efectuados, el tiempo transcurrido y la penalización por uso del undo.

¡Diviértete planificando tus movimientos con cuidado y optimiza tu estrategia para resolver el caso con el puntaje más alto!