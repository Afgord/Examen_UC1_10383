
package utilerias;

/**
 * Define el contrato que deben cumplir los objetos
 * interesados en recibir notificaciones del modelo.
 *
 * El modelo avisa que ocurrió un cambio y cada observador
 * consulta la información actualizada que necesita.
 */
public interface Observador {

    /**
     * Recibe la notificación de que el modelo cambió.
     *
     * El observador será responsable de consultar
     * los datos actualizados mediante IConsultaModelo.
     */
    void actualizar();
}
