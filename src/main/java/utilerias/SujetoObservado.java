
package utilerias;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para los objetos que necesitan notificar cambios
 * a otros componentes del sistema.
 *
 * Administra una lista de observadores registrados y proporciona
 * las operaciones necesarias para agregarlos, eliminarlos y
 * notificarles cuando ocurre una modificación.
 * ModeloCompra heredará esta clase para implementar el patrón
 * Observer Pull.
 */
public abstract class SujetoObservado {

    private final List<Observador> observadores;

    /**
     * Inicializa la lista de observadores.
     */
    public SujetoObservado() {
        observadores = new ArrayList<>();
    }

    /**
     * Registra un observador para recibir notificaciones.
     *
     * @param observador Objeto interesado en los cambios del modelo.
     */
    public void agregarObservador(Observador observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador previamente registrado.
     *
     * @param observador Observador que dejará de recibir notificaciones.
     */
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados.
     *
     * Cada observador ejecutará actualizar() para consultar
     * los datos que necesita mediante la interfaz IConsultaModelo.
     */
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }
}
