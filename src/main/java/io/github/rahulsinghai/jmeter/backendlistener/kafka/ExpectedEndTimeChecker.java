package io.github.rahulsinghai.jmeter.backendlistener.kafka;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpectedEndTimeChecker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ExpectedEndTimeChecker.class);

    private boolean running;
    private final long expectedEndTime;
    private final long delayTime;

    public ExpectedEndTimeChecker(long expectedEndTime, long delayTime) {
        this.expectedEndTime = expectedEndTime;
        this.delayTime = delayTime;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (System.currentTimeMillis() > expectedEndTime + delayTime) {
                    logger.info("ExpectedEndTime checked, stop engine now.");
                    StandardJMeterEngine.stopEngineNow();
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
