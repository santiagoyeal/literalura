import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GutendexApiClient gutendexApiClient;

    public Book saveBook(String title) {
        // Verificar si el libro ya existe en la base de datos
        Optional<Book> existingBook = bookRepository.findByTitle(title);
        if (existingBook.isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return existingBook.get();
        }

        // Obtener la información del libro desde la API Gutendex
        Optional<Book> bookInfo = gutendexApiClient.fetchBookInfo(title);
        if (bookInfo.isPresent()) {
            Book book = bookInfo.get();
            // Guardar el autor si no existe
            Author author = book.getAuthor();
            Optional<Author> existingAuthor = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            if (existingAuthor.isPresent()) {
                book.setAuthor(existingAuthor.get());
            } else {
                authorRepository.save(author);
            }
            return bookRepository.save(book);
        } else {
            System.out.println("El libro no fue encontrado.");
            return null;
        }
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
}
