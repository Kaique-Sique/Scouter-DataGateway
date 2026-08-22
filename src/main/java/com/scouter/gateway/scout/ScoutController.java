package com.scouter.gateway.scout;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scouter.gateway.auth.AuthService;
import com.scouter.gateway.scout.auto.AutoScoutService;
import com.scouter.gateway.scout.teleop.TeleopScoutService;

@RestController
@RequestMapping("/scout")
public class ScoutController {
    private final AuthService authService;
    private final AutoScoutService autoScoutService;
    private final TeleopScoutService teleopScoutService;

    public ScoutController(AuthService authService, AutoScoutService autoScoutService, TeleopScoutService teleopScoutService)
    {
        this.authService = authService;
        this.autoScoutService = autoScoutService;
        this.teleopScoutService = teleopScoutService;
    }

    
}
