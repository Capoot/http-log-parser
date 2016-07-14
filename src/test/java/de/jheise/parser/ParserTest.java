package de.jheise.parser;

import de.jheise.log.Communication;
import de.jheise.log.CommunicationLog;
import de.jheise.http.RequestMethod;
import org.junit.Test;

import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ParserTest {

    @Test
    public void test() throws Exception {

        InputStream in = ParserTest.class.getResourceAsStream("/example.log");
        ApacheHttpClientWireLogParser parser = new ApacheHttpClientWireLogParser();
        CommunicationLog log = parser.parse(in);

        assertEquals(1, log.size());
        Communication c = log.iterator().next();
        assertEquals("/", c.getRequest().getPath());
        assertEquals(1, c.getRequest().getQueryParams().size());
        assertEquals(4, c.getRequest().getHeaders().size());
        assertEquals("www.google.de", c.getRequest().getHeaders().getValues("Host").get(0));
        assertTrue(c.getRequest().getHeaders().isMultiValued("Host"));
        assertEquals(RequestMethod.GET, c.getRequest().getMethod());
    }
}
