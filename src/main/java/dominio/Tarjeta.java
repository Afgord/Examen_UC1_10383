
package dominio;

/**
 * Representa una tarjeta utilizada como medio de pago
 * dentro de una compra.
 *
 * Contiene el número de tarjeta, banco emisor y ciudad.
 */
public class Tarjeta {

    private final String numero;
    private final String bancoEmisor;
    private final String ciudad;

    /**
     * Crea una tarjeta con los datos proporcionados.
     *
     * @param numero Número de la tarjeta.
     * @param bancoEmisor Nombre del banco emisor.
     * @param ciudad Ciudad asociada a la tarjeta.
     */
    public Tarjeta(String numero, String bancoEmisor, String ciudad) {
        this.numero = numero;
        this.bancoEmisor = bancoEmisor;
        this.ciudad = ciudad;
    }

    /**
     * @return Número de la tarjeta.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @return Nombre del banco emisor.
     */
    public String getBancoEmisor() {
        return bancoEmisor;
    }

    /**
     * @return Ciudad asociada a la tarjeta.
     */
    public String getCiudad() {
        return ciudad;
    }
}
