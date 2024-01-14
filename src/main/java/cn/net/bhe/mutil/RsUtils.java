package cn.net.bhe.mutil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RsUtils {

    public static RsIt getRsIt(ResultSet resultSet) {
        return new RsIt(resultSet);
    }

    public static class RsIt implements Iterator<Map<String, Object>> {

        private final ResultSet resultSet;
        private final ResultSetMetaData metaData;
        private final int columnCount;

        private RsIt(ResultSet resultSet) {
            try {
                this.resultSet = resultSet;
                this.metaData = resultSet.getMetaData();
                this.columnCount = this.metaData.getColumnCount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            try {
                return resultSet.next();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Map<String, Object> next() {
            try {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    map.put(columnName, columnValue);
                }
                return map;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
