package com.pjb.sandbox.persistence.dao;

public class JpqlBuilder {

	private JpqlBuilder() {		
	}
	
	public static SelectFields create() {
		return new sqlsteps();
	}
	
	public static interface SelectFields {
		FromTables select(String fields);
	}
	
	public static interface FromTables {
		Joins from(String tables);
	}
	
	public static interface Joins {
		Joins leftOuterJoin(String join, boolean fetch);
		Criteria where(String whereClause);
		OrderBy orderBy(String order);
		String toString();
	}
	
	public static interface Criteria {
		Criteria and(String whereClause);
		Criteria or(String whereClause);
		OrderBy orderBy(String order);
		String toString();
	}
	
	public static interface OrderBy {
		OrderBy orderBy(String order);
		OrderBy plus(String order);
		String toString();
	}
	
	private static class sqlsteps implements SelectFields, FromTables, Joins, Criteria, OrderBy {
		StringBuilder sqlBuilder;
		private sqlsteps() {
			sqlBuilder = new StringBuilder();
		}
		public FromTables select(String fields) {
			sqlBuilder.append("SELECT ").append(fields);
			return this;
		}
		public Joins from(String tables) {
			sqlBuilder.append(" FROM ").append(tables);
			return this;
		}
		public Joins leftOuterJoin(String join, boolean fetch) {
			sqlBuilder.append(" LEFT OUTER JOIN ");
			if(fetch)
				sqlBuilder.append("FETCH ");
			sqlBuilder.append(join);
			return this;
		}		
		public Criteria where(String where) {
			sqlBuilder.append(" WHERE ").append(where);
			return this;
		}
		public Criteria and(String where) {
			sqlBuilder.append(" AND ").append(where);
			return this;
		}
		public Criteria or(String where) {
			sqlBuilder.append(" OR ").append(where);
			return this;
		}
		public OrderBy orderBy(String order) {
			sqlBuilder.append(" ORDER BY ").append(order);
			return this;
		}
		public OrderBy plus(String order) {
			sqlBuilder.append(", ").append(order);
			return this;
		}
		public String toString() {
			System.out.println("*********************************");
			System.out.println(sqlBuilder.toString());
			System.out.println("*********************************");
			return sqlBuilder.toString();
		}
	}
}
