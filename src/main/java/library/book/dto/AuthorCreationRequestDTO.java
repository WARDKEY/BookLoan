package library.book.dto;

import lombok.Data;

/**
 * 저자의 이름 요청 DTO
 */
@Data
public class AuthorCreationRequestDTO {
	private String firstName;
	private String lastName;
}
