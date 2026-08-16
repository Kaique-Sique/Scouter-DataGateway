package com.scouter.gateway.build;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BuildInfoProvider {

    @Value("${git.remote.origin.url:unknown}")
    private String repo;

    @Value("${git.branch:unknown}")
    private String branch;

    @Value("${git.commit.id:unknown}")
    private String commit;

    @Value("${git.commit.message.short:unknown}")
    private String commitMessage;

    @Value("${git.commit.user.name:unknown}")
    private String commitAuthor;

    @Value("${git.commit.time:unknown}")
    private String buildTime;

    public BuildInfo _get() {
        return new BuildInfo(
            repo,
            branch,
            commit,
            commitMessage,
            commitAuthor,
            buildTime
        );
    }
}