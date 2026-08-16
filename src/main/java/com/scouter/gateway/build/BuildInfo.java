package com.scouter.gateway.build;

public record BuildInfo(
    String repo,
    String branch,
    String commit,
    String commitAuthor,
    String buildTime
) {}