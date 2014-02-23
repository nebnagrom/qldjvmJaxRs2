package au.org.qldjvm;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("subpath/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getItSubpath(@PathParam("pathParam") String pathParam, @QueryParam("queryParam") String queryParam) {
        return "Subpath Got it! with path param " + pathParam + " query param " + queryParam;
    }
    
    @GET
    @Path("badRequest")
    public Response badGet() {
        ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST.getStatusCode());
        return responseBuilder.entity("Bad Request").build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteIt() {
        return "Deleted it!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String postIt(@FormParam("formParam") String formParam) {
        return "Posted it! with form param " + formParam;
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putIt(@Context UriInfo ui) {
        return "Put it!";
    }
}
