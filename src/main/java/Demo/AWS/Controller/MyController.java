package Demo.AWS.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/")
    public String hello() {
        return "Hello This My First Auto Deployment of SpringBoot Application in github";
    }
}