package org.travel_agency.config;

import java.util.function.Consumer;
import org.springframework.web.reactive.function.client.WebClient;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

public class CustomWCRequestHeaders implements Consumer<WebClient.RequestHeadersSpec<?>> {
   
	private Tracer tracer;
	
    public CustomWCRequestHeaders(Tracer tracer) {
    	this.tracer = tracer;
    }
    
	@Override
    public void accept(WebClient.RequestHeadersSpec<?> requestSpec) {
    	Span span = tracer.currentSpan();
        requestSpec.headers(httpHeaders -> {
                httpHeaders.set("X-B3-TraceId", span.context().traceId());
                httpHeaders.set("X-B3-ParentSpanId", span.context().parentId());
                httpHeaders.set("X-B3-SpanId", span.context().spanId());
                httpHeaders.set("X-B3-Sampled", span.context().sampled().toString());
              });

    }
}

