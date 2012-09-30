package com.pjb.sandbox.persistence.dao;

import org.springframework.stereotype.Repository;

import com.pjb.sandbox.persistence.model.Event;

@Repository
public class DefaultEventDao extends AbstractDao<Event, Long> implements EventDao {
	
	public Event loadAll(Long eventId) {
		String sql = JpqlBuilder.create().select("e")
										.from("Event e")
											.leftOuterJoin("e.eventDestinations ed", true)
											.leftOuterJoin("e.markets m", true)
											.leftOuterJoin("m.marketDestinations md", true)
											.leftOuterJoin("m.selections s", true)
											.leftOuterJoin("s.selectionDestinations sd", true)
										.where("e.id = ?1")
										.orderBy("e.description ASC")
						.toString();
		
		return querySingle(sql, eventId);
	}
}
