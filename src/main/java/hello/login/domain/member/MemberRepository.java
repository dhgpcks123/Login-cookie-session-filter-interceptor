package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; //static사용

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member = {}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
        List<Member> all = findAll();

        /*
        for (Member m : all) {
            if(m.getLoginId().equals(loginId)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
        */
        return findAll().stream()
                    .filter(m -> m.getLoginId().equals(loginId))
                    .findFirst();
        //list -> stream으로 바꾸고 loof돌아. filter 만족하면 넘어가.  같은 녀석만 넘어갔겠지? 거기서 먼저 나온 애를 반환해줘! 뒤에 애들은 무시 됨
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
