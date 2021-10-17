package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member){
        member.setId(sequence++);
        log.info("save : member = {}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        System.out.println("MemberRepository.findById");
        System.out.println(id);
        System.out.println(store);
        System.out.println(store.get(0));
        System.out.println("MemberRepository.findById");
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
//      List<Member> all = findAll();
//      for (Member m : all){
//         if(m.getLoginId().equals(loginId)){
//             return Optional.of(m);
//         };
//      }
//        return Optional.empty();
      //null을 직접 반환하지 않고 빈 Optional을 반환한다.

        return findAll().stream()
                    .filter(m->m.getLoginId().equals(loginId))
                    .findFirst();
    };

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
