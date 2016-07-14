package de.jheise.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.*;

public class HttpRequestBuilder {

    private String method;
    private String path;
    private HashMap<String, List<String>> queryParams;
    private HashMap<String, List<String>> headers = new HashMap<>();
    private byte[] entity;

    public HttpRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder path(String path) {
        this.path = path;
        return this;
    }

    public HttpRequestBuilder queryParams(String queryParamsString) {

        queryParams = new HashMap<>();
        if(isEmpty(queryParamsString)) {
            return this;
        }
        if(queryParamsString.startsWith("?")) {
            queryParamsString = queryParamsString.substring(1);
        }

        String[] parts = queryParamsString.split("&");
        for(String s : parts) {
            String[] finalParts = s.split("=");
            String key = finalParts[0];
            List<String> list = queryParams.get(key);
            if(list == null) {
                list = new LinkedList<>();
                queryParams.put(key, list);
            }
            String[] values = finalParts[1].split(",");
            Collections.addAll(list, values);
        }

        return this;
    }

    public HttpRequestBuilder header(String headerString) {

        if(isEmpty(headerString)) {
            throw new IllegalArgumentException("the header must be a non-empty String in the form " +
                    "[Header-Name: value1, value2, etc]");
        }

        String[] parts = headerString.split(":");
        String key = trim(lowerCase(parts[0]));
        List<String> list = headers.get(key);
        if(list == null) {
            list = new LinkedList<>();
            headers.put(key, list);
        }
        String[] values = parts[1].split(",");
        for(String value : values) {
            list.add(trim(value));
        }

        return this;
    }

    public ImmutableHttpRequest build() {
        return new ImmutableHttpRequest(RequestMethod.valueOf(upperCase(method)), path, queryParams, headers, entity);
    }

    public HttpRequestBuilder entity(byte[] bytes) {
        this.entity = bytes;
        return this;
    }
}
