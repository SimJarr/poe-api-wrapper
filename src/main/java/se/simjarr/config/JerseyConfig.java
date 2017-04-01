package se.simjarr.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import se.simjarr.resource.StashResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(StashResource.class);
    }
}
