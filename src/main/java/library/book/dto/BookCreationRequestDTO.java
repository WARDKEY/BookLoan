package library.book.dto;

import lombok.Data;

/**
 * 책의 요청 DTO
 */
@Data
public class BookCreationRequestDTO {
	private String name;
	private String isbn;
	private Long authorId;
}
