import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    private HashMap<String, String> contactos;

    private final String archivo = "contacts.csv";

    public AddressBook() {
        contactos = new HashMap<>();
    }

    public void load() {

        File file = new File(archivo);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", 2);

                if (datos.length == 2) {

                    String numero = datos[0].trim();
                    String nombre = datos[1].trim();

                    contactos.put(numero, nombre);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

            for (Map.Entry<String, String> contacto : contactos.entrySet()) {

                String numero = contacto.getKey();
                String nombre = contacto.getValue();

                writer.write(numero + "," + nombre);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public void list() {

        System.out.println();
        System.out.println("Contactos:");

        if (contactos.isEmpty()) {
            System.out.println("No hay contactos registrados.");
            return;
        }

        for (Map.Entry<String, String> contacto : contactos.entrySet()) {

            String numero = contacto.getKey();
            String nombre = contacto.getValue();

            System.out.println(numero + " : " + nombre);
        }
    }

    public void create(String numero, String nombre) {

        if (numero.isEmpty() || nombre.isEmpty()) {
            System.out.println("El número y el nombre son obligatorios.");
            return;
        }

        if (contactos.containsKey(numero)) {
            System.out.println("Ya existe un contacto con ese número.");
            return;
        }

        contactos.put(numero, nombre);

        System.out.println("Contacto creado correctamente.");

        save();
    }

    public void delete(String numero) {

        if (contactos.containsKey(numero)) {

            contactos.remove(numero);

            System.out.println("Contacto eliminado correctamente.");

            save();

        } else {

            System.out.println("No se encontró un contacto con ese número.");
        }
    }

    public void menu() {

        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {

            System.out.println();
            System.out.println("================================");
            System.out.println("        AGENDA TELEFÓNICA");
            System.out.println("================================");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.println("================================");
            System.out.print("Selecciona una opción: ");

            try {

                opcion = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Opción no válida.");
                opcion = 0;
            }

            switch (opcion) {

                case 1:
                    list();
                    break;

                case 2:

                    System.out.print("Ingresa el número telefónico: ");
                    String numero = scanner.nextLine().trim();

                    System.out.print("Ingresa el nombre del contacto: ");
                    String nombre = scanner.nextLine().trim();

                    create(numero, nombre);

                    break;

                case 3:

                    System.out.print("Ingresa el número telefónico a eliminar: ");
                    String numeroEliminar = scanner.nextLine().trim();

                    delete(numeroEliminar);

                    break;

                case 4:

                    save();

                    System.out.println("Contactos guardados.");
                    System.out.println("Programa finalizado.");

                    break;

                default:

                    System.out.println("Opción no válida. Intenta nuevamente.");
            }

        } while (opcion != 4);

        scanner.close();
    }

    public static void main(String[] args) {

        AddressBook agenda = new AddressBook();

        agenda.load();

        agenda.menu();
    }
}