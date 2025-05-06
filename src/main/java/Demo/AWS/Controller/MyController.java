package Demo.AWS.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/")
    public String hello() {
        return "Hello varun, Jenkins Automation using webhook trigger is sucessfully running!!!";
    }
}