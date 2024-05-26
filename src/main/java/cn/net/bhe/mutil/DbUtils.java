package cn.net.bhe.mutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbUtils {

    private Connection _connection;

    private DbUtils() {
    }

    public static DbUtils build(String url, String user, String password) throws Exception {
        DbUtils dbUtils = new DbUtils();
        dbUtils._connection = DriverManager.getConnection(url, user, password);
        return dbUtils;
    }

    private Connection getConnection() {
        return _connection;
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public boolean execute(String sql) throws Exception {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            return statement.execute(sql);
        }
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public List<HashMap<String, Object>> executeQuery(String sql) throws Exception {
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
        }
    }

}
