package de.jheise.parser;

import de.jheise.http.HttpRequestBuilder;
import de.jheise.http.HttpResponseBuilder;
import de.jheise.log.Communication;
import de.jheise.log.CommunicationLog;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ApacheHttpClientWireLogParser {

    private ParserState state;
    private CommunicationLog log;

    public CommunicationLog parse(InputStream in) {

        transitionToSeekBeginOfRequest();
        log = new CommunicationLog();

        Scanner scanner = new Scanner(in);
        while(scanner.hasNextLine()) {
            state.process(scanner.nextLine());
        }

        return log;
    }

    private void transitionToSeekBeginOfRequest() {

        state = line -> {
            if(isBeginOfRequest(line)) {
                transitionToReadingRequest(line);
            }
        };
    }

    private boolean isBeginOfRequest(String line) {
        throw new RuntimeException("todo");
    }

    private void transitionToReadingRequest(String firstLine) {

        HttpRequestBuilder builder = new HttpRequestBuilder();
        {
            String[] parts = firstLine.split(" ");
            builder.method(parts[0]);
            builder.path(extractPath(parts[1]));
            builder.queryParams(extractQueryParams(parts[1]));
        }

        transitionToReadingRequestHeaders(builder);
    }

    private String extractPath(String input) {
        throw new RuntimeException("todo");
    }

    private String extractQueryParams(String input) {
        throw new RuntimeException("todo");
    }

    private void transitionToReadingRequestHeaders(HttpRequestBuilder builder) {

        state = line -> {

            if(isHeader(line)) {
                builder.header(line);
            }
            if(isEndOfHeaders(line)) {
                transitionToReadingRequestEntity(builder);
            }
        };
    }

    private boolean isHeader(String line) {
        throw new RuntimeException("todo");
    }

    private boolean isEndOfHeaders(String line) {
        throw new RuntimeException("todo");
    }

    private void transitionToReadingRequestEntity(HttpRequestBuilder builder) {

        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        state = line -> {

            if(isEndOfRequest(line)) {
                builder.entity(bis.toByteArray());
                Communication c = finishUpCurrentRequest(builder);
                transitionToReadingResponse(c);
            }

            byte[] bytes = line.getBytes();
            bis.write(bytes, 0, bytes.length);
        };
    }

    private boolean isEndOfRequest(String line) {
        throw new RuntimeException("todo");
    }

    private Communication finishUpCurrentRequest(HttpRequestBuilder builder) {
        Communication c = new Communication();
        c.setRequest(builder.build());
        log.add(c);
        return c;
    }

    private boolean isBeginningOfResponse(String line) {
        throw new RuntimeException("todo");
    }

    private void transitionToReadingResponse(Communication c) {

        state = line -> {
            if(isBeginningOfResponse(line)) {
                readStatusLine(line, c);
            }
        };
    }

    private void readStatusLine(String line, Communication c) {

        HttpResponseBuilder builder = new HttpResponseBuilder();
        {
            String[] parts = line.split(" ");
            builder.statusCode(parseInt(parts[1]));
            builder.statusText(parts[2]);
        }

        transitionToReadingResponseHeaders(builder, c);
    }

    private void transitionToReadingResponseHeaders(HttpResponseBuilder builder, Communication c) {

        state = line -> {

            if(isHeader(line)) {
                builder.header(line);
            } else if(isEndOfResponseHeaders(line)) {
                transitionToReadingResponseEntity(builder, c);
            }
        };
    }

    private boolean isEndOfResponseHeaders(String line) {
        throw new RuntimeException("todo");
    }

    private void transitionToReadingResponseEntity(HttpResponseBuilder builder, Communication c) {

        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        state = line -> {

            if(isEndOfResponseEntity(line)) {
                builder.entity(bis.toByteArray());
                c.setResponse(builder.build());
            }

            byte[] bytes = line.getBytes();
            bis.write(bytes, 0, bytes.length);
        };
    }

    private boolean isEndOfResponseEntity(String line) {
        throw new RuntimeException("todo");
    }
}
