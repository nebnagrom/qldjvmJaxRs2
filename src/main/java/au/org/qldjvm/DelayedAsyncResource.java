package au.org.qldjvm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/delayedAsync")
public class DelayedAsyncResource {
    private static final Logger LOG = LoggerFactory.getLogger(DelayedAsyncResource.class);

    private static final ConcurrentHashMap<String, String> responseById = new ConcurrentHashMap<String, String>();

    @GET
    public void getResponse(@Suspended final AsyncResponse ar, @QueryParam("id") final String responseId) {

        LOG.debug("got a request for response id {} ", responseId);

        ar.setTimeout(20, TimeUnit.SECONDS);

        new Thread() {
            public void run() {
                do {

                    String response = responseById.get(responseId);
                    LOG.debug("polling for response with id {} found {}", responseId, response);
                    if (response != null) {
                        LOG.debug("returning response {}", response);
                        ar.resume(response);
                        return;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                } while (true);
            }
        }.start();

        LOG.debug("returning now");
    }

    @PUT
    public String putResponse(@FormParam("id") String responseId, @FormParam("response") String response) {

        responseById.put(responseId, response);
        String newResponse = responseById.get(responseId);
        LOG.debug("puting a response in place for id {} new response is {}", responseId, newResponse);
        return newResponse;
    }
}
