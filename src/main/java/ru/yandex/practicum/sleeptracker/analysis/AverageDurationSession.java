package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageDurationSession implements Function<List<SleepingSession>, Double> {

    @Override
    public Double apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return 0.0;
        }

        return sessions.stream()
                .mapToLong(session ->
                        Duration.between(session.getStart(), session.getEnd()).toMinutes()
                )
                .average()
                .orElse(0.0);
    }
}
