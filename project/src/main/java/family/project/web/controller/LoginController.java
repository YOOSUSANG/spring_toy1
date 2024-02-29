package family.project.web.controller;

import family.project.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping()
public class LoginController {

    @GetMapping("/loginhome")
    public String loginHome(@ModelAttribute("member") LoginDto loginDto, @RequestParam(value = "logout", required = false) String logout) {

        if (Objects.equals(logout, "")) {
            return "redirect:/"; // 로그아웃시 홈으로
        }
        return "loginHome";
    }

    @GetMapping("/login")
    public String login(@RequestParam("error") String error, @RequestParam("exception") String exception, @ModelAttribute("member") LoginDto loginDto, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginHome";
    }
}