package com.scouter.gateway.user.favorites.events;

import java.util.List;

public record FavoriteEventResponse(
        List<String> events
) {}