
---

#  Serpientes y Escaleras – Lautaro Marino - Poo 2025 - UnLu

---

## Reglas del Juego

* Cada jugador comienza en la posición **0**.
* En cada turno, un jugador **lanza el dado** y avanza el número indicado.
* Si cae en una:

  * **Escalera** → sube a una posición más alta.
  * **Serpiente** → baja a una posición más baja.
* Si un jugador saca **tres 6 seguidos**, se pierde el turno y el jugador no avanza.
* El primer jugador que llega o supera la **última casilla** gana la partida.
* El jugador no puede pasarse de la casilla 100, si lo hace, no avanza.
* Cada cambio en el juego se muestra en la consola mediante la **VistaConsola**.

---

## Cómo ejecutar el juego

La clase principal es:

```
App
```

Dentro de `main()` simplemente se inicializan:

* el **modelo** (`Juego`)
* la **vista** (`VistaConsola`)
* el **controlador** (`ControladorConsola`)

### Para ejecutarlo

1. Ejecutá la clase:

```
src/
 └── ar/edu/unlu/game/App.java
```

---

## Estructura de Carpetas

```
src/
└── ar/edu/unlu/game/
    ├── App.java                     # Ejecucion del juego
    │
    ├── Controlador/
    │     └── ControladorConsola.java   # Controlador 
    │
    ├── Vista/
    │     └── VistaConsola.java         # Vista
    │
    ├── Model/
    │     ├── Juego.java                # Modelo
    │     ├── Jugador.java
    │     ├── Dado.java
    │     ├── Tablero.java
    │     ├── Casilla.java
    │     ├── CasillaEscalera.java
    │     ├── CasillaSerpiente.java
    │
    └── Observer/
          ├── Observable.java           # Interface Observable
          └── Observador.java           # Interface Observador
```
