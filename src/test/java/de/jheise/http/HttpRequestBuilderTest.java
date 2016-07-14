package de.jheise.http;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertEquals(1, request.getQueryParams().getValues(key).size());
        assertEquals(value, request.getQueryParams().getValues(key).get(0));
        assertFalse(request.getQueryParams().isMultiValued(key));
    }

    @Test
    public void multiValuedQueryParamVar1() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .queryParams("?a=1,2")
                .build();

        assertEquals(1, request.getQueryParams().size());
        assertEquals(2, request.getQueryParams().getValues("a").size());
        assertEquals("1", request.getQueryParams().getValues("a").get(0));
        assertEquals("2", request.getQueryParams().getValues("a").get(1));
        assertTrue(request.getQueryParams().isMultiValued("a"));
    }

    @Test
    public void multiValuedQueryParamVar2() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .queryParams("?a=1&a=2")
                .build();

        assertEquals(1, request.getQueryParams().size());
        assertEquals(2, request.getQueryParams().getValues("a").size());
        assertEquals("1", request.getQueryParams().getValues("a").get(0));
        assertEquals("2", request.getQueryParams().getValues("a").get(1));
        assertTrue(request.getQueryParams().isMultiValued("a"));
    }

    @Test
    public void singleValuedHeaderShouldBeAddedCorrectly() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .header("My-Header: my-value")
                .build();

        assertEquals(1, request.getHeaders().size());
        assertEquals("my-value", request.getHeaders().getValues("my-header").get(0));
        assertFalse(request.getHeaders().isMultiValued("my-header"));
    }

    @Test
    public void multiValuedHeaderVariant1ShouldBeAddedCorrectly() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .header("My-Header: my-value1, my-value2")
                .build();

        assertEquals(1, request.getHeaders().size());
        assertEquals(2, request.getHeaders().getValues("my-header").size());
        assertTrue(request.getHeaders().isMultiValued("my-header"));
        assertEquals("my-value1", request.getHeaders().getValues("my-header").get(0));
        assertEquals("my-value2", request.getHeaders().getValues("my-header").get(1));
    }

    @Test
    public void multiValuedHeaderVariant2ShouldBeAddedCorrectly() {

        ImmutableHttpRequest request = new HttpRequestBuilder()
                .method("GET")
                .path("/")
                .header("My-Header: my-value1")
                .header("My-Header: my-value2")
                .build();

        assertEquals(1, request.getHeaders().size());
        assertEquals(2, request.getHeaders().getValues("my-header").size());
        assertTrue(request.getHeaders().isMultiValued("my-header"));
        assertEquals("my-value1", request.getHeaders().getValues("my-header").get(0));
        assertEquals("my-value2", request.getHeaders().getValues("my-header").get(1));
    }
}
