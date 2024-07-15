import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GutendexApiClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String GUTENDEX_API_URL = "https://gutendex.com/books/";

    public Optional<Book> fetchBookInfo(String title) {
        // Construir la URL de la solicitud
        String url = GUTENDEX_API_URL + "?search=" + title;

        // Realizar la solicitud a la API
        try {
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
            if (response != null && !response.getResults().isEmpty()) {
                // Mapear la respuesta de la API a la entidad Book
                GutendexBook gutendexBook = response.getResults().get(0);
                Book book = new Book();
                book.setTitle(gutendexBook.getTitle());
                book.setLanguage(gutendexBook.getLanguages().get(0));
                book.setDownloads(gutendexBook.getDownload_count());

                Author author = new Author();
                String[] authorName = gutendexBook.getAuthors().get(0).getName().split(", ");
                author.setLastName(authorName[0]);
                author.setFirstName(authorName.length > 1 ? authorName[1] : "");
                book.setAuthor(author);

                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

class GutendexResponse {
    private List<GutendexBook> results;

    // Getters and setters

    public List<GutendexBook> getResults() {
        return results;
    }

    public void setResults(List<GutendexBook> results) {
        this.results = results;
    }
}

class GutendexBook {
    private String title;
    private List<String> languages;
    private int download_count;
    private List<GutendexAuthor> authors;

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public List<GutendexAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<GutendexAuthor> authors) {
        this.authors = authors;
    }
}

class GutendexAuthor {
    private String name;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
