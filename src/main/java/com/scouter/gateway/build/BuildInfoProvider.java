package com.scouter.gateway.build;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;

@Component
public class BuildInfoProvider {

    @Autowired(required = false)
    private GitProperties gitProperties;

    public BuildInfo _get() {
        if (gitProperties == null) {
            return new BuildInfo("unknown", "unknown", "unknown", "unknown", "unknown");
        }
        return new BuildInfo(
            gitProperties.get("remote.origin.url"),
            gitProperties.getBranch(),
            gitProperties.getShortCommitId(),
            gitProperties.get("commit.user.name"),
            gitProperties.getCommitTime() != null ? gitProperties.getCommitTime().toString() : "unknown"
        );
    }
}