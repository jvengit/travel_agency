package org.travel_agency.config;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.observation.Observation;

public class CustomObserverHandler extends DefaultMeterObservationHandler {

	 private final MeterRegistry meterRegistry;
	
	 public CustomObserverHandler(MeterRegistry meterRegistry) {
		 super(meterRegistry);
		 this.meterRegistry = meterRegistry;
	 }

	 @Override
	 public void onEvent(Observation.Event event, Observation.Context context) {
	     Counter.builder(context.getName() + "=====" + event.getName())
	         .tags(createTags(context))
	         .register(this.meterRegistry)
	         .increment(12d);
	     
	     Counter.builder(context.getName() + "." + event.getName())
	         .tags(createTags(context))
	         .register(this.meterRegistry)
	         .increment();
	 }
	 
	 private List<Tag> createTags(Observation.Context context) {
	        List<Tag> tags = new ArrayList<>();
	        for (KeyValue keyValue : context.getLowCardinalityKeyValues()) {
	        	System.out.println("*********************TAGS"+keyValue.getKey()+" - "+keyValue.getValue());
	            tags.add(Tag.of(keyValue.getKey(), keyValue.getValue()));
	        }
	        return tags;
	 }
}
