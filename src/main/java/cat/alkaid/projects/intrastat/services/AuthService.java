
package cat.alkaid.projects.intrastat.services;

import java.security.Key;
import org.jose4j.jws.JsonWebSignature;
import javax.ws.rs.core.SecurityContext;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import java.util.Set;
import java.util.List;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jwk.RsaJsonWebKey;
import javax.inject.Inject;

@Service
public class AuthService
{
    @Autowired
    private AccountService service;
    private static RsaJsonWebKey webKey;
    
    static {
        AuthService.webKey = null;
    }
    
    private RsaJsonWebKey getWebKeyInstance() throws JoseException {
        if (AuthService.webKey == null) {
            (AuthService.webKey = RsaJwkGenerator.generateJwk(2048)).setKeyId("k1");
        }
        return AuthService.webKey;
    }
    
    public AuthAccessElement Login(final AuthLoginElement loginElement) throws JoseException {
        final List<Account> accounts = this.service.findByUsername(loginElement.getUsername());
        if (accounts.size() == 0) {
            return null;
        }
        final Account accountRegistration = accounts.get(0);
        if (accountRegistration != null && accountRegistration.getActivated() != null && accountRegistration.validate(loginElement.getPassword())) {
            final AuthAccessElement accessElement = new AuthAccessElement(loginElement.getUsername(), accountRegistration.getId());
            final String token = this.getToken(accountRegistration);
            accessElement.setAuthToken(token);
            this.service.update(accountRegistration);
            return accessElement;
        }
        return null;
    }
    
    public void isAuthorized(final String authId, final String authToken, final Set<String> role) throws JoseException, InvalidJwtException {
        final JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime().setAllowedClockSkewInSeconds(30).setRequireSubject().setExpectedIssuer(authId).setExpectedAudience(new String[] { "Audience" }).setVerificationKey(this.getWebKeyInstance().getKey()).build();
        final JwtClaims jwtClaims = jwtConsumer.processToClaims(authToken);
        if (role.contains("Admin") && !authId.equals("xavier.cortada")) {
            new Exception("Role not permited");
        }
    }
    
    public Account getAccount(final String authId) {
        final List<Account> accounts = this.service.findByUsername(authId);
        if (accounts.size() == 0) {
            return null;
        }
        final Account accountRegistration = accounts.get(0);
        return accountRegistration;
    }
    
    public SecurityContext getSecurityContext(final String authId, final String authToken, final Set<String> rolesAllowed) {
        final List<Account> accounts = this.service.findByUsername(authId);
        if (accounts.size() == 0) {
            return null;
        }
        final Account accountRegistration = accounts.get(0);
        if (accountRegistration != null) {
            //final SecurityContext securityCtx = (SecurityContext)new AuthService.(this, accountRegistration);
            return null; //securityCtx;
        }
        return null;
    }
    
    private String getToken(final Account account) throws JoseException {
        final JwtClaims claims = new JwtClaims();
        claims.setIssuer(account.getUserName());
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(10000.0f);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setSubject("intrastat");
        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey((Key)this.getWebKeyInstance().getPrivateKey());
        jws.setKeyIdHeaderValue(this.getWebKeyInstance().getKeyId());
        jws.setAlgorithmHeaderValue("RS256");
        final JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime().setAllowedClockSkewInSeconds(30).setRequireSubject().setExpectedIssuer(account.getUserName()).setExpectedAudience(new String[] { "Audience" }).setVerificationKey(this.getWebKeyInstance().getKey()).build();
        final String token = jws.getCompactSerialization();
        try {
            final JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
            System.out.println("JWT validation succeeded! " + jwtClaims);
        }
        catch (InvalidJwtException e) {
            System.out.println("Invalid JWT! " + e);
        }
        return token;
    }
}

