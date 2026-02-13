package com.example.sentinel;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class SentinelHealthIndicator implements HealthIndicator {

    private final SentinelController sentinelController;

    public SentinelHealthIndicator(SentinelController sentinelController) {
        this.sentinelController = sentinelController;
    }

    @Override
    public Health health() {
        if (sentinelController.isHealthy()) {
            return Health.up().withDetail("Sentinel", "Operational").build();
        }
        return Health.down().withDetail("Sentinel", "Failure Simulated").build();
    }
}
