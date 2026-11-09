import java.util.Random;
import java.util.Scanner;

/**
 * Ejercicio 25 - Búsqueda exponencial.
 *
 * Crea un arreglo ordenado de enteros y compara búsqueda lineal, binaria y
 * exponencial buscando elementos cercanos al inicio, al centro, al final y
 * elementos que no existen.
 */
public class BusquedaExponencialApp {

    private static final Scanner entrada = new Scanner(System.in);
    private static final Buscador buscador = new Buscador();
    private static int[] datos;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  BÚSQUEDA EXPONENCIAL vs BINARIA vs LINEAL");
        System.out.println("=================================================");
        datos = generarOrdenado(1000, 25);
        System.out.println("Se generó un arreglo ordenado de 1000 enteros (semilla 25).");

        int opcion;
        do {
            System.out.println();
            System.out.println("1. Generar un nuevo arreglo ordenado");
            System.out.println("2. Ver el arreglo actual");
            System.out.println("3. Pruebas: inicio, centro, final e inexistentes");
            System.out.println("4. Buscar un valor ingresado por el usuario");
            System.out.println("5. Comparar con diferentes tamaños de arreglo");
            System.out.println("0. Salir");
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> generarNuevo();
                case 2 -> mostrarArreglo();
                case 3 -> pruebasPredefinidas();
                case 4 -> buscarValor();
                case 5 -> compararTamanios();
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("[!] Opción no válida.");
            }
        } while (opcion != 0);
    }

    /**
     * Genera un arreglo estrictamente creciente: cada valor es el anterior
     * más 2, 3 o 4. Así siempre hay "huecos" (valor + 1 nunca está), lo que
     * sirve para probar búsquedas de elementos inexistentes dentro del rango.
     */
    static int[] generarOrdenado(int tamanio, long semilla) {
        Random azar = new Random(semilla);
        int[] a = new int[tamanio];
        int valor = azar.nextInt(5);
        for (int i = 0; i < tamanio; i++) {
            valor += 2 + azar.nextInt(3);
            a[i] = valor;
        }
        return a;
    }

    private static void generarNuevo() {
        int tamanio = leerEntero("Tamaño del arreglo (1 a 10.000.000): ");
        if (tamanio < 1 || tamanio > 10_000_000) {
            System.out.println("[!] Tamaño fuera de rango.");
            return;
        }
        int semilla = leerEntero("Semilla para los números aleatorios: ");
        datos = generarOrdenado(tamanio, semilla);
        System.out.println("[OK] Arreglo de " + tamanio + " elementos. Ordenado: " + Buscador.estaOrdenado(datos));
    }

    private static void mostrarArreglo() {
        int n = datos.length;
        System.out.println("Tamaño: " + n + " | mínimo: " + datos[0] + " | máximo: " + datos[n - 1]);
        if (n <= 40) {
            imprimirRango(0, n);
        } else {
            System.out.print("Primeros 20: ");
            imprimirRango(0, 20);
            System.out.print("Centro (" + (n / 2 - 5) + " a " + (n / 2 + 4) + "): ");
            imprimirRango(n / 2 - 5, n / 2 + 5);
            System.out.print("Últimos 20: ");
            imprimirRango(n - 20, n);
        }
    }

    private static void imprimirRango(int desde, int hasta) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = desde; i < hasta; i++) {
            sb.append(datos[i]);
            if (i < hasta - 1) {
                sb.append(", ");
            }
        }
        System.out.println(sb.append("]"));
    }

    /** Casos obligatorios del ejercicio. */
    private static void pruebasPredefinidas() {
        int n = datos.length;
        int[][] casos = {
                {datos[0], 0},
                {datos[Math.min(3, n - 1)], 0},
                {datos[n / 2], 0},
                {datos[n - 1], 0},
                {datos[Math.max(0, n - 4)], 0},
                {datos[0] - 1, 1},
                {datos[n / 2] + 1, 1},
                {datos[n - 1] + 10, 1},
        };
        String[] descripciones = {
                "Primer elemento (inicio)",
                "Cerca del inicio (pos 3)",
                "Centro del arreglo",
                "Último elemento (final)",
                "Cerca del final",
                "Inexistente: menor que el mínimo",
                "Inexistente: hueco en el centro",
                "Inexistente: mayor que el máximo",
        };

        System.out.println("\nArreglo de " + n + " elementos ordenados.");
        System.out.printf("%-34s %9s | %-18s | %-18s | %-18s%n",
                "Caso", "Valor", "Lineal", "Binaria", "Exponencial");
        System.out.println("-".repeat(108));
        for (int i = 0; i < casos.length; i++) {
            int valor = casos[i][0];
            ResultadoBusqueda l = buscador.lineal(datos, valor);
            ResultadoBusqueda b = buscador.binaria(datos, valor);
            ResultadoBusqueda e = buscador.exponencial(datos, valor);
            System.out.printf("%-34s %9d | %-18s | %-18s | %-18s%n",
                    descripciones[i], valor, celda(l), celda(b), celda(e));
        }
        System.out.println("Formato de cada celda: posición encontrada (o 'no está') / comparaciones.");
    }

    private static String celda(ResultadoBusqueda r) {
        String pos = r.fueEncontrado() ? "pos " + r.getPosicion() : "no está";
        return pos + " / " + r.getComparaciones() + " cmp";
    }

    private static void buscarValor() {
        int valor = leerEntero("Valor a buscar: ");
        System.out.println();
        for (ResultadoBusqueda r : new ResultadoBusqueda[]{
                buscador.lineal(datos, valor),
                buscador.binaria(datos, valor),
                buscador.exponencial(datos, valor)}) {
            String texto = r.fueEncontrado()
                    ? "encontrado en la posición " + r.getPosicion()
                    : "NO existe en el arreglo";
            System.out.printf("%-12s -> %-35s comparaciones: %-7d tiempo: %,d ns%n",
                    r.getAlgoritmo(), texto, r.getComparaciones(), r.getTiempoNanos());
        }
    }

    /**
     * Para cada tamaño, busca el elemento de la posición 10 (inicio), el del
     * centro y el último, y muestra las comparaciones de cada algoritmo.
     * Muestra que la exponencial gana cuando el elemento está cerca del inicio
     * y que en el peor caso hace aproximadamente el doble que la binaria.
     */
    private static void compararTamanios() {
        int[] tamanios = {100, 1_000, 10_000, 100_000, 1_000_000};
        System.out.printf("%n%-10s %-8s | %10s | %8s | %11s%n",
                "Tamaño", "Posición", "Lineal", "Binaria", "Exponencial");
        System.out.println("-".repeat(58));
        for (int n : tamanios) {
            int[] a = generarOrdenado(n, 7);
            int[] posiciones = {10, n / 2, n - 1};
            String[] nombres = {"inicio", "centro", "final"};
            for (int k = 0; k < posiciones.length; k++) {
                int valor = a[posiciones[k]];
                System.out.printf("%-10d %-8s | %10d | %8d | %11d%n", n, nombres[k],
                        buscador.lineal(a, valor).getComparaciones(),
                        buscador.binaria(a, valor).getComparaciones(),
                        buscador.exponencial(a, valor).getComparaciones());
            }
            System.out.printf("%-10d %-8s | %10d | %8d | %11d%n", n, "no está",
                    buscador.lineal(a, a[n - 1] + 1).getComparaciones(),
                    buscador.binaria(a, a[n - 1] + 1).getComparaciones(),
                    buscador.exponencial(a, a[n - 1] + 1).getComparaciones());
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!entrada.hasNextLine()) {
                return 0;
            }
            String texto = entrada.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("[!] Debe escribir un número entero.");
            }
        }
    }
}
