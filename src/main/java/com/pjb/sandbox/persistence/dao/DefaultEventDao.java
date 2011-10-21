package com.pjb.sandbox.persistence.dao;

import org.springframework.stereotype.Repository;

import com.pjb.sandbox.persistence.model.Event;

@Repository
public class DefaultEventDao extends AbstractDao<Event, Long> implements EventDao {
}
