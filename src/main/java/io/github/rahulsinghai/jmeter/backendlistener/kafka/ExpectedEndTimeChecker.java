package io.github.rahulsinghai.jmeter.backendlistener.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpectedEndTimeChecker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ExpectedEndTimeChecker.class);

    private boolean running = true;
    private final long expectedDuration;
    private final long delayTime;
    private final long startTime;

    public ExpectedEndTimeChecker(long expectedDuration, long delayTime) {
        this.expectedDuration = expectedDuration;
        this.startTime = System.currentTimeMillis();
        this.delayTime = delayTime;
        logger.info("ExpectedDuration: " + expectedDuration + ", delay: " + delayTime);
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (System.currentTimeMillis() > startTime + expectedDuration + delayTime) {
                    logger.info("ExpectedEndTime checked, stop engine now.");
                    logger.info("Notifying test listeners of end of test");
                    Thread.sleep(2000);
                    System.exit(0);
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
