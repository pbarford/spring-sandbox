package com.pjb.sandbox.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pjb.sandbox.builders.EventBuilder;
import com.pjb.sandbox.builders.UserBuilder;
import com.pjb.sandbox.persistence.dao.EventDao;
import com.pjb.sandbox.persistence.dao.JpqlBuilder;
import com.pjb.sandbox.persistence.dao.UserDao;
import com.pjb.sandbox.persistence.model.Event;
import com.pjb.sandbox.persistence.model.EventDestination;
import com.pjb.sandbox.persistence.model.Market;
import com.pjb.sandbox.persistence.model.MarketDestination;
import com.pjb.sandbox.persistence.model.Selection;
import com.pjb.sandbox.persistence.model.SelectionDestination;

@ContextConfiguration(locations={"classpath:/com/pjb/sandbox/persistence/PersistenceTest-context.xml"})
public class TestNgPersistenceTest extends AbstractTestNGSpringContextTests {

	private static final int DEST_COUNT = 8;
	private static final int SELECTION_COUNT = 20;
	private static final int MARKET_COUNT = 1;

	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private UserDao userDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@BeforeMethod(groups={"query"})
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void init() {
		
		Event e = new Event("event-1");
		e.setEventDestinations(new HashSet<EventDestination>());
		for(int x = 0; x < DEST_COUNT; x++) {
			EventDestination ed = new EventDestination("event-destination-" + x);
			ed.setEvent(e);
			e.getEventDestinations().add(ed);
		}
		
		e.setMarkets(new HashSet<Market>());
		for(int x = 0; x < MARKET_COUNT; x++) {
			Market m = new Market("market-" + x);
			m.setEvent(e);
			m.setMarketDestinations(new HashSet<MarketDestination>());
			for(int y = 0; y < DEST_COUNT; y++) {
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
				for(int z = 0; z < DEST_COUNT; z++) {
					SelectionDestination sd = new SelectionDestination("selection-destination-" + z);
					sd.setSelection(s);
					s.getSelectionDestinations().add(sd);
				}
			}
			e.getMarkets().add(m);
		}
		
		eventDao.persist(e);		
		System.out.println("*********** getEntityInsertCount : " + getStats().getEntityInsertCount());
		
	}
	
	private Statistics getStats() {
		Session session = (Session) entityManager.getDelegate();
		return session.getSessionFactory().getStatistics();
	}
	
	@Test(groups={"query"})
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
	public void testQuery() {
		Event e = eventDao.loadAll(Long.valueOf(10));
		System.out.println("*********** getEntityFetchCount : " + getStats().getEntityFetchCount());
		System.out.println("*********** getQueryCacheMissCount : " + getStats().getQueryCacheMissCount());
		System.out.println("*********** getQueryCacheHitCount : " + getStats().getQueryCacheHitCount());
		System.out.println("*********** getQueryExecutionCount : " + getStats().getQueryExecutionCount());
		for(String q: getStats().getQueries()) {
			QueryStatistics qstats = getStats().getQueryStatistics(q);
			
			System.out.println("*********** " + q + " ***********");
			System.out.println("*********** START ***********");
			System.out.println("*********** getExecutionAvgTime : " + qstats.getExecutionAvgTime() + " m/s");
			System.out.println("*********** getQueryExecutionCount : " + qstats.getExecutionRowCount());
			System.out.println("*********** DONE ***********");
		}
		assertThat(e.getDescription(), equalTo("event-1"));
		assertThat(e.getEventDestinations().size(), equalTo(DEST_COUNT));
		assertThat(e.getMarkets().size(), equalTo(MARKET_COUNT));	
	}
	
	@Test(groups={"user", "builders"})
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
	public void testAddUser() {
		
		userDao.persist(UserBuilder.newUser().withName("Paulo").withAge(38).build());
		userDao.persist(UserBuilder.newUser().withName("Anita").withAge(43).build());
		userDao.persist(UserBuilder.newUser().withName("Alexandra").withAge(6).build());
		
		String sql = JpqlBuilder.create().select("u").from("User u").where("u.name='Paulo'").toString();
		assertThat(userDao.query(sql).size(), equalTo(1));
		
	}
	
	@Test(groups={"builders"})
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
	public void testEventBuilder() {
		
		Event e = EventBuilder.newEvent()
				.withEventDescription("event-1")
					.andEventDestination("e-dest-1")
					.andEventDestination("e-dest-1")
					.andMarket().withMarketDescription("market-1")
						.andMarketDestination("m-dest-1")
						.andMarketDestination("m-dest-2")
						.andSelection()
							.withSelectionDescription("selection-1")
							.andSelectionDestination("s-dest-1")
							.andSelectionDestination("s-dest-2")
					.andAnotherMarket().withMarketDescription("market-2")
						.andMarketDestination("m-dest-2")
						.andMarketDestination("m-dest-2")
						.andMarketDestination("m-dest-3")
						.andMarketDestination("m-dest-3")
						.andSelection()
							.withSelectionDescription("selection-2")
							.andSelectionDestination("s-dest-1")
							.andSelectionDestination("s-dest-2")
						.andAnotherSelection()
							.withSelectionDescription("selection-3")
							.andSelectionDestination("s-dest-5")
							.andSelectionDestination("s-dest-6")
							.andSelectionDestination("s-dest-7")
				.build();
		
		eventDao.persist(e);
		
		assertThat(e.getId(), notNullValue());
	}
}
