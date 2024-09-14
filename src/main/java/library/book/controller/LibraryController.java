package library.book.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import library.book.dto.AuthorCreationRequestDTO;
import library.book.dto.BookCreationRequestDTO;
import library.book.dto.BookLendRequestDTO;
import library.book.dto.MemberCreationRequestDTO;
import library.book.model.Author;
import library.book.model.Book;
import library.book.model.Member;
import library.book.service.LibraryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/library")
@RequiredArgsConstructor
public class LibraryController {
	private final LibraryService libraryService;

	/**
	 * isbn으로 도서 조회
	 * @param isbn
	 * @return
	 */
	@GetMapping("/book")
	public ResponseEntity readBooks(@RequestParam(required = false) String isbn){
		// 파람 값으로 넘어온 isbn이 존재하지 않으면 도서 목록을 불러오고
		if (isbn == null){
			return ResponseEntity.ok(libraryService.readBooks());
		}
		// 있으면 해당 도서 조회
		return ResponseEntity.ok(libraryService.readBook(isbn));
	}

	/**
	 * id로 도서 조회
	 * @param bookId
	 * @return
	 */
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> readBook(@PathVariable Long bookId){
		return ResponseEntity.ok(libraryService.readBook(bookId));
	}

	/**
	 * 도서 추가
	 * @param request
	 * @return
	 */
	@PostMapping("/book")
	public ResponseEntity<Book> createBook(@RequestBody BookCreationRequestDTO request){
		return ResponseEntity.ok(libraryService.createBook(request));
	}

	/**
	 * 도서 삭제
	 * @param bookId
	 * @return
	 */
	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId){
		libraryService.deleteBook(bookId);
		return ResponseEntity.ok().build();
	}

	/**
	 * 회원 생성
	 * @param request
	 * @return
	 */
	@PostMapping("/member")
	public ResponseEntity<Member> createMember(@RequestBody MemberCreationRequestDTO request){
		return ResponseEntity.ok(libraryService.createMember(request));
	}

	/**
	 * 회원 정보 수정
	 * @param request
	 * @param memberId
	 * @return
	 */
	@PatchMapping("/member/{memberId}")
	public ResponseEntity<Member> updateMember(@RequestBody MemberCreationRequestDTO request,
		@PathVariable Long memberId){
		return ResponseEntity.ok(libraryService.updateMember(memberId, request));
	}

	/**
	 * 도서 대출
	 * @param request
	 * @return
	 */
	@PostMapping("/book/lend")
	public ResponseEntity<List<String>> lendBook(@RequestBody BookLendRequestDTO request){
		return ResponseEntity.ok(libraryService.lendBook((List<BookLendRequestDTO>)request));
	}

	/**
	 * 저자 추가
	 * @param request
	 * @return
	 */
	@PostMapping("/author")
	public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreationRequestDTO request){
		return ResponseEntity.ok(libraryService.createAuthor(request));
	}

}
