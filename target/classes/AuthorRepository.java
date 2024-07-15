public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBirthDateBeforeAndDeathDateAfter(LocalDate date);
}
