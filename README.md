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

* **🏺 Cajas Frágiles con Desgaste:** A diferencia de las normales, estas tienen un contador de resistencia visible con un máximo de 18 empujes. Si calculás mal tu ruta y el contador llega a cero, la evidencia se destruye y perdés el nivel instantáneamente.
* **🔌 Sistema de Llaves y Muros:** Incorporamos "Cajas Llave" que no sirven para completar el nivel, sino que son puramente instrumentales. Debes empujarlas estratégicamente hacia una "Casilla Candado"; al hacerlo, se desbloquean muros cerrados en el mapa, habilitando nuevos pasajes.
* **🧊 Físicas de Deslizamiento (Terreno Resbaladizo):** Al empujar una caja sobre estas casillas, pierdes el control manual: la caja continúa deslizándose automáticamente dentro del mismo turno hasta chocar con un obstáculo sólido o salir de la zona resbaladiza. El jugador empuja una vez, y el terreno hace el resto.
* **🪜 Escaleras Transitables:** Una mecánica completamente nueva diseñada para niveles complejos divididos por paredes continuas ("islas"). Puedes empujar una escalera contra un obstáculo sólido y luego caminar hacia ella para trepar y teletemporarte al primer espacio vacío del otro lado.
* **🔒 Seguro Antifrustración (Bloqueo en Destino):** En los puzzles ajustados, un empuje accidental sobre una caja ya acomodada puede arruinar todo el progreso. Implementamos un sistema donde las cajas se bloquean automáticamente al llegar a su destino (señalizado con un cambio visual), impidiendo que las muevas por error.
* **⏪ Deshacer (Undo) Estratégico:** Nuestro sistema de *Undo* guarda estados completos del tablero, con retroceso de 5 movimientos hacia atrás.

---

## 📜 Reglas de Juego y Entidades

Las reglas físicas fundamentales son: el jugador solo puede empujar cajas (nunca tirar de ellas), no puede empujar más de una caja a la vez, y nadie puede atravesar paredes o muros cerrados.

### Tipos de Cajas y Mecánicas

* **💰 Cajas Normales (Bolsa de Dinero):** Se desplazan exactamente un casillero. Al llegar a una casilla de destino (caja de seguridad abierta), quedan bloqueadas automáticamente y la caja de seguridad se cierra visualmente. No pueden volver a moverse.
* **🏺 Cajas Frágiles (Jarrón):** Tienen una resistencia limitada a un máximo de 18 empujes, visible en todo momento sobre la imagen. Cada empuje válido reduce su resistencia. Si llega a cero, el nivel termina en derrota.
* **🔑 Cajas Llave (Caja de Herramientas) y Muros:** Para abrir muros cerrados, debés empujar una Caja Llave hacia una Casilla Candado. Al hacerlo, la caja queda depositada permanentemente, el circuito se representa visualmente como activado, y los muros vinculados se abren.
* **🧊 Terreno Resbaladizo:** Al empujar cualquier caja hacia este tipo de casilla, continuará deslizándose automáticamente en esa misma dirección hasta chocar con un obstáculo sólido o llegar a una casilla normal.
* **🪜 Escaleras:** Primero empujala para apoyarla contra un obstáculo. Luego, al caminar hacia ella, el jugador trepará y aparecerá en el primer espacio vacío al otro lado.

### Sistema de Deshacer (Undo)

* El juego almacena los últimos **15 estados** del nivel.
* Cada uso retrocede el tablero exactamente **5 movimientos** hacia atrás. Si hay menos de 5 disponibles, retrocede hasta el estado inicial del nivel.
* Cada uso genera una **penalización de 8 puntos** sobre el puntaje final.

---

## 🗺️ Niveles y Progresión

El juego cuenta con una campaña de **6 niveles** accesibles desde el menú principal o desde el selector de niveles individual. La dificultad presenta una curva progresiva:

* **Niveles Iniciales:** Introducen las mecánicas básicas de empuje y el manejo de cajas frágiles.
* **Niveles Intermedios:** Incorporan muros cerrados y cajas llave, obligando a planificar el orden exacto de los movimientos.
* **Niveles Avanzados:** Combinan todos los elementos e introducen terreno resbaladizo y escaleras, creando puzzles que requieren precisión y pensamiento lateral.

---

## 🏆 Condiciones de Victoria, Derrota y Puntaje

* **🏅 Victoria:** El nivel se completa cuando **todas** las cajas normales y frágiles están sobre sus casillas de destino. *(Las cajas llave no cuentan para la condición de victoria).* Si al completar el nivel el puntaje es negativo, se considera derrota por recursos agotados en lugar de victoria.
* **💀 Derrota:** El nivel se pierde si el contador de una Caja Frágil llega a cero, o si al completar todas las cajas el puntaje acumulado es negativo.
* **💯 Puntaje:** Se muestra en tiempo real en el HUD junto al contador de movimientos y undos utilizados. Se calcula de la siguiente manera:
    * Puntaje base: **130 puntos**
    * Cada movimiento efectuado: **-1 punto**
    * Cada uso del botón Undo: **-8 puntos**
    * El puntaje mínimo visible en el HUD es 0, pero internamente puede volverse negativo — lo que activa la derrota al completar el nivel si los recursos se agotan.

---

## 👥 Equipo "NO Presenta"

Este proyecto fue planificado y desarrollado mediante división de módulos por el siguiente equipo:

* **Agustín:** Project Management, Diseño de niveles, Estética visual, desarrollador principal del UML, Modelo y Vista.
* **Tomás Augusto Romero:** Desarrollo del gestor de sonidos y entidades no movibles, UML, y soporte de desarrollo.
* **Facundo:** Testing, Proofreading y soporte de la interfaz de usuario.

¡Diviértete planificando tus movimientos con cuidado y optimizá tu estrategia para resolver el caso con el puntaje más alto!