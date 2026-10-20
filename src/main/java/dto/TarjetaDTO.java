
package dto;

/**
 * Contiene los datos de una tarjeta que serán mostrados
 * al cliente durante el proceso de compra.
 *
 * Permite que la vista consulte la información de la tarjeta
 * sin acceder directamente a la entidad Tarjeta del dominio.
 *
 * @param numero Número de la tarjeta.
 * @param bancoEmisor Nombre del banco que emitió la tarjeta.
 * @param ciudad Ciudad asociada a la tarjeta.
 */
public record TarjetaDTO(
        String numero,
        String bancoEmisor,
        String ciudad
) {
}
