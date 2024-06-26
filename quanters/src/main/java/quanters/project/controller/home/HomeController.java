package quanters.project.controller.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quanters.project.dto.login.PrincipalDetails;
import quanters.project.service.aws.S3Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller // ResponseBody 필요없음
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌. Autowired 필요없음
@RequestMapping("/home")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final S3Service s3Service;

    @GetMapping("/home")
    public String goHomePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        Object principal = session.getAttribute("sessionUser");
        if(principal instanceof PrincipalDetails) {
            PrincipalDetails userDetails = (PrincipalDetails) principal;
            String userId = userDetails.getUsername();
            String authorities = userDetails.getAuthorities().toString();
        }
        return "home/home";
    }

    @GetMapping("/search")
    public String goSearchPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        Object principal = session.getAttribute("sessionUser");
        if(principal instanceof PrincipalDetails) {
            PrincipalDetails userDetails = (PrincipalDetails) principal;
            String userId = userDetails.getUsername();
            String authorities = userDetails.getAuthorities().toString();
            model.addAttribute("userId", userId);
        }
        return "home/search";
    }

    @GetMapping("/detail")
    public String searchDetail(@RequestParam(value = "keyword") String keyword, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object principal = session.getAttribute("sessionUser");
        if(principal instanceof PrincipalDetails) {
            PrincipalDetails userDetails = (PrincipalDetails) principal;
            String userId = userDetails.getUsername();
            String authorities = userDetails.getAuthorities().toString();
            model.addAttribute("userId", userId);
        }
        model.addAttribute("keyword", keyword);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        String yyyyMM = formatedNow.substring(0,6);
        String dd = formatedNow.substring(6);
        String result = "";
        try {
            result = s3Service.getObject(yyyyMM + "/result_" + dd + ".csv", keyword);
            model.addAttribute("result", result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "home/detail";
    }
}
