package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.AnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountSession implements Function<List<SleepingSession>, AnalysisResult<Long>> {

    @Override
    public AnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return new AnalysisResult<>("Количество плохих сессий сна", 0L);
        }

        long badQualityCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new AnalysisResult<>("Количество плохих сессий сна", badQualityCount);
    }
}
