package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.AnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightCountSession implements Function<List<SleepingSession>, AnalysisResult<Long>> {

    @Override
    public AnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new AnalysisResult<>("Количество бессонных ночей (00:00-06:00 без сна)", 0L);
        }

        LocalDate firstDate = sessions.getFirst().getStart().toLocalTime().isAfter(LocalTime.NOON)
                ? sessions.getFirst().getStart().toLocalDate().plusDays(1)
                : sessions.getFirst().getStart().toLocalDate();

        LocalDate lastDate = sessions.getLast().getEnd().toLocalDate();
        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate.plusDays(1));
        LocalTime nightEnd = LocalTime.of(6, 0);

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(session -> {
                    LocalTime start = session.getStart().toLocalTime();
                    boolean spansDays = session.getEnd().toLocalDate().isAfter(session.getStart().toLocalDate());

                    return start.isBefore(nightEnd) || spansDays;
                })
                .map(session -> session.getEnd().toLocalDate())
                .collect(Collectors.toSet());

        long sleeplessNights = totalNights - nightsWithSleep.size();
        return new AnalysisResult<>(
                "Количество бессонных ночей (00:00-06:00 без сна)",
                Math.max(0, sleeplessNights)
        );
    }
}
