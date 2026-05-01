package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;
import java.util.List;

public class CountSleepSessionInPeriod implements Function<List<SleepingSession>, Integer> {

    // Принимаем List<SleepingSession>, вернем количество

    @Override
    public Integer apply(List<SleepingSession> sessions) {
        if (sessions == null) return 0;
        return sessions.size();
    }
}
