package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model){
        //HttpServletRequest에도 쿠키가 담겨 있긴 할거야.
        if(memberId == null){
            return "home";
        }
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){

        //세션 관리자 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        if(member == null){
            return "home";
        }


        model.addAttribute("member", member);
        return "loginHome";
    }


//    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        // 쿠키 날리는 건.. setMaxAge(0);으로 해준다.
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}