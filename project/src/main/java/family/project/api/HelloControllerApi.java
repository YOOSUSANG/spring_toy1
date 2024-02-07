package family.project.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerApi {

    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }
}
