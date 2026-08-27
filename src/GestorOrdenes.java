import java.util.ArrayList;
import java.util.List;

public class GestorOrdenes {

    // List define el tipo de coleccion y ArrayList crea su version dinamica.
    private final List<OrdenServicio> ordenes;

    // Cada gestor comienza con una coleccion vacia y sin un limite fijo.
    public GestorOrdenes() {
        this.ordenes = new ArrayList<>();
    }

    // Valida la informacion antes de permitir que una orden llegue a la lista.
    public void registrarOrden(int numeroOrden, String nombrePropietario,
                               String placaVehiculo, String descripcionServicio,
                               double costoEstimado)
            throws OrdenDuplicadaException, DatoInvalidoException {

        if (existeOrden(numeroOrden)) {
            throw new OrdenDuplicadaException(
                    "Ya existe una orden registrada con el numero "
                            + numeroOrden + ".");
        }

        if (nombrePropietario == null || nombrePropietario.trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El nombre del propietario no puede estar vacio.");
        }

        if (placaVehiculo == null || placaVehiculo.trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "La placa del vehiculo no puede estar vacia.");
        }

        if (descripcionServicio == null
                || descripcionServicio.trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "La descripcion del servicio no puede estar vacia.");
        }

        if (costoEstimado <= 0) {
            throw new DatoInvalidoException(
                    "El costo estimado debe ser mayor que 0.");
        }

        // Se guardan textos sin espacios sobrantes para mantener datos consistentes.
        ordenes.add(new OrdenServicio(
                numeroOrden,
                nombrePropietario.trim(),
                placaVehiculo.trim(),
                descripcionServicio.trim(),
                costoEstimado));
    }

    // Se devuelve una copia para que nadie modifique la lista sin validaciones.
    public List<OrdenServicio> consultarOrdenes() {
        return new ArrayList<>(ordenes);
    }

    // Recorre la coleccion hasta encontrar el numero solicitado.
    public OrdenServicio buscarOrden(int numeroOrden)
            throws OrdenNoEncontradaException {

        for (OrdenServicio orden : ordenes) {
            if (orden.getNumeroOrden() == numeroOrden) {
                return orden;
            }
        }

        throw new OrdenNoEncontradaException(
                "No existe una orden registrada con el numero "
                        + numeroOrden + ".");
    }

    // Solamente modifica la informacion permitida por el enunciado.
    public void modificarOrden(int numeroOrden, String nuevaDescripcion,
                               double nuevoCosto)
            throws OrdenNoEncontradaException, DatoInvalidoException {

        OrdenServicio orden = buscarOrden(numeroOrden);

        if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "La descripcion del servicio no puede estar vacia.");
        }

        if (nuevoCosto <= 0) {
            throw new DatoInvalidoException(
                    "El costo estimado debe ser mayor que 0.");
        }

        orden.setDescripcionServicio(nuevaDescripcion.trim());
        orden.setCostoEstimado(nuevoCosto);
    }

    // Buscar primero permite informar claramente cuando la orden no existe.
    public void cancelarOrden(int numeroOrden)
            throws OrdenNoEncontradaException {
        OrdenServicio orden = buscarOrden(numeroOrden);
        ordenes.remove(orden);
    }

    // Una placa puede aparecer varias veces, por eso se guardan todas las coincidencias.
    public List<OrdenServicio> consultarPorPlaca(String placa) {
        List<OrdenServicio> resultado = new ArrayList<>();

        if (placa == null) {
            return resultado;
        }

        for (OrdenServicio orden : ordenes) {
            if (orden.getPlacaVehiculo().equalsIgnoreCase(placa.trim())) {
                resultado.add(orden);
            }
        }

        return resultado;
    }

    // Acumula el costo de todo lo que continua almacenado como orden activa.
    public double calcularValorTotal() {
        double total = 0;

        for (OrdenServicio orden : ordenes) {
            total += orden.getCostoEstimado();
        }

        return total;
    }

    // La lista vacia se controla para no dividir entre cero.
    public double calcularCostoPromedio() {
        if (ordenes.isEmpty()) {
            return 0;
        }

        return calcularValorTotal() / ordenes.size();
    }

    // Compara cada costo y conserva la orden mas cara encontrada hasta el momento.
    public OrdenServicio ordenMayorCosto()
            throws OrdenNoEncontradaException {

        if (ordenes.isEmpty()) {
            throw new OrdenNoEncontradaException(
                    "No hay ordenes registradas actualmente.");
        }

        OrdenServicio mayor = ordenes.get(0);

        for (OrdenServicio orden : ordenes) {
            if (orden.getCostoEstimado() > mayor.getCostoEstimado()) {
                mayor = orden;
            }
        }

        return mayor;
    }

    public int cantidadOrdenes() {
        return ordenes.size();
    }

    // Esta comprobacion privada evita repetir numeros de orden.
    private boolean existeOrden(int numeroOrden) {
        for (OrdenServicio orden : ordenes) {
            if (orden.getNumeroOrden() == numeroOrden) {
                return true;
            }
        }

        return false;
    }
}

// Las excepciones pueden compartir este archivo porque no son clases publicas.
// Siguen siendo clases independientes y se deben representar tambien en el UML.

class OrdenDuplicadaException extends Exception {

    public OrdenDuplicadaException(String mensaje) {
        super(mensaje);
    }
}

class OrdenNoEncontradaException extends Exception {

    public OrdenNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

class DatoInvalidoException extends Exception {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
