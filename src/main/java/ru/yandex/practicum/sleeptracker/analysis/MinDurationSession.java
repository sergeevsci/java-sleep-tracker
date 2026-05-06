package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.AnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinDurationSession implements Function<List<SleepingSession>, AnalysisResult<Long>> {

    @Override
    public AnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            return new AnalysisResult<>(AnalysisConstants.MIN_DURATION_TITLE, 0L);
        }

        long minDuration = sessions.stream()
                .mapToLong(session ->
                        Duration.between(session.getStart(), session.getEnd()).toMinutes()
                )
                .min()
                .orElse(0L);

        return new AnalysisResult<>(AnalysisConstants.MIN_DURATION_TITLE, minDuration);
    }
}
