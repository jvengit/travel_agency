package org.travel_agency.config;

import java.util.function.Consumer;
import org.springframework.web.client.RestClient;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

public class CustomRCRequestHeaders implements Consumer<RestClient.RequestHeadersSpec<?>> {
   
	private Tracer tracer;
	
    public CustomRCRequestHeaders(Tracer tracer) {
    	this.tracer = tracer;
    }
    
	@Override
    public void accept(RestClient.RequestHeadersSpec<?> requestSpec) {
    	Span span = tracer.currentSpan();
        requestSpec.headers(httpHeaders -> {
                httpHeaders.set("X-B3-TraceId", span.context().traceId());
                httpHeaders.set("X-B3-ParentSpanId", span.context().parentId());
                httpHeaders.set("X-B3-SpanId", span.context().spanId());
                httpHeaders.set("X-B3-Sampled", span.context().sampled().toString());
              });

    }
}
