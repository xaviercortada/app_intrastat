
package cat.alkaid.projects.intrastat.auth;

import javax.ws.rs.WebApplicationException;
import org.jboss.resteasy.spi.Failure;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import java.lang.reflect.Method;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import java.util.Set;
import java.util.HashSet;
import javax.annotation.security.DenyAll;
import java.lang.annotation.Annotation;
import javax.annotation.security.PermitAll;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.core.Headers;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.AuthService;
import org.jboss.resteasy.core.ServerResponse;
import java.util.logging.Logger;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
public class MyInterceptor implements PreProcessInterceptor
{
    private static final Logger LOGGER;
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED;
    private static final ServerResponse ACCESS_FORBIDDEN;
    private static final ServerResponse SERVER_ERROR;
    @Inject
    AuthService authService;
    @Inject
    @AuthenticatedUser
    Event<AuthenticatedEvent> userAuthenticatedEvent;
    
    static {
        LOGGER = Logger.getLogger(MyInterceptor.class.getName());
        ACCESS_DENIED = new ServerResponse((Object)"Access denied for this resource", 401, new Headers());
        ACCESS_FORBIDDEN = new ServerResponse((Object)"Nobody can access this resource", 403, new Headers());
        SERVER_ERROR = new ServerResponse((Object)"INTERNAL SERVER ERROR", 500, new Headers());
    }
    
    public ServerResponse preProcess(final HttpRequest request, final ResourceMethodInvoker resourceMethod) throws Failure, WebApplicationException {
        final Method method = resourceMethod.getMethod();
        if (method.isAnnotationPresent((Class<? extends Annotation>)PermitAll.class)) {
            return null;
        }
        if (method.isAnnotationPresent((Class<? extends Annotation>)DenyAll.class)) {
            return MyInterceptor.ACCESS_FORBIDDEN;
        }
        final HttpHeaders headers = request.getHttpHeaders();
        final List<String> authorization = (List<String>)headers.getRequestHeader("Authorization");
        final List<String> identification = (List<String>)headers.getRequestHeader("auth-id");
        final List<String> company = (List<String>)headers.getRequestHeader("company-id");
        if (authorization != null && authorization.size() > 0) {
            try {
                final String authToken = authorization.get(0).substring("Bearer".length()).trim();
                final String authId = identification.get(0);
                final HashSet roles = new HashSet();
                if (method.isAnnotationPresent((Class<? extends Annotation>)AdminRole.class)) {
                    roles.add("Admin");
                }
                this.authService.isAuthorized(authId, authToken, (Set)roles);
                Long companyId = null;
                try {
                    final String s = company.get(0);
                    companyId = Long.parseLong(s);
                }
                catch (Exception ex) {}
                this.userAuthenticatedEvent.fire((Object)new AuthenticatedEvent(authId, companyId));
            }
            catch (JoseException e) {
                e.printStackTrace();
                return MyInterceptor.ACCESS_DENIED;
            }
            catch (InvalidJwtException e2) {
                e2.printStackTrace();
                return MyInterceptor.ACCESS_DENIED;
            }
            catch (Exception e3) {
                return MyInterceptor.ACCESS_DENIED;
            }
        }
        if (authorization == null || authorization.isEmpty()) {
            return MyInterceptor.ACCESS_DENIED;
        }
        return null;
    }
}

