package de.jheise.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.upperCase;

public class HttpRequestBuilder {

    private String method;
    private String path;
    private HashMap<String, List<String>> queryParams;
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
        throw new RuntimeException("todo");
    }

    public ImmutableHttpRequest build() {
        return new ImmutableHttpRequest(RequestMethod.valueOf(upperCase(method)), path, queryParams, entity);
    }

    public HttpRequestBuilder entity(byte[] bytes) {
        this.entity = bytes;
        return this;
    }
}
