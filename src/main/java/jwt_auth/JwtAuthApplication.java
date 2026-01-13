package jwt_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class JwtAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtAuthApplication.class, args);
    }

    @GetMapping("/private/hello")
    @ResponseStatus(HttpStatus.OK)
    public String privateResource() {
        return "hello authenticated user "
            + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
