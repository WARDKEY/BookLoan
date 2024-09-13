package library.book.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {

	/**
	 * 저자에 대한 model, pkId와 성, 이름
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;

	/**
	 * 저자와 도서는 1:N 관계로 연결
	 */
	@JsonBackReference
	@OneToMany(mappedBy = "author"
		,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Book> books;
}
