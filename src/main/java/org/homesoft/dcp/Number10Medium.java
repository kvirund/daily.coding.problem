package org.homesoft.dcp;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * This problem was asked by Apple.
 * <p>
 * Implement a job scheduler which takes in a function f and an integer n, and calls f after n milliseconds.
 */
public class Number10Medium {
    private static Logger logger = LoggerFactory.getLogger(Number10Medium.class.getName());
    private final TreeMap<Long, List<Callable<Void>>> schedule = new TreeMap<>();
    private boolean done = false;

    private Number10Medium() {
        new Thread(this::worker).start();
    }

    public static void main(String[] args) {
        configureLogger();

        Number10Medium solution = new Number10Medium();

        solution.scheduleSync("1", 400);
        solution.scheduleSync(new Function("2"), 500);
        solution.scheduleAsync("3", 1000);
        solution.scheduleAsync(new Function("4"), 800);

        for (int i = 0; i != 16; ++i) {
            solution.scheduleAsync(new Function("T34-" + i), 1000 + 10 * (i >> 2));
        }
        solution.finish();
    }

    private static void configureLogger() {
        final Properties props = new Properties();
        try {
            final URL url = Loader.getResource("log4j.properties");
            props.load(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);
    }

    private void worker() {
        synchronized (schedule) {
            while (!done || !schedule.isEmpty()) {
                try {
                    final long currentTimestamp = System.currentTimeMillis();
                    while (!schedule.isEmpty() && schedule.firstKey() <= currentTimestamp) {
                        final long first = schedule.firstKey();
                        final List<Callable<Void>> functions = schedule.firstEntry().getValue();
                        for (Callable<Void> function : functions) {
                            new Thread(() -> {
                                try {
                                    function.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                        schedule.remove(first);
                    }

                    if (schedule.isEmpty()) {
                        logger.debug("Waiting for new events.");
                        if (!done) {
                            schedule.wait();
                        }
                    } else {
                        final long timeout = schedule.firstKey() - currentTimestamp;
                        logger.debug("Waiting {} milliseconds till next scheduled task.", timeout);
                        schedule.wait(timeout);
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void scheduleSync(String id, long timeout) {
        logger.info("Scheduling task with ID " + id);
        final Callable<Void> function = new Function(id);
        scheduleSync(function, timeout);
    }

    private void scheduleSync(Callable<Void> function, long timeout) {
        try {
            Thread.sleep(timeout);
            function.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleAsync(Callable<Void> function, long timeout) {
        synchronized (schedule) {
            final long timestamp = System.currentTimeMillis() + timeout;
            if (!schedule.containsKey(timestamp)) {
                schedule.put(timestamp, new LinkedList<>());
            }
            final List<Callable<Void>> functions = schedule.get(timestamp);
            functions.add(function);

            schedule.notify();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void scheduleAsync(String id, long timeout) {
        logger.info("Scheduling task with ID " + id);
        final Callable<Void> function = new Function(id);
        scheduleAsync(function, timeout);
    }

    private synchronized void finish() {
        logger.info("Waiting for finishing all tasks.");
        synchronized (schedule) {
            done = true;
            schedule.notify();
        }
    }

    static class Function implements Callable<Void> {
        private final String id;

        Function(String id) {
            this.id = id;
        }

        @Override
        public Void call() {
            logger.info("Called function " + id);

            return null;
        }
    }
}
