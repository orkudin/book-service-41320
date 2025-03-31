package kz.iitu.se241m.bookservice.service;

import kz.iitu.se241m.bookservice.database.Member;
import kz.iitu.se241m.bookservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Scope("prototype") // Каждый раз при запросе будет создаваться новый экземпляр бина
public class MemberService {

    // Field Injection - Внедрение зависимости через поле
    // Считается менее предпочтительным, чем конструктор или сеттер, т.к. усложняет тестирование
    // и может скрыть зависимости. Но задание требует его показать.
    @Autowired
    private MemberRepository memberRepository;

    // Инъекция значения из application.properties
    @Value("${library.member.registration.default-bonus-days:0}") // :0 - значение по умолчанию, если свойство не найдено
    private int registrationBonusDays;

    @Value("${library.name}") // Ещё одно значение из properties
    private String libraryName;


    public MemberService(){
        System.out.println("MemberService (prototype): New instance created.");
    }

    public List<Member> getAllMembers() {
        System.out.println("MemberService: Requesting all members for library: " + libraryName);
        return memberRepository.findAll();
    }

    public Member registerMember(String name, String email) {
        System.out.println("MemberService: Registering new member: " + name);
        // Проверка на существующий email (простая бизнес-логика)
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            System.out.println("MemberService: Email " + email + " is already registered.");
            // В реальном приложении нужно вернуть ошибку или существующего пользователя
            return existingMember.get();
        }

        Member newMember = new Member();
        newMember.setName(name);
        newMember.setEmail(email);
        // Используем значение из properties для установки даты регистрации (просто пример)
        newMember.setRegistrationDate(ZonedDateTime.now().minusDays(registrationBonusDays));

        return memberRepository.save(newMember);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }
}