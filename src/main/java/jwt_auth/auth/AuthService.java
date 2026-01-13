package jwt_auth.auth;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jwt_auth.User.LoginUserDto;
import jwt_auth.User.User;
import jwt_auth.User.UserRepository;
import jwt_auth.exceptions.LoginException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepository,
        JwtService jwtService,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(User user) {
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) {
            throw new LoginException("This email is already being used.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.createUser(user);
        user = userRepository.getUserByEmail(user.getEmail()).get();

        return jwtService.generateToken(user);
    }

    public String login(LoginUserDto loginUser) {
        Optional<User> userOpt = userRepository.getUserByEmail(loginUser.getEmail());

        if (userOpt.isEmpty())
            throw new LoginException("User not found");
        if (!passwordEncoder.matches(loginUser.getPassword(), userOpt.get().getPassword()))
            throw new LoginException("Invalid credentials");

        return jwtService.generateToken(userOpt.get());
    }

}
