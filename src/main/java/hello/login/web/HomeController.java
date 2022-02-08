package hello.login.web;

import hello.login.domain.member.MemberRepository;
import hello.login.domain.member.Member;
import hello.login.domain.session.SessionConst;
import hello.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    //@GetMapping("/")
    public String home() {
        return "home";
    }


    //@GetMapping("/")
    public String homeLogin(
            @CookieValue(name = "memberId", required = false) Long memberId,
            Model model) {
        if (memberId == null) {
            return "home";
        }
        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession httpSession = request.getSession(false); //로그인 안하고 접속될 경우도 있으니까
        if(httpSession == null)
        {
            return "home";
        }

        Member loginMember = (Member)httpSession.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //@GetMapping("/")  //sessionAttribute를 통해 매핑
    public String homeLoginV3Spring(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {  //애노테이션으로 세션을 찾아주는 것 까지

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")  //argument resolver를 통해 매핑
    public String homeLoginV3ArgResolver(@Login Member loginMember, Model model) {  //애노테이션으로 멤버를 찾아줌 , 이렇게 custom 애노테이션은 그 동작을 하는 클래스를 만들어주고 config에 등록을 해줘야지 인식이 되어 사용 가능

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }




}