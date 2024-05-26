package cn.net.bhe.mutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DbUtils {

    private final ArrayBlockingQueue<Connection> connPool = new ArrayBlockingQueue<>(10);

    private DbUtils() {
    }

    public static DbUtils build(String url, String user, String password) throws SQLException {
        DbUtils dbUtils = new DbUtils();
        for (int i = 0; i < 5; i++) {
            dbUtils.putConnection(DriverManager.getConnection(url, user, password));
        }
        return dbUtils;
    }

    private Connection getConnection() {
        try {
            return connPool.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can not get connection: " + e.getLocalizedMessage());
        }
    }

    private void putConnection(Connection connection) {
        try {
            connPool.put(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can not put connection: " + e.getLocalizedMessage());
        }
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public boolean execute(String sql) throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            return statement.execute(sql);
        } finally {
            putConnection(connection);
        }
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public List<HashMap<String, Object>> executeQuery(String sql) throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                List<HashMap<String, Object>> list = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    HashMap<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String key = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        row.put(key, value);
                    }
                    list.add(row);
                }
                return list;
            }
        } finally {
            putConnection(connection);
        }
    }

    public void close() throws SQLException {
        for (Connection connection : connPool) {
            connection.close();
        }
    }

}
