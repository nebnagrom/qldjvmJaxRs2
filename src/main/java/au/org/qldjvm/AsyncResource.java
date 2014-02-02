package au.org.qldjvm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ChunkedOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/async")
public class AsyncResource {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncResource.class);
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        LOG.debug("basic get");
        return "Got it!";
    }
    
    @GET
    @Path("/data")
    public ChunkedOutput<String> sendChunks(){
        
        LOG.debug("triggering chunked sendingS");
        
        return null;
    }
    
    @POST
    public void startAsync() {
        
        LOG.debug("chunked posting received");
    }
}
