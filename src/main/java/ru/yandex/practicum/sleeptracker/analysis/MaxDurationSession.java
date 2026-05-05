package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxDurationSession implements Function<List<SleepingSession>, Long> {

    @Override
    public Long apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return 0L;
        }

        return sessions.stream()
                .mapToLong(session ->
                        Duration.between(session.getStart(), session.getEnd()).toMinutes()
                )
                .max()
                .orElse(0L);
    }
}
