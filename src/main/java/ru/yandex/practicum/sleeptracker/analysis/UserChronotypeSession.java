package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserChronotypeSession implements Function<List<SleepingSession>, Chronotype> {

    @Override
    public Chronotype apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return Chronotype.UNDEFINED;
        }

        LocalTime owlSleep = LocalTime.of(23, 0);
        LocalTime owlWake = LocalTime.of(9, 0);
        LocalTime larkSleep = LocalTime.of(22, 0);
        LocalTime larkWake = LocalTime.of(7, 0);
        LocalTime nightEnd = LocalTime.of(6, 0);

        Map<Chronotype, Long> counts = sessions.stream()
                .filter(s -> s.getStart().toLocalTime().isBefore(nightEnd)
                        || s.getEnd().toLocalDate().isAfter(s.getStart().toLocalDate()))
                .map(s -> {
                    LocalTime start = s.getStart().toLocalTime();
                    LocalTime end = s.getEnd().toLocalTime();

                    if (start.isAfter(owlSleep) && end.isAfter(owlWake)) {
                        return Chronotype.OWL;
                    } else if (start.isBefore(larkSleep) && end.isBefore(larkWake)) {
                        return Chronotype.LARK;
                    } else {
                        return Chronotype.PIGEON;
                    }
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long pigeons = counts.getOrDefault(Chronotype.PIGEON, 0L);

        if ((pigeons >= owls && pigeons >= larks) || owls == larks) {
            return Chronotype.PIGEON;
        }

        return owls > larks ? Chronotype.OWL : Chronotype.LARK;
    }
}
