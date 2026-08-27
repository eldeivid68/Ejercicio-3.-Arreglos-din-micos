import java.util.List;
import java.util.Scanner;

public class Main {

    // Un solo Scanner es suficiente para trabajar con todas las opciones del menu.
    private static final Scanner sc = new Scanner(System.in);

    // El gestor mantiene las ordenes y se encarga de toda la logica del sistema.
    private static final GestorOrdenes gestor = new GestorOrdenes();

    public static void main(String[] args) {
        int opcion;

        // El programa sigue activo hasta que el usuario decida salir.
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarOrden();
                    break;
                case 2:
                    consultarOrdenes();
                    break;
                case 3:
                    buscarOrden();
                    break;
                case 4:
                    modificarOrden();
                    break;
                case 5:
                    cancelarOrden();
                    break;
                case 6:
                    consultarPorPlaca();
                    break;
                case 7:
                    reporteCostos();
                    break;
                case 8:
                    ordenDeMayorCosto();
                    break;
                case 9:
                    cantidadOrdenes();
                    break;
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }

            // Este espacio ayuda a que cada proceso se distinga mejor en consola.
            System.out.println();
        } while (opcion != 10);

        sc.close();
    }

    // Presenta las operaciones disponibles para administrar el taller.
    private static void mostrarMenu() {
        System.out.println("===== Taller Automotriz - Menu Principal =====");
        System.out.println("1. Registrar orden");
        System.out.println("2. Consultar ordenes");
        System.out.println("3. Buscar orden");
        System.out.println("4. Modificar orden");
        System.out.println("5. Cancelar orden");
        System.out.println("6. Consultar ordenes por placa");
        System.out.println("7. Reporte de costos");
        System.out.println("8. Orden de mayor costo");
        System.out.println("9. Cantidad de ordenes");
        System.out.println("10. Salir");
    }

    // Reune los datos de la nueva orden y deja que el gestor los valide.
    private static void registrarOrden() {
        try {
            int numero = leerEntero("Numero de orden: ");

            System.out.print("Nombre del propietario: ");
            String nombre = sc.nextLine();

            System.out.print("Placa del vehiculo: ");
            String placa = sc.nextLine();

            System.out.print("Descripcion del servicio: ");
            String descripcion = sc.nextLine();

            double costo = leerDouble("Costo estimado: ");

            gestor.registrarOrden(numero, nombre, placa, descripcion, costo);
            System.out.println("Orden registrada correctamente.");
        } catch (OrdenDuplicadaException | DatoInvalidoException e) {
            System.out.println("No se pudo registrar la orden: " + e.getMessage());
        } finally {
            // Se ejecuta tanto si la orden fue registrada como si ocurrio un error.
            System.out.println("Proceso de registro finalizado.");
        }
    }

    // Muestra una copia de todas las ordenes registradas actualmente.
    private static void consultarOrdenes() {
        List<OrdenServicio> ordenes = gestor.consultarOrdenes();

        if (ordenes.isEmpty()) {
            System.out.println("No hay ordenes registradas actualmente.");
            return;
        }

        for (OrdenServicio orden : ordenes) {
            System.out.println(orden);
        }
    }

    // Localiza una orden usando su numero como identificador.
    private static void buscarOrden() {
        try {
            int numero = leerEntero("Numero de orden a buscar: ");
            OrdenServicio orden = gestor.buscarOrden(numero);

            System.out.println("Orden encontrada:");
            System.out.println(orden);
        } catch (OrdenNoEncontradaException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Proceso de busqueda finalizado.");
        }
    }

    // Permite cambiar los datos del servicio que todavia pueden variar.
    private static void modificarOrden() {
        try {
            int numero = leerEntero("Numero de orden a modificar: ");

            System.out.print("Nueva descripcion del servicio: ");
            String descripcion = sc.nextLine();

            double costo = leerDouble("Nuevo costo estimado: ");

            gestor.modificarOrden(numero, descripcion, costo);
            System.out.println("Orden modificada correctamente.");
        } catch (OrdenNoEncontradaException | DatoInvalidoException e) {
            System.out.println("No se pudo modificar la orden: " + e.getMessage());
        } finally {
            System.out.println("Proceso de modificacion finalizado.");
        }
    }

    // Elimina de la coleccion la orden indicada por el usuario.
    private static void cancelarOrden() {
        try {
            int numero = leerEntero("Numero de orden a cancelar: ");
            gestor.cancelarOrden(numero);
            System.out.println("Orden cancelada correctamente.");
        } catch (OrdenNoEncontradaException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Proceso de cancelacion finalizado.");
        }
    }

    // Presenta todas las visitas registradas para un mismo vehiculo.
    private static void consultarPorPlaca() {
        System.out.print("Placa del vehiculo: ");
        String placa = sc.nextLine();

        List<OrdenServicio> resultado = gestor.consultarPorPlaca(placa);

        if (resultado.isEmpty()) {
            System.out.println("No hay ordenes asociadas a esa placa.");
            return;
        }

        for (OrdenServicio orden : resultado) {
            System.out.println(orden);
        }
    }

    // Resume el valor total y el promedio de las ordenes que siguen activas.
    private static void reporteCostos() {
        System.out.printf("Valor total de ordenes activas: Q%.2f%n",
                gestor.calcularValorTotal());
        System.out.printf("Costo promedio de ordenes activas: Q%.2f%n",
                gestor.calcularCostoPromedio());
    }

    // Muestra el trabajo con el costo estimado mas elevado.
    private static void ordenDeMayorCosto() {
        try {
            OrdenServicio orden = gestor.ordenMayorCosto();
            System.out.println("Orden con el costo estimado mas alto:");
            System.out.println(orden);
        } catch (OrdenNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Informa cuantos objetos se encuentran guardados en la coleccion.
    private static void cantidadOrdenes() {
        System.out.println("Cantidad de ordenes registradas: "
                + gestor.cantidadOrdenes());
    }

    // Insiste hasta recibir un numero entero y evita que el programa se cierre.
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.");
            }
        }
    }

    // Tambien controla entradas incorrectas cuando se necesita un decimal.
    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero valido (ej. 250.50).");
            }
        }
    }
}
