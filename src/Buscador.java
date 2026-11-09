/**
 * Implementación manual de tres algoritmos de búsqueda sobre un arreglo de
 * enteros ORDENADO de forma ascendente.
 *
 * Cada método cuenta las comparaciones entre el valor buscado y los elementos
 * del arreglo (una "comparación" = una vez que se examina a[i] contra el objetivo).
 *
 *  - Lineal:       O(n)       recorre de izquierda a derecha.
 *  - Binaria:      O(log n)   parte el intervalo por la mitad en cada paso.
 *  - Exponencial:  O(log i)   donde i es la posición del elemento: primero
 *                             encuentra un rango [limite/2, limite] duplicando
 *                             el límite (1, 2, 4, 8, ...) y luego aplica binaria
 *                             solo dentro de ese rango.
 */
public class Buscador {

    private long comparaciones;

    // ------------------------------------------------------------ LINEAL
    /**
     * Búsqueda lineal. Como el arreglo está ordenado, se puede detener en
     * cuanto encuentra un valor mayor que el objetivo (ya no puede aparecer).
     */
    public ResultadoBusqueda lineal(int[] a, int objetivo) {
        validar(a);
        comparaciones = 0;
        long inicio = System.nanoTime();
        int posicion = -1;
        for (int i = 0; i < a.length; i++) {
            comparaciones++;
            if (a[i] == objetivo) {
                posicion = i;
                break;
            }
            if (a[i] > objetivo) {
                break; // los siguientes son todavía mayores
            }
        }
        return new ResultadoBusqueda("Lineal", posicion, comparaciones, System.nanoTime() - inicio);
    }

    // ----------------------------------------------------------- BINARIA
    /** Búsqueda binaria iterativa sobre todo el arreglo. */
    public ResultadoBusqueda binaria(int[] a, int objetivo) {
        validar(a);
        comparaciones = 0;
        long inicio = System.nanoTime();
        int posicion = binariaEnRango(a, objetivo, 0, a.length - 1);
        return new ResultadoBusqueda("Binaria", posicion, comparaciones, System.nanoTime() - inicio);
    }

    /**
     * Binaria entre los índices lo y hi (incluidos). La reutiliza la
     * búsqueda exponencial para su segunda fase.
     */
    private int binariaEnRango(int[] a, int objetivo, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2; // evita desbordamiento de (lo + hi)
            comparaciones++;
            if (a[mid] == objetivo) {
                return mid;
            }
            if (a[mid] < objetivo) {
                lo = mid + 1; // el objetivo está a la derecha
            } else {
                hi = mid - 1; // el objetivo está a la izquierda
            }
        }
        return -1;
    }

    // ------------------------------------------------------- EXPONENCIAL
    /**
     * Búsqueda exponencial.
     *
     * Fase 1 (galope): revisa a[0]; si no es, revisa a[1], a[2], a[4], a[8]...
     * hasta que el límite se sale del arreglo o a[limite] >= objetivo.
     * Así se sabe que el objetivo, si existe, está entre limite/2 y limite.
     *
     * Fase 2: búsqueda binaria solo en ese rango.
     *
     * Ventaja: si el elemento está cerca del inicio, el rango es pequeño y se
     * hacen muy pocas comparaciones, sin necesidad de conocer el tamaño total.
     */
    public ResultadoBusqueda exponencial(int[] a, int objetivo) {
        validar(a);
        comparaciones = 0;
        long inicio = System.nanoTime();
        int posicion;

        if (a.length == 0) {
            posicion = -1;
        } else {
            comparaciones++;
            if (a[0] == objetivo) {
                posicion = 0;
            } else {
                int limite = 1;
                while (limite < a.length) {
                    comparaciones++;
                    if (a[limite] >= objetivo) {
                        break;
                    }
                    limite *= 2; // 1, 2, 4, 8, 16, ...
                }
                int lo = limite / 2;
                int hi = Math.min(limite, a.length - 1);
                posicion = binariaEnRango(a, objetivo, lo, hi);
            }
        }
        return new ResultadoBusqueda("Exponencial", posicion, comparaciones, System.nanoTime() - inicio);
    }

    // --------------------------------------------------------- utilidades
    /** Precondición de binaria y exponencial: arreglo no nulo y ordenado. */
    private void validar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo.");
        }
        if (!estaOrdenado(a)) {
            throw new IllegalArgumentException("El arreglo debe estar ordenado de forma ascendente.");
        }
    }

    public static boolean estaOrdenado(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }
}
