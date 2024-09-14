package library.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import library.book.model.Book;
import library.book.repository.AuthorRepository;
import library.book.repository.BookRepository;
import library.book.repository.LendRepository;
import library.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryService {

	private final AuthorRepository authorRepository;
	private final MemberRepository memberRepository;
	private final LendRepository lendRepository;
	private final BookRepository bookRepository;

	/**
	 * id를 기준으로 도서를 조회
	 * @param id
	 * @return
	 */
	public Book readBook(Long id){
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()){
			return book.get();
		}

		throw new EntityNotFoundException("해당 아이디의 책을 찾을 수 없습니다.");
	}

	/**
	 * 모든 도서 조회
	 * @return
	 */
	public List<Book> readBooks(){
		return bookRepository.findAll();
	}

	/**
	 * isbn을 기준으로 도서를 조회
	 * @param isbn
	 * @return
	 */
	public Book readBook(String isbn){
		Optional<Book> book = bookRepository.findByIsbn(isbn);
		if (book.isPresent()){
			return book.get();
		}

		throw new EntityNotFoundException("해당 isbn의 책을 찾을 수 없습니다.");
	}

	public Book createBook(BookCreattionRequest boookl){

	}
}
