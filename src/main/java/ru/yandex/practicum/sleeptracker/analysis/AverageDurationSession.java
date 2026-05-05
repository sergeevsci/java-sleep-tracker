package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.model.AnalysisResult;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageDurationSession implements Function<List<SleepingSession>, AnalysisResult<Double>> {

    @Override
    public AnalysisResult<Double> apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new AnalysisResult<>("Средняя продолжительность сна, мин.", 0.0);
        }

        double averageDuration = sessions.stream()
                .mapToLong(session ->
                        Duration.between(session.getStart(), session.getEnd()).toMinutes()
                )
                .average()
                .orElse(0.0);

        return new AnalysisResult<>("Средняя продолжительность сна, мин.", averageDuration);
    }
}
