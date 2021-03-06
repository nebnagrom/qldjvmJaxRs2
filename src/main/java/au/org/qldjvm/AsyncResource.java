package au.org.qldjvm;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.server.ChunkedOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/async")
public class AsyncResource {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncResource.class);

    @GET
    public ChunkedOutput<String> getChunkedResponse() {
        final ChunkedOutput<String> output = new ChunkedOutput<String>(String.class);

        new Thread() {
            public void run() {
                try {
                    String chunkBase = "Chunk_";

                    for (int i = 0; i < 10; i++) {
                        LOG.debug("writing a chunk {}\n", i);
                        output.write(chunkBase + i + " ");
                        Thread.sleep(500);
                    }
                } catch (IOException e) {
                    // IOException thrown when writing the
                    // chunks of response: should be handled
                } catch (InterruptedException e) {
                    LOG.debug("got interrupted, will just terminate and no more chunks");
                } finally {
                    try {
                        output.close();
                    } catch (IOException e) {
                        LOG.error("failed to close output in finally not sure what happens now!");
                    }
                }
            }
        }.start();

        return output;
    }

    @GET
    @Path("/data")
    public String getChunks() {

        LOG.debug("triggering chunked sending");
        Client client = ClientBuilder.newClient();
        final Response response = client.target("http://localhost:8091/jaxrs2/rest/async").request().get();
        LOG.debug("async get done");
        final ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>() {
        });
        chunkedInput.setParser(ChunkedInput.createParser(" "));
        String chunk;
        StringBuilder chunkResult = new StringBuilder();
        while ((chunk = chunkedInput.read()) != null) {
            LOG.debug("Next chunk received: " + chunk);
            chunkResult.append(chunk);
            chunkResult.append("\n");
        }

        return chunkResult.toString();
    }
}
