package library.book.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
