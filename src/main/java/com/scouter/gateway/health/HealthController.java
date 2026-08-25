package com.scouter.gateway.health;

//Java imports
import java.time.Instant;
import java.util.List;

//Spring framework imports
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {

    private final HealthDatabaseProvider healthDatabaseProvider;

    public HealthController(HealthDatabaseProvider healthDatabaseProvider) {
        this.healthDatabaseProvider = healthDatabaseProvider;
    }
    
    /**
     * host /health endpoint, this endpoint show all basic infos about this project,
     * such as BuildInfo - github infos, HealthDependency - db staus, and system status
     * @return HealthResponse
     */
    @GetMapping("/health")
    public HealthResponse response()
    {
        
        return new HealthResponse(
            "UP",
            "Scouter Gateway",
            "0.0.1",
            Instant.now(),
            "17.0.16",
            "4.1.0",
            "development",
            List.of(healthDatabaseProvider.check())
        );
    }
}
