package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinDurationSession implements Function<List<SleepingSession>, Long> {

    @Override
    public Long apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return 0L;
        }

        return sessions.stream()
                .mapToLong(session ->
                        Duration.between(session.getStart(), session.getEnd()).toMinutes()
                )
                .min()
                .orElse(0L);
    }
}
