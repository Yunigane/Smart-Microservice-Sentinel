package com.example.sentinel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class SentinelController {

    private static final Logger logger = LoggerFactory.getLogger(SentinelController.class);
    private final AtomicBoolean isHealthy = new AtomicBoolean(true);

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        if (!isHealthy.get()) {
            logger.error("Simulation failure: NullPointerException occurred in healthCheck logic");
            throw new NullPointerException("Simulated internal error");
        }
        return ResponseEntity.ok("All systems operational");
    }

    @GetMapping("/toggle-failure")
    public String toggleFailure(@RequestParam(defaultValue = "true") boolean fail) {
        isHealthy.set(!fail);
        logger.info("Application health set to: " + (isHealthy.get() ? "UP" : "DOWN"));
        return "System health is now " + (isHealthy.get() ? "UP" : "DOWN");
    }

    @GetMapping("/api/data")
    public String getData() {
        if (!isHealthy.get()) {
            logger.warn("Database connection timeout simulated");
            return "Service temporarily unavailable due to DB issues";
        }
        return "Sensitive data: DB_PASSWORD=[REDACTED] (This should be scrubbed)";
    }
}
