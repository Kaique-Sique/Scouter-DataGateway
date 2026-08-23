package com.scouter.gateway.scout.teleop;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TeleopScoutService {
    private TeleopScoutRepository teleopScoutRepository;

    public TeleopScoutService(TeleopScoutRepository teleopScoutRepository)
    {
        this.teleopScoutRepository = teleopScoutRepository;
    }

    public void DeleteByMatchTeamId(String MatchTeamId){
        
    }
}
