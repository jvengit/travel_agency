package org.airport.config;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.observation.Observation;

public class CustomObservationHandler extends DefaultMeterObservationHandler {

	 private final MeterRegistry meterRegistry;
	
	 public CustomObservationHandler(MeterRegistry meterRegistry) {
		 super(meterRegistry);
		 this.meterRegistry = meterRegistry;
	 }

	 @Override
	 public void onStart(Observation.Context context) {

	            LongTaskTimer.Sample longTaskSample = LongTaskTimer.builder(context.getName() + ".active")
	                .tags(createTags(context))
	                .register(meterRegistry)
	                .start();
	            context.put(LongTaskTimer.Sample.class, longTaskSample);


	        Timer.Sample sample = Timer.start(meterRegistry);
	        context.put(Timer.Sample.class, sample);
		 
		 Counter.builder(context.getName() + "." + "noevent")
         .tags(createTags(context))
         .register(this.meterRegistry)
         .increment();
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
	            tags.add(Tag.of(keyValue.getKey(), keyValue.getValue()));
	        }
	        return tags;
	 }
}