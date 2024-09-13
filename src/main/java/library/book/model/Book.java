package library.book.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "book")
public class Book {
	/**
	 * 도서에 대한 model, pkId와 도서명, ISBN 번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String isbn;

	/**
	 * 저자와 도서는 1:N 관계로 연결
	 */
	@ManyToOne
	@JoinColumn(name = "author_id")
	@JsonManagedReference
	private Author author;

	/**
	 * 책과 대출은 1:N 관계
	 */
	@JsonBackReference
	@OneToMany(mappedBy = "book",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lend> lends;
}

