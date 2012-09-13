package com.pjb.sandbox.persistence.dao;

import com.pjb.sandbox.persistence.model.Event;

public interface EventDao extends Dao<Event, Long> {

	Event loadAll(Long eventId);
}
