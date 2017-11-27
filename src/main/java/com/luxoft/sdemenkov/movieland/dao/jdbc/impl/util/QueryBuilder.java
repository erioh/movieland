package com.luxoft.sdemenkov.movieland.dao.jdbc.impl.util;


public class QueryBuilder {
    private StringBuilder stringBuilder;

    private QueryBuilder() {

    }

    private QueryBuilder(String query) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(query);
    }

    public static QueryBuilder forQuery(String query) {
        query = query.replace(";", "");
        return new QueryBuilder(query);
    }

    public QueryBuilder withLimit(int pageNumber, int rowsOnPage) {
        int skippedRows = (pageNumber - 1) * rowsOnPage;
        stringBuilder.append(" LIMIT ");
        stringBuilder.append(skippedRows);
        stringBuilder.append(" , ");
        stringBuilder.append(rowsOnPage);
        stringBuilder.append(" ");
        return this;
    }

    public String build() {
        stringBuilder.append(";");
        return stringBuilder.toString();
    }
}
