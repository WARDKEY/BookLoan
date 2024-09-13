package library.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library.book.model.Book;
import library.book.model.Lend;
import library.book.model.LendStatus;

public interface LendRepository extends JpaRepository<Lend, Long> {

	/**
	 * 책과 대출 상태 조회
	 * @param book
	 * @param status
	 * @return
	 */
	Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
