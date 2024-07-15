import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al catálogo de libros LiterAlura");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar todos los autores");
            System.out.println("4. Listar autores vivos en un determinado año");
            System.out.println("5. Listar libros por idioma");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el título del libro: ");
                    String title = scanner.nextLine();
                    bookService.saveBook(title);
                    break;
                case 2:
                    List<Book> books = bookService.listAllBooks();
                    books.forEach(book -> System.out.println(book.getTitle() + " - " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()));
                    break;
                case 3:
                    // Implementar la lógica para listar todos los autores
                    break;
                case 4:
                    System.out.print("Introduce el año: ");
                    int year = scanner.nextInt();
                    // Implementar la lógica para listar autores vivos en el año dado
                    break;
                case 5:
                    System.out.print("Introduce el código del idioma (ES, EN, FR, PT): ");
                    String language = scanner.nextLine();
                    List<Book> booksByLanguage = bookService.listBooksByLanguage(language);
                    booksByLanguage.forEach(book -> System.out.println(book.getTitle() + " - " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()));
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
