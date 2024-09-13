package library.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library.book.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
