package au.org.qldjvm;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("myResourceClient")
public class MyResourceClient {

    static final String targetLocation = "http://localhost:8091/jaxrs2/rest/myresource/";

    private static final Logger LOG = LoggerFactory.getLogger(MyResourceClient.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        Client client = ClientBuilder.newClient();
        client.register(LoggingFilter.class);
        WebTarget target = client.target(targetLocation);
        target.register(LoggingFilter.class);
        Builder requestBuilder = target.request();
        Response response = requestBuilder.accept(MediaType.TEXT_PLAIN).header("someHeader", "someValue").get();

        LOG.debug("passed on get returned a status of {}", response.getStatus());
        return response.readEntity(String.class);
    }

    @GET
    @Path("subpath/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getItSubpath(@PathParam("pathParam") String pathParam, @QueryParam("queryParam") String queryParam) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetLocation).path("subpath").path(pathParam)
                .queryParam("queryParam", queryParam);
        Builder requestBuilder = target.request();
        String response = requestBuilder.get(String.class);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String postIt(@FormParam("formParam") String formParam) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetLocation);
        Builder requestBuilder = target.request();

        Form form = new Form();
        form.param("formParam", formParam);

        Response response = requestBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        LOG.debug("passed on post returned a status of {}", response.getStatus());
        return response.readEntity(String.class);

    }
}