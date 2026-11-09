# Búsqueda exponencial (Ejercicio 25)

Taller práctico de algoritmos de búsqueda — Estructuras de Datos — Universidad de Cartagena.

Programa Java que crea un arreglo ordenado de enteros e implementa manualmente:

- Búsqueda lineal — O(n)
- Búsqueda binaria — O(log n)
- Búsqueda exponencial — O(log i), donde i es la posición del elemento

Busca elementos cercanos al inicio, al centro y al final, elementos inexistentes
(menor que el mínimo, hueco en el centro y mayor que el máximo) y compara la
cantidad de comparaciones de cada algoritmo, también con tamaños de 100 a 1.000.000.

## Ejecutar

Requiere Java 17 o superior.

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out BusquedaExponencialApp
```

## Archivos

- `Buscador.java`: los tres algoritmos y el conteo de comparaciones.
- `ResultadoBusqueda.java`: posición, comparaciones y tiempo de una búsqueda.
- `BusquedaExponencialApp.java`: menú, generación de datos y tablas comparativas.
