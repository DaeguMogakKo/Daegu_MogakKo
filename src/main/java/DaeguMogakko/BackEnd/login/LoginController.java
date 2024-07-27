package DaeguMogakko.BackEnd.login;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        loginService.isCorrectEmail(loginDto.email());
        loginService.isCorrectPassword(loginDto.email(), loginDto.password());
        String accessToken = loginService.issueAccessToken(loginDto.email());
        String refreshToken = loginService.issueRefreshToken(loginDto.email());
        return new ResponseEntity<>(Map.of(
                "accessToken", accessToken,
                "refresh", refreshToken),
                HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody signupDto signupDto) {
        loginService.signup(signupDto.email(), signupDto.password());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
