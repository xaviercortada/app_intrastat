package cat.alkaid.projects.intrastat.rest;

import org.jose4j.lang.JoseException;

import cat.alkaid.projects.intrastat.service.AuthService;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.annotation.Priority;

import javax.ws.rs.Priorities;

import java.io.IOException;

/**
 * Created by xavier on 26/07/15.
 */

@Provider
@Secured
@Priority(Priorities.AUTHORIZATION)
public class AuthSecurityInterceptor implements ContainerRequestFilter {
    // 401 - Access denied
    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @EJB
    AuthService authService;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        //String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        String authToken = authHeader.substring("Bearer".length()).trim();


        try {
            authService.isAuthorized(authId, authToken, null);
        } catch (JoseException e) {
            e.printStackTrace();
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
        }

        return;

    }
}
