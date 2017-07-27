package com.example.zipkin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import zipkin.Annotation;
import zipkin.BinaryAnnotation;
import zipkin.Codec;
import zipkin.Span;
import zipkin.reporter.Callback;
import zipkin.reporter.Encoder;
import zipkin.reporter.Encoding;
import zipkin.reporter.Sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RabbitSender implements Sender {

    static final int MB = 1024 * 1024;

    Encoder<Span> encoder = Encoder.JSON;
    Codec codec = Codec.JSON;
    ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = Logger.getLogger(this.getClass());

    private RabbitTemplate rabbitTemplate;

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Encoding encoding() {
        return encoder.encoding();
    }

    @Override
    public int messageMaxBytes() {
        return 5 * MB;
    }

    @Override
    public int messageSizeInBytes(List<byte[]> encodedSpans) {

        return encoding().listSizeInBytes(encodedSpans);
    }

    @Override
    public void sendSpans(List<byte[]> encodedSpans, Callback callback) {
        try {
            List<Span> spans = decodeSpan(encodedSpans);
            Spans sleuthSpans = toSleuthSpans(spans);
            rabbitTemplate.convertAndSend(objectMapper.writeValueAsString(sleuthSpans));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CheckResult check() {
        logger.info("rabbit sender check");
        return null;
    }

    @Override
    public void close() throws IOException {
        logger.info("rabbit sender close");
    }

    private List<Span> decodeSpan(List<byte[]> encodedSpans) {
        List<Span> spans = new ArrayList<>();
        for (byte[] encodedSpan : encodedSpans) {
            spans.add(codec.readSpan(encodedSpan));
        }
        return spans;
    }

    private Spans toSleuthSpans(List<Span> spans) {
        Host host = new Host(null, "localhost", 8080);
        List<com.example.zipkin.Span> spans2 = new ArrayList<>();
        com.example.zipkin.Span span2 = null;
        for (Span span : spans) {

            com.example.zipkin.Span.SpanBuilder spanBuilder = com.example.zipkin.Span.builder()
                    .begin(span.timestamp != null ? span.timestamp / 1000 : 0)
                    .end(span.timestamp != null && span.duration != null ? (span.timestamp + span.duration) / 1000 : 0)
                    .name(span.name)
                    .traceId(span.traceId)
                    .traceIdHigh(span.traceIdHigh)
                    .parent(span.parentId)
                    .spanId(span.id)
                    .remote(true)
                    .exportable(true)
                    .processId(null);
            for (Annotation annotation : span.annotations) {
                if (host.getServiceName() == null && annotation.endpoint.serviceName != null) {
                    host.setServiceName(annotation.endpoint.serviceName);
                }
                spanBuilder.log(new com.example.zipkin.Log(annotation.timestamp / 1000, annotation.value));
            }
            for (BinaryAnnotation binaryAnnotation : span.binaryAnnotations) {
                if ("ca".equals(binaryAnnotation.key))
                    continue;
                try {
                    spanBuilder.tag(binaryAnnotation.key, new String(binaryAnnotation.value, "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            spans2.add(spanBuilder.build());
        }
        return new Spans(host, spans2);
    }
}
