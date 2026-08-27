public class OrdenServicio {

    // Estos datos identifican la orden, al cliente y el trabajo solicitado.
    private final int numeroOrden;
    private final String nombrePropietario;
    private final String placaVehiculo;
    private String descripcionServicio;
    private double costoEstimado;

    // El constructor recibe la informacion necesaria para crear una orden completa.
    public OrdenServicio(int numeroOrden, String nombrePropietario,
                         String placaVehiculo, String descripcionServicio,
                         double costoEstimado) {
        this.numeroOrden = numeroOrden;
        this.nombrePropietario = nombrePropietario;
        this.placaVehiculo = placaVehiculo;
        this.descripcionServicio = descripcionServicio;
        this.costoEstimado = costoEstimado;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public double getCostoEstimado() {
        return costoEstimado;
    }

    // La descripcion y el costo si pueden cambiar cuando se modifica una orden.
    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public void setCostoEstimado(double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    // Reune los datos en un formato sencillo para mostrarlos en consola.
    @Override
    public String toString() {
        return "Orden #" + numeroOrden
                + " | Propietario: " + nombrePropietario
                + " | Placa: " + placaVehiculo
                + " | Servicio: " + descripcionServicio
                + " | Costo estimado: Q"
                + String.format("%.2f", costoEstimado);
    }
}
