package library.book.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;

import jakarta.persistence.EntityNotFoundException;
import library.book.dto.AuthorCreationRequestDTO;
import library.book.dto.BookCreationRequestDTO;
import library.book.dto.BookLendRequestDTO;
import library.book.dto.MemberCreationRequestDTO;
import library.book.model.Author;
import library.book.model.Book;
import library.book.model.Lend;
import library.book.model.LendStatus;
import library.book.model.Member;
import library.book.model.MemberStatus;
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

	/**
	 * 도서를 생성(생성할 때 저자를 찾아서 직접 지정)
	 * @param book
	 * @return
	 */
	public Book createBook(BookCreationRequestDTO book){
		Optional<Author> author = authorRepository.findById(book.getAuthorId());
		if (!author.isPresent()){
			throw new EntityNotFoundException("저자가 존재하지 않습니다.");
		}
		Book bookToCreate = new Book();
		BeanUtils.copyProperties(book, bookToCreate);
		bookToCreate.setAuthor(author.get());
		return bookRepository.save(bookToCreate);
	}

	/**
	 * id를 기준으로 도서 삭제
	 * @param id
	 */
	public void deleteBook(Long id){
		bookRepository.deleteById(id);
	}

	/**
	 * 회원 생성
	 * @param request
	 * @return
	 */
	public Member createMember(MemberCreationRequestDTO request){
		Member member = new Member();
		BeanUtils.copyProperties(request, member);
		return memberRepository.save(member);
	}

	/**
	 * 회원 정보 업데이트
	 * @param id
	 * @param request
	 * @return
	 */
	public Member updateMember(Long id, MemberCreationRequestDTO request){
		Optional<Member> optionalMember = memberRepository.findById(id);
		if (!optionalMember.isPresent()){
			throw new EntityNotFoundException("회원이 존재하지 않습니다.");
		}
		Member member = optionalMember.get();
		member.setLastName(request.getLastName());
		member.setFirstName(request.getFirstName());
		return memberRepository.save(member);
	}

	/**
	 * 저자 생성
	 * @param request
	 * @return
	 */
	public Author createAuthor(AuthorCreationRequestDTO request){
		Author author = new Author();
		BeanUtils.copyProperties(request, author);
		return authorRepository.save(author);
	}

	/**
	 * 도서 대출
	 * @param list
	 * @return
	 */
	public List<String> lendBook(List<BookLendRequestDTO> list){
		List<String> booksApprovedToBurrow = new ArrayList<>();
		list.forEach(bookLendRequestDTO -> {
			// 회원 아이디로 책 찾기
			Optional<Book> bookForId = bookRepository.findById(bookLendRequestDTO.getMemberId());
			if (!bookForId.isPresent()){
				throw new EntityNotFoundException("책을 찾을 수 없습니다.");
			}

			// 회원 아이디로 회원 찾기
			Optional<Member> memberForId = memberRepository.findById(bookLendRequestDTO.getMemberId());
			if (!memberForId.isPresent()){
				throw new EntityNotFoundException("회원을 찾을 수 없습니다.");
			}

			// 찾은 회원의 정보 불러와서 대출중인지 확인
			Member member = memberForId.get();
			if (member.getStatus() != MemberStatus.ACTIVE){
				throw new RuntimeException("회원이 이미 대출중입니다.");
			}

			// 책과 책의 대출 상태로 빌린 책을 찾음
			Optional<Lend> burrowedBook = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);

			// 책이 대출중이 아니라면
			if (!burrowedBook.isPresent()){
				// 대출 목록에 도서 이름추가한 다음
				booksApprovedToBurrow.add(bookForId.get().getName());

				// 대출 데이터베이스에 추가
				Lend lend = new Lend();
				lend.setMember(memberForId.get());
				lend.setBook(bookForId.get());
				lend.setStatus(LendStatus.BURROWED);
				lend.setStartOn(Instant.now());
				lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
				lendRepository.save(lend);
			}
		});

		return booksApprovedToBurrow;
	}
}
