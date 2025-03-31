package kz.iitu.se241m.bookservice.repository;

import kz.iitu.se241m.bookservice.database.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Member save(Member member);
}