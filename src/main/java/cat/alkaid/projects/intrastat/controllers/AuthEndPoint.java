package cat.alkaid.projects.intrastat.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import cat.alkaid.projects.intrastat.auth.JwtTokenFactory;
import cat.alkaid.projects.intrastat.security.AppUserDetailsService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthEndPoint {

    private final JwtTokenFactory tokenFactory;
    private final AppUserDetailsService appUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthAccessElement createToken(@RequestBody AuthLoginElement ele) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    ele.getUsername(), ele.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(ele.getUsername());
        String token = tokenFactory.doGenerateToken(userDetails);
        String refreshToken = tokenFactory.doGenerateRefreshToken(userDetails);
        return new AuthAccessElement(token, refreshToken);
    }

    @PostMapping("/token")
    public AuthAccessElement refreshToken(@RequestBody AuthLoginElement ele) {
        String jwtToken = ele.getRefreshToken();

        try {
            if (StringUtils.hasText(jwtToken) && tokenFactory.validateToken(jwtToken)) {
                String newToken = tokenFactory.doRefreshToken(jwtToken);
                return new AuthAccessElement(newToken, "");
            }
            throw new BadCredentialsException("Invalid token");

        } catch (Exception e) {

            throw new BadCredentialsException("Expired token");
        }

    }

    @GetMapping("/check")
    public String checkToken() {
        return "checked";
    }

}
