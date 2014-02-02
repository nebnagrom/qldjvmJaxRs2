package au.org.qldjvm;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxRSApplication extends ResourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(JaxRSApplication.class);

    public JaxRSApplication() {
        LOG.debug("initialising my jax rs app");
        register(AsyncResource.class);
        register(MyResource.class);
    }
}
