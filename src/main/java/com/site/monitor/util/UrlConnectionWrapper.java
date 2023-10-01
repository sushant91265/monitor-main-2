package com.site.monitor.util;

import java.net.HttpURLConnection;

/**
 * UrlConnectionWrapper is a wrapper class for HttpURLConnection.
 * This class is used to close the connection after the request is made.
 */
public class UrlConnectionWrapper implements AutoCloseable {
    private HttpURLConnection connection;

    public UrlConnectionWrapper(HttpURLConnection connection) {
        this.connection = connection;
    }
    public HttpURLConnection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
