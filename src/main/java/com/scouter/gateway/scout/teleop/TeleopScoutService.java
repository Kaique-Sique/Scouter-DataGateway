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


    Optional<TeleopScout> findByMatchTeamId(String matchTeamId) 
    {
        return null;
    }

    List<TeleopScout> findByUserId(UUID userId) 
    {
        return null;
    }

    List<TeleopScout> findByTeamKey(String teamKey) 
    {
        return null;
    }

    List<TeleopScout> findByEventKey(String eventKey) 
    {
        return null;
    }

    List<TeleopScout> findByMatchKey(String matchKey) 
    {
        return null;
    }

    List<TeleopScout> findByTeamKeyAndMatchKey(String teamKey, String matchKey) 
    {
        return null;
    }

    boolean existsByMatchTeamId(String matchTeamId) 
    {
        return false;
    }

    void deleteByMatchTeamId(String matchTeamId)
    {
        
    }
}
