package de.jheise.http;

import java.util.HashMap;
import java.util.List;

public class ImmutableHttpRequest {

    private final RequestMethod method;
    private final String path;
    private final byte[] entity;
    private final QueryParams queryParams;

    public ImmutableHttpRequest(RequestMethod method, String path, HashMap<String, List<String>> queryParams,
            byte[] entity) {

        if(method == null) {
            throw new IllegalArgumentException("method must not be null");
        }
        if(path == null || path.isEmpty() || !path.startsWith("/")) {
            throw new IllegalArgumentException("path must be a non-empty string and begin with /");
        }
        if(method == RequestMethod.GET && entity != null) {
            throw new IllegalStateException("GET request may not have a request entity");
        }

        this.method = method;
        this.path = path;
        this.entity = entity;

        this.queryParams = new QueryParams(queryParams);
    }

    public String getPath() {
        return path;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public Headers getHeaders() {
        throw new RuntimeException("todo");
    }

    public RequestMethod getMethod() {
        return method;
    }
}
