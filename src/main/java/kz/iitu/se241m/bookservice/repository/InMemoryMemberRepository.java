package kz.iitu.se241m.bookservice.repository;

import kz.iitu.se241m.bookservice.database.Member;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public InMemoryMemberRepository() {
        save(new Member(null, "Alice Smith", "alice@example.com", ZonedDateTime.now().minusDays(10)));
        save(new Member(null, "Bob Johnson", "bob@example.com", ZonedDateTime.now().minusDays(5)));
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(members.values());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return members.values().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(sequence.incrementAndGet());
        }
        if (member.getRegistrationDate() == null) {
            member.setRegistrationDate(ZonedDateTime.now());
        }
        members.put(member.getId(), member);
        System.out.println("InMemoryMemberRepository: Saved member - " + member.getName());
        return member;
    }
}