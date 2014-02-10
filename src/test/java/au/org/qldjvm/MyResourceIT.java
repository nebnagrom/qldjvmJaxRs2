package au.org.qldjvm;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class MyResourceIT {

    private static final String targetLocation = "http://localhost:8091/jaxrs2/rest/myresource/";

    @Test
    public void testGetMyResource() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetLocation);
        Builder requestBuilder = target.request();
        String response = requestBuilder.get(String.class);

        assertEquals("Got it!", response);
    }
}
