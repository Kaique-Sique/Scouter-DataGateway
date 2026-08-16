package com.scouter.gateway.controller.health;

//Java imports
import java.time.Instant;
import java.util.List;

//Spring framework imports
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//local imports
import com.scouter.gateway.build.BuildInfoProvider;

@RestController
public class HealthController {

    private final BuildInfoProvider buildInfoProvider;

    public HealthController(BuildInfoProvider buildInfoProvider) {
        this.buildInfoProvider = buildInfoProvider;
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
            buildInfoProvider._get(),
            "0.0.1",
            Instant.now(),
            "17.0.16",
            "4.1.0",
            "development",
            List.of(
                new HealthDependency(
                    "Supabase-db",
                    "not-implemented",
                    0)
            )
        );
    }
}
