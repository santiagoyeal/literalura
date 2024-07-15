@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String language;
    private int downloads;
    @ManyToOne
    private Author author;

    // Getters and setters
}
