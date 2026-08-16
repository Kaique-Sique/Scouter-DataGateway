package com.scouter.gateway.build;

public record BuildInfo(
    String repo,
    String branch,
    String commit,
    String commitMessage,
    String commitAuthor,
    String buildTime
) {}