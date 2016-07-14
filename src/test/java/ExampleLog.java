import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.IOException;

public class ExampleLog {

    @Test
    public void produceSomeLogRequests() throws Exception {
        Request.Get("http://www.google.de?q=test").execute();
    }
}
