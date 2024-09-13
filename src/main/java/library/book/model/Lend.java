package library.book.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "lend")
public class Lend {

	/**
	 * 대출에 대한 model pkId, 대출 시작 시간, 대출 종료 시간, 대출 상태
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant startOn;
	private Instant dueOn;

	@Enumerated(EnumType.ORDINAL)
	private LendStatus status;

	/**
	 * 책과 대출은 1:N 관계
	 */
	@ManyToOne
	@JoinColumn(name = "book_id")
	@JsonManagedReference
	private Book book;

	/**
	 * 회원과 대출은 1:N 관계
	 */
	@ManyToOne
	@JoinColumn(name = "member_id")
	@JsonBackReference
	private Member member;
}
