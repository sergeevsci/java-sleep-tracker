package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.AnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class CountSleepSessionInPeriod implements Function<List<SleepingSession>, AnalysisResult<Integer>> {

    @Override
    public AnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return new AnalysisResult<>("Общее количество сессий", 0);
        }

        return new AnalysisResult<>("Общее количество сессий", sessions.size());
    }
}
