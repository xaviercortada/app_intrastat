package cat.alkaid.projects.intrastat;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import cat.alkaid.projects.intrastat.controllers.PaisEndpoint;
import cat.alkaid.projects.intrastat.controllers.ProveedorEndpoint;


@Configuration
@ApplicationPath("/rest")
public class RestConfig extends ResourceConfig {

    @PostConstruct
    public void init() {
        register(ProveedorEndpoint.class);
        register(PaisEndpoint.class);
    }

}