package com.example.rabbit;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Spans {

    private Host host;
    private List<Span> spans = Collections.emptyList();

    @SuppressWarnings("unused")
    private Spans() {
    }

    public Spans(Host host, List<Span> spans) {
        this.host = host;
        this.spans = spans;
    }

    public Host getHost() {
        return this.host;
    }

    public List<Span> getSpans() {
        return this.spans;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setSpans(List<Span> spans) {
        this.spans = spans;
    }
}