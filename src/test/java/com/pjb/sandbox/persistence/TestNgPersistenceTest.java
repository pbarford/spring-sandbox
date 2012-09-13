package com.pjb.sandbox.persistence;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pjb.sandbox.persistence.dao.EventDao;
import com.pjb.sandbox.persistence.model.Event;
import com.pjb.sandbox.persistence.model.Market;
import com.pjb.sandbox.persistence.model.MarketDestination;
import com.pjb.sandbox.persistence.model.Selection;
import com.pjb.sandbox.persistence.model.SelectionDestination;

@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/persistence/PersistenceTest-context.xml"})
public class TestNgPersistenceTest extends AbstractTestNGSpringContextTests {

	private static final int SELECTION_DEST_COUNT = 8;
	private static final int SELECTION_COUNT = 20;
	private static final int MARKET_COUNT = 200;
	private static final int MARKET_DEST_COUNT = 8;
	@Autowired
	private EventDao eventDao;
	
	@BeforeMethod
	public void init() {
		Event e = new Event("event-1");
		e.setMarkets(new HashSet<Market>());
		for(int x = 0; x < MARKET_COUNT; x++) {
			Market m = new Market("market-" + x);
			m.setEvent(e);
			m.setMarketDestinations(new HashSet<MarketDestination>());
			for(int y = 0; y < MARKET_DEST_COUNT; y++) {
				MarketDestination md = new MarketDestination("market-destination-" + y);
				md.setMarket(m);
				m.getMarketDestinations().add(md);
			}
			m.setSelections(new HashSet<Selection>());
			for(int y = 0; y < SELECTION_COUNT; y++) {
				Selection s = new Selection("selection-" + y);
				s.setMarket(m);
				m.getSelections().add(s);
				s.setSelectionDestinations(new HashSet<SelectionDestination>());
				for(int z = 0; z < SELECTION_DEST_COUNT; z++) {
					SelectionDestination sd = new SelectionDestination("selection-destination-" + z);
					sd.setSelection(s);
					s.getSelectionDestinations().add(sd);
				}
			}
			e.getMarkets().add(m);
		}
		eventDao.persist(e);
	}
	
	@Test
	public void testQuery() {
		Event e = eventDao.loadAll(Long.valueOf(10));
		assertThat(e.getDescription(), equalTo("event-1"));
		assertThat(e.getMarkets().size(), equalTo(MARKET_COUNT));
		
		
	}
}
