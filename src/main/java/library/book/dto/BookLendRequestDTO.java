package library.book.dto;

import java.util.List;

import lombok.Data;

/**
 * 대출 요청 DTO
 */
@Data
public class BookLendRequestDTO {
	private List<Long> bookIds;
	private Long memberId;
}
