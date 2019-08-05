package com.noter.storage;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Query {

    private String query;
    private Object[] parameters;

    public Query(String query) {
        this.query = query;
    }

    public Query(String query, Object... parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setParameters(Object... parameters) {
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void execute(Database database) {
        database.query(query, parameters);
    }
}