package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountSession implements Function<List<SleepingSession>, Long> {

    @Override
    public Long apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return 0L;
        }

        return sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
    }
}
