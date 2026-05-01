package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountSession implements Function<List<SleepingSession>, Long> {

    @Override
    public Long apply(List<SleepingSession> sessions) {
        if (sessions == null) return 0L;

        return sessions.stream()
                .filter(session -> "BAD".equalsIgnoreCase(session.getQuality())) // Фильтр по качеству
                .count();
    }
}
