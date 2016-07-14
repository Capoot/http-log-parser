package de.jheise.http;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HttpRequestBuilderTest {

    @Test
    public void queryParamsShouldBeBuiltCorrectlyFromString() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .queryParams("?a=1&b=2&c=3")
                .build();

        assertEquals(3, request.getQueryParams().size());
        assertExactlyOneValue(request, "a", "1");
        assertExactlyOneValue(request, "b", "2");
        assertExactlyOneValue(request, "c", "3");
    }

    private void assertExactlyOneValue(ImmutableHttpRequest request, String key, String value) {
        assertEquals(1, request.getQueryParams().get(key).size());
        assertEquals(value, request.getQueryParams().get(key).get(0));
    }

    @Test
    public void multiValuedQueryParamVar1() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .queryParams("?a=1,2")
                .build();

        assertEquals(1, request.getQueryParams().size());
        assertEquals(2, request.getQueryParams().get("a").size());
        assertEquals("1", request.getQueryParams().get("a").get(0));
        assertEquals("2", request.getQueryParams().get("a").get(1));
    }

    @Test
    public void multiValuedQueryParamVar2() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .queryParams("?a=1&a=2")
                .build();

        assertEquals(1, request.getQueryParams().size());
        assertEquals(2, request.getQueryParams().get("a").size());
        assertEquals("1", request.getQueryParams().get("a").get(0));
        assertEquals("2", request.getQueryParams().get("a").get(1));
    }
}
