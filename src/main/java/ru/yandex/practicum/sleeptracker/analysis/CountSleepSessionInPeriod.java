package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class CountSleepSessionInPeriod implements Function<List<SleepingSession>, Integer> {

    @Override
    public Integer apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return 0;
        }

        return sessions.size();
    }
}
