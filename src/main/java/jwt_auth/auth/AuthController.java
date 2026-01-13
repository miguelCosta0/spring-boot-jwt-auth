package jwt_auth.auth;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import jwt_auth.User.LoginUserDto;
import jwt_auth.User.User;

@RestController
@RequestMapping(
    path = "/auth",
    produces = MediaType.TEXT_PLAIN_VALUE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody @Valid User user) {
        return authService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginUserDto user) {
        return authService.login(user);
    }

}
