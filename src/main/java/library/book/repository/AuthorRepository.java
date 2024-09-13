package library.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library.book.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
