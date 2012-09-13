package com.pjb.sandbox.persistence.dao;

import org.springframework.stereotype.Repository;

import com.pjb.sandbox.persistence.model.Event;

@Repository
public class DefaultEventDao extends AbstractDao<Event, Long> implements EventDao {

	private static final String LOAD_ALL_SQL = "SELECT e FROM Event e " +
													"LEFT OUTER JOIN FETCH e.eventDestinations ed " +
													"LEFT OUTER JOIN FETCH e.markets m " +
													"LEFT OUTER JOIN FETCH m.marketDestinations md " +
													"LEFT OUTER JOIN FETCH m.selections s " +
													"LEFT OUTER JOIN FETCH s.selectionDestinations sd " +
													"WHERE e.id = ?1";
	
	public Event loadAll(Long eventId) {
		return querySingle(LOAD_ALL_SQL, eventId);
	}
}
