package library.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	/**
	 *  isbn 번호로 도서 찾기
	 * @param isbn
	 * @return
	 */
	Optional<Book> findByIsbn(String isbn);
}
