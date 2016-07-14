package de.jheise.log;

import de.jheise.http.ImmutableHttpRequest;
import de.jheise.http.ImmutableHttpResponse;

public class Communication {


    private ImmutableHttpRequest request;
    private ImmutableHttpResponse response;

    public ImmutableHttpRequest getRequest() {
        throw new RuntimeException("todo");
    }

    public void setRequest(ImmutableHttpRequest request) {
        this.request = request;
    }

    public void setResponse(ImmutableHttpResponse response) {
        this.response = response;
    }
}
