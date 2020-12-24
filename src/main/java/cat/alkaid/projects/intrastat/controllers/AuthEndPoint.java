package cat.alkaid.projects.intrastat.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import cat.alkaid.projects.intrastat.auth.JwtTokenFactory;
import cat.alkaid.projects.intrastat.security.AppUserDetailsService;
import io.jsonwebtoken.impl.DefaultClaims;
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

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s = encoder.encode(ele.getPassword());


        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    ele.getUsername(), ele.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(ele.getUsername());
        String token = tokenFactory.doGenerateToken(userDetails);
        return new AuthAccessElement(token);
    }

    @PostMapping("/token")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = tokenFactory.getMapFromIoJsonwebtokenClaims(claims);
        return tokenFactory.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    }

}
