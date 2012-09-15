package com.pjb.sandbox.builders;

import java.util.HashSet;

import com.pjb.sandbox.persistence.model.Event;
import com.pjb.sandbox.persistence.model.EventDestination;
import com.pjb.sandbox.persistence.model.Market;
import com.pjb.sandbox.persistence.model.MarketDestination;
import com.pjb.sandbox.persistence.model.Selection;
import com.pjb.sandbox.persistence.model.SelectionDestination;

public class EventBuilder {

	private EventBuilder() {		
	}
	
	public static DescriptionStep newEvent() {
		return new EventSteps();
	}
	
	public static interface DescriptionStep {
		EventDestinationStep withEventDescription(String name);
	}
	
	public static interface EventDestinationStep {
		EventDestinationStep andEventDestination(String desc);
		MarketStep andMarket();
		
	}
	
	public static interface MarketStep {
		MarketDestinationStep withMarketDescription(String desc);
	}
	
	public static interface MarketDestinationStep {		
		MarketDestinationStep andMarketDestination(String desc);
		SelectionStep andSelection();
	}
	
	public static interface SelectionStep {
		SelectionDestinationStep withSelectionDescription(String desc);
		
	}
	
	public static interface SelectionDestinationStep {
		SelectionDestinationStep andSelectionDestination(String desc);
		MarketStep andAnotherMarket();
		Event build();
		
	}
	
	private static class EventSteps implements DescriptionStep, EventDestinationStep, MarketStep, MarketDestinationStep, SelectionStep, SelectionDestinationStep {
		
		private Event event;
		private Market currentMarket;
		private Selection currentSelection;
		
		public EventDestinationStep withEventDescription(String desc) {
			event = new Event();
			event.setDescription(desc);
			event.setEventDestinations(new HashSet<EventDestination>());
			event.setMarkets(new HashSet<Market>());
			return this;
		}
		
		public EventDestinationStep andEventDestination(String desc) {
			return this;
		}

		public MarketStep andMarket() {
			currentMarket = new Market();
			currentMarket.setEvent(event);
			currentMarket.setMarketDestinations(new HashSet<MarketDestination>());
			currentMarket.setSelections(new HashSet<Selection>());
			event.getMarkets().add(currentMarket);
			return this;
		}

		public MarketDestinationStep withMarketDescription(String desc) {
			currentMarket.setDescription(desc);
			return this;
		}

		public MarketDestinationStep andMarketDestination(String desc) {
			MarketDestination dest = new MarketDestination();
			dest.setMarket(currentMarket);
			dest.setDescription(desc);
			currentMarket.getMarketDestinations().add(dest);
			return this;
		}

		public SelectionStep andSelection() {
			currentSelection = new Selection();
			currentSelection.setMarket(currentMarket);
			currentSelection.setSelectionDestinations(new HashSet<SelectionDestination>());
			return this;
		}
		
		public SelectionDestinationStep withSelectionDescription(String desc) {
			currentSelection.setDescription(desc);
			return this;
		}
		
		public SelectionDestinationStep andSelectionDestination(String desc) {
			SelectionDestination dest = new SelectionDestination();
			dest.setSelection(currentSelection);
			dest.setDescription(desc);
			currentSelection.getSelectionDestinations().add(dest);
			return this;
		}
		
		public MarketStep andAnotherMarket() {
			return andMarket();
		}
		
		public Event build() {
			return event;
		}
			
	}
}
