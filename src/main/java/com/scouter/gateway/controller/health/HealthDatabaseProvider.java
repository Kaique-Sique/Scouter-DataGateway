package com.scouter.gateway.controller.health;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * HealthDatabaseProvider
 */
@componet
public class HealthDatabaseProvider {
    private final JdbcTemplate jdbcTemplate;

    public HealthDatabaseProvider(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public HealthDependency check()
    {
        long startTime = System.currentTimeMillis();

        try {
            // Query test script
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            long latency = System.currentTimeMillis() - startTime;

            return new HealthDependency("Supabase-db", "up", latency);

        } catch (Exception e) {
            long latency = System.currentTimeMillis() - start;
            return new HealthDependency("Supabase-db", $"down-{e}", latency);

        }

        return new HealthDependency(null, null, null, null);
    }
}