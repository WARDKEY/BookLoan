package library.book.dto;

import lombok.Data;

/**
 * 회원 요청 DTO
 */
@Data
public class MemberCreationRequestDTO {
	private String firstName;
	private String lastName;
}
