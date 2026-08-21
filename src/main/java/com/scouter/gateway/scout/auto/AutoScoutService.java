package com.scouter.gateway.scout.auto;

import org.springframework.stereotype.Service;

@Service
public class AutoScoutService {

    private final AutoScoutRepository repository;

    public AutoScoutService(AutoScoutRepository repository)
    {
        this.repository = repository;
    }
    
}
