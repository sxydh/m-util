package cn.net.bhe.mutil;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sxydh
 */
public class JdbcUtils {

    static final ExecutorService executorService = new ThreadPoolExecutor(
            10,
            20,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new BasicThreadFactory.Builder()
                    .namingPattern("JdbcUtils executorService - %d")
                    .daemon(false)
                    .priority(Thread.MAX_PRIORITY)
                    .build(),
            new ThreadPoolExecutor.DiscardPolicy());

    public static <T> long batchInsert(DataSource dataSource, String table, List<T> inserts, int threads, int await) {
        if (inserts == null || inserts.size() == 0) return 0;
        ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>(inserts);
        Field[] fields = inserts.get(0).getClass().getDeclaredFields();
        List<String> columns = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            columns.add(BtringUtils.camelToSnake(field.getName()));
            placeholders.add("?");
        }
        // 开启任务
        CountDownLatch latch = new CountDownLatch(threads);
        AtomicLong atomicLong = new AtomicLong();
        for (int i = 0; i < threads; i++) {
            executorService.submit(() -> {
                try (Connection connection = dataSource.getConnection()) {
                    connection.setAutoCommit(false);
                    connection.setClientInfo("rewriteBatchedStatements", "true");
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            String.format(
                                    " insert into %s (%s) values (%s) ",
                                    table,
                                    String.join(", ", columns),
                                    String.join(", ", placeholders)));
                    // 批量插入
                    while (!queue.isEmpty()) {
                        // 建议50条提交一次
                        for (int j = 0; j < 50; j++) {
                            T t = queue.poll();
                            if (t != null) {
                                // 拼接参数
                                preparedStatement.clearParameters();
                                for (int k = 1; k <= fields.length; k++) {
                                    Object fieldValue = fields[k - 1].get(t);
                                    if (fieldValue instanceof String) {
                                        preparedStatement.setString(k, (String) fieldValue);
                                    } else if (fieldValue != null) {
                                        preparedStatement.setObject(k, fieldValue);
                                    } else {
                                        preparedStatement.setNull(k, Types.NULL);
                                    }
                                }
                                preparedStatement.addBatch();
                            } else {
                                break;
                            }
                        }
                        int[] counts = preparedStatement.executeBatch();
                        connection.commit();
                        atomicLong.addAndGet(counts.length);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        // 是否等待
        if (await > 0) {
            try {
                latch.await(await, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return atomicLong.get();
    }

}
