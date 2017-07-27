package com.example.zipkin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Span {

    public static final String SAMPLED_NAME = "X-B3-Sampled";
    public static final String PROCESS_ID_NAME = "X-Process-Id";
    public static final String PARENT_ID_NAME = "X-B3-ParentSpanId";
    public static final String TRACE_ID_NAME = "X-B3-TraceId";
    public static final String SPAN_NAME_NAME = "X-Span-Name";
    public static final String SPAN_ID_NAME = "X-B3-SpanId";
    public static final String SPAN_EXPORT_NAME = "X-Span-Export";
    public static final Set<String> SPAN_HEADERS = new HashSet<>(Arrays.asList(SAMPLED_NAME, PROCESS_ID_NAME, PARENT_ID_NAME, TRACE_ID_NAME, SPAN_ID_NAME, SPAN_NAME_NAME, SPAN_EXPORT_NAME));
    public static final String SPAN_SAMPLED = "1";
    public static final String SPAN_NOT_SAMPLED = "0";
    public static final String SPAN_LOCAL_COMPONENT_TAG_NAME = "lc";
    public static final String SPAN_ERROR_TAG_NAME = "error";
    public static final String CLIENT_RECV = "cr";
    public static final String CLIENT_SEND = "cs";
    public static final String SERVER_RECV = "sr";
    public static final String SERVER_SEND = "ss";
    public static final String SPAN_PEER_SERVICE_TAG_NAME = "peer.service";
    public static final String INSTANCEID = "spring.instance_id";

    private final long begin;
    private long end = 0;
    private final String name;
    private final long traceIdHigh;
    private final long traceId;
    private List<Long> parents = new ArrayList<>();
    private final long spanId;
    private boolean remote = false;
    private boolean exportable = true;
    private final Map<String, String> tags;
    private final String processId;
    private final Collection<Log> logs;
    private final Span savedSpan;

    @JsonIgnore
    private final Long startNanos;
    private Long durationMicros;

    @SuppressWarnings("unused")
    private Span() {
        this(-1, -1, "dummy", 0, Collections.<Long>emptyList(), 0, false, false, null);
    }

    public Span(Span current, Span savedSpan) {
        this.begin = current.getBegin();
        this.end = current.getEnd();
        this.name = current.getName();
        this.traceIdHigh = current.getTraceIdHigh();
        this.traceId = current.getTraceId();
        this.parents = current.getParents();
        this.spanId = current.getSpanId();
        this.remote = current.isRemote();
        this.exportable = current.isExportable();
        this.processId = current.getProcessId();
        this.tags = current.tags;
        this.logs = current.logs;
        this.startNanos = current.startNanos;
        this.durationMicros = current.durationMicros;
        this.savedSpan = savedSpan;
    }

    @Deprecated
    public Span(long begin, long end, String name, long traceId, List<Long> parents, long spanId, boolean remote, boolean exportable, String processId) {
        this(begin, end, name, traceId, parents, spanId, remote, exportable, processId, null);
    }

    @Deprecated
    public Span(long begin, long end, String name, long traceId, List<Long> parents, long spanId, boolean remote, boolean exportable, String processId, Span savedSpan) {
        this(new SpanBuilder().begin(begin).end(end).name(name).traceId(traceId).parents(parents).spanId(spanId).remote(remote).exportable(exportable).processId(processId).savedSpan(savedSpan));
    }

    Span(SpanBuilder builder) {
        if (builder.begin > 0) {
            this.startNanos = null;
            this.begin = builder.begin;
        } else {
            this.startNanos = nanoTime();
            this.begin = System.currentTimeMillis();
        }
        if (builder.end > 0) {
            this.end = builder.end;
            this.durationMicros = (this.end - this.begin) * 1000;
        }
        this.name = builder.name != null ? builder.name : "";
        this.traceIdHigh = builder.traceIdHigh;
        this.traceId = builder.traceId;
        this.parents.addAll(builder.parents);
        this.spanId = builder.spanId;
        this.remote = builder.remote;
        this.exportable = builder.exportable;
        this.processId = builder.processId;
        this.savedSpan = builder.savedSpan;
        this.tags = new ConcurrentHashMap<>();
        this.tags.putAll(builder.tags);
        this.logs = new ConcurrentLinkedQueue<>();
        this.logs.addAll(builder.logs);
    }

    public static SpanBuilder builder() {
        return new SpanBuilder();
    }

    public synchronized void stop() {
        if (this.durationMicros == null) {
            if (this.begin == 0) {
                throw new IllegalStateException("Span for " + this.name + " has not been started");
            }
            if (this.end == 0) {
                this.end = System.currentTimeMillis();
            }
            if (this.startNanos != null) {
                this.durationMicros = Math.max(1, (nanoTime() - this.startNanos) / 1000);
            } else {
                this.durationMicros = (this.end - this.begin) * 1000;
            }
        }
    }

    @Deprecated
    @JsonIgnore
    public synchronized long getAccumulatedMillis() {
        return getAccumulatedMicros() / 1000;
    }

    @JsonIgnore
    public synchronized long getAccumulatedMicros() {
        if (this.durationMicros != null) {
            return this.durationMicros;
        } else {
            if (this.begin == 0) {
                return 0;
            }
            if (this.startNanos != null) {
                return Math.max(1, (nanoTime() - this.startNanos) / 1000);
            } else {
                return (System.currentTimeMillis() - this.begin) * 1000;
            }
        }
    }

    @JsonIgnore
    long nanoTime() {
        return System.nanoTime();
    }

    @JsonIgnore
    public synchronized boolean isRunning() {
        return this.begin != 0 && this.durationMicros == null;
    }

    public void tag(String key, String value) {
        if (StringUtils.hasText(value)) {
            this.tags.put(key, value);
        }
    }

    public void logEvent(String event) {
        this.logs.add(new Log(System.currentTimeMillis(), event));
    }

    public Map<String, String> tags() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(this.tags));
    }

    public List<Log> logs() {
        return Collections.unmodifiableList(new ArrayList<>(this.logs));
    }

    @JsonIgnore
    public Span getSavedSpan() {
        return this.savedSpan;
    }

    public boolean hasSavedSpan() {
        return this.savedSpan != null;
    }

    public String getName() {
        return this.name;
    }

    public long getSpanId() {
        return this.spanId;
    }

    public long getTraceIdHigh() {
        return this.traceIdHigh;
    }

    public long getTraceId() {
        return this.traceId;
    }

    public String getProcessId() {
        return this.processId;
    }

    public List<Long> getParents() {
        return this.parents;
    }

    public boolean isRemote() {
        return this.remote;
    }

    public long getBegin() {
        return this.begin;
    }

    public long getEnd() {
        return this.end;
    }

    public boolean isExportable() {
        return this.exportable;
    }

    public String traceIdString() {
        if (this.traceIdHigh != 0) {
            char[] result = new char[32];
            writeHexLong(result, 0, this.traceIdHigh);
            writeHexLong(result, 16, this.traceId);
            return new String(result);
        }
        char[] result = new char[16];
        writeHexLong(result, 0, this.traceId);
        return new String(result);
    }

    public static String idToHex(long id) {
        char[] data = new char[16];
        writeHexLong(data, 0, id);
        return new String(data);
    }

    static void writeHexLong(char[] data, int pos, long v) {
        writeHexByte(data, pos + 0, (byte) ((v >>> 56L) & 0xff));
        writeHexByte(data, pos + 2, (byte) ((v >>> 48L) & 0xff));
        writeHexByte(data, pos + 4, (byte) ((v >>> 40L) & 0xff));
        writeHexByte(data, pos + 6, (byte) ((v >>> 32L) & 0xff));
        writeHexByte(data, pos + 8, (byte) ((v >>> 24L) & 0xff));
        writeHexByte(data, pos + 10, (byte) ((v >>> 16L) & 0xff));
        writeHexByte(data, pos + 12, (byte) ((v >>> 8L) & 0xff));
        writeHexByte(data, pos + 14, (byte) (v & 0xff));
    }

    static void writeHexByte(char[] data, int pos, byte b) {
        data[pos + 0] = HEX_DIGITS[(b >> 4) & 0xf];
        data[pos + 1] = HEX_DIGITS[b & 0xf];
    }

    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static long hexToId(String hexString) {
        Assert.hasText(hexString, "Can't convert empty hex string to long");
        int length = hexString.length();
        if (length < 1 || length > 32) throw new IllegalArgumentException("Malformed id: " + hexString);

        int beginIndex = length > 16 ? length - 16 : 0;

        return hexToId(hexString, beginIndex);
    }

    public static long hexToId(String lowerHex, int index) {
        Assert.hasText(lowerHex, "Can't convert empty hex string to long");
        long result = 0;
        for (int endIndex = Math.min(index + 16, lowerHex.length()); index < endIndex; index++) {
            char c = lowerHex.charAt(index);
            result <<= 4;
            if (c >= '0' && c <= '9') {
                result |= c - '0';
            } else if (c >= 'a' && c <= 'f') {
                result |= c - 'a' + 10;
            } else {
                throw new IllegalArgumentException("Malformed id: " + lowerHex);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "[Trace: " + traceIdString() + ", Span: " + idToHex(this.spanId) + ", Parent: " + getParentIdIfPresent() + ", exportable:" + this.exportable + "]";
    }

    private String getParentIdIfPresent() {
        return this.getParents().isEmpty() ? "null" : idToHex(this.getParents().get(0));
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (this.traceIdHigh >>> 32) ^ this.traceIdHigh;
        h *= 1000003;
        h ^= (this.traceId >>> 32) ^ this.traceId;
        h *= 1000003;
        h ^= (this.spanId >>> 32) ^ this.spanId;
        h *= 1000003;
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Span) {
            Span that = (Span) o;
            return (this.traceIdHigh == that.traceIdHigh) && (this.traceId == that.traceId) && (this.spanId == that.spanId);
        }
        return false;
    }

    public static class SpanBuilder {
        private long begin;
        private long end;
        private String name;
        private long traceIdHigh;
        private long traceId;
        private ArrayList<Long> parents = new ArrayList<>();
        private long spanId;
        private boolean remote;
        private boolean exportable = true;
        private String processId;
        private Span savedSpan;
        private List<Log> logs = new ArrayList<>();
        private Map<String, String> tags = new LinkedHashMap<>();

        SpanBuilder() {
        }

        public Span.SpanBuilder begin(long begin) {
            this.begin = begin;
            return this;
        }

        public Span.SpanBuilder end(long end) {
            this.end = end;
            return this;
        }

        public Span.SpanBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Span.SpanBuilder traceIdHigh(long traceIdHigh) {
            this.traceIdHigh = traceIdHigh;
            return this;
        }

        public Span.SpanBuilder traceId(long traceId) {
            this.traceId = traceId;
            return this;
        }

        public Span.SpanBuilder parent(Long parent) {
            this.parents.add(parent);
            return this;
        }

        public Span.SpanBuilder parents(Collection<Long> parents) {
            this.parents.addAll(parents);
            return this;
        }

        public Span.SpanBuilder log(Log log) {
            this.logs.add(log);
            return this;
        }

        public Span.SpanBuilder logs(Collection<Log> logs) {
            this.logs.addAll(logs);
            return this;
        }

        public Span.SpanBuilder tag(String tagKey, String tagValue) {
            this.tags.put(tagKey, tagValue);
            return this;
        }

        public Span.SpanBuilder tags(Map<String, String> tags) {
            this.tags.putAll(tags);
            return this;
        }

        public Span.SpanBuilder spanId(long spanId) {
            this.spanId = spanId;
            return this;
        }

        public Span.SpanBuilder remote(boolean remote) {
            this.remote = remote;
            return this;
        }

        public Span.SpanBuilder exportable(boolean exportable) {
            this.exportable = exportable;
            return this;
        }

        public Span.SpanBuilder processId(String processId) {
            this.processId = processId;
            return this;
        }

        public Span.SpanBuilder savedSpan(Span savedSpan) {
            this.savedSpan = savedSpan;
            return this;
        }

        public Span build() {
            return new Span(this);
        }

        @Override
        public String toString() {
            return new Span(this).toString();
        }
    }
}