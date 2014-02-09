package au.org.qldjvm;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.container.AsyncResponse;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class DelayedAsyncResourceTest {

    private DelayedAsyncResource resource;

    private IMocksControl mocksControl;

    @Before
    public void setup() {
        resource = new DelayedAsyncResource();

        mocksControl = EasyMock.createControl();
    }

    @Test
    public void testPutTriggersResume() throws InterruptedException {

        String responseId = "a";
        String response = "foo";
        AsyncResponse ar = mocksControl.createMock(AsyncResponse.class);

        EasyMock.expect(ar.setTimeout(20, TimeUnit.SECONDS)).andReturn(true);
        EasyMock.expect(ar.resume(response)).andReturn(true);

        mocksControl.replay();

        resource.getResponse(ar, responseId);
        resource.putResponse(responseId, response);
        Thread.sleep(400);

        mocksControl.verify();
    }
}
