/**
 * Resultado de una búsqueda: posición encontrada (-1 si no existe),
 * número de comparaciones realizadas y tiempo empleado.
 */
public class ResultadoBusqueda {

    private final String algoritmo;
    private final int posicion;
    private final long comparaciones;
    private final long tiempoNanos;

    public ResultadoBusqueda(String algoritmo, int posicion, long comparaciones, long tiempoNanos) {
        this.algoritmo = algoritmo;
        this.posicion = posicion;
        this.comparaciones = comparaciones;
        this.tiempoNanos = tiempoNanos;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean fueEncontrado() {
        return posicion >= 0;
    }

    public long getComparaciones() {
        return comparaciones;
    }

    public long getTiempoNanos() {
        return tiempoNanos;
    }
}
