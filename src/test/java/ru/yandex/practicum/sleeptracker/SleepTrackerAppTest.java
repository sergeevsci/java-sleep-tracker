package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.AverageDurationSession;
import ru.yandex.practicum.sleeptracker.analysis.BadQualityCountSession;
import ru.yandex.practicum.sleeptracker.analysis.CountSleepSessionInPeriod;
import ru.yandex.practicum.sleeptracker.analysis.MaxDurationSession;
import ru.yandex.practicum.sleeptracker.analysis.MinDurationSession;
import ru.yandex.practicum.sleeptracker.analysis.SleeplessNightCountSession;
import ru.yandex.practicum.sleeptracker.analysis.UserChronotypeSession;
import ru.yandex.practicum.sleeptracker.model.AnalysisResult;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    void countSleepSessionInPeriodShouldReturnSessionsCount() {
        CountSleepSessionInPeriod countSleepSessionInPeriod = new CountSleepSessionInPeriod();

        AnalysisResult<Integer> result = countSleepSessionInPeriod.apply(List.of(
                session(0, 60, SleepQuality.GOOD),
                session(120, 240, SleepQuality.BAD),
                session(300, 450, SleepQuality.NORMAL)
        ));

        assertEquals("Общее количество сессий", result.getDescription());
        assertEquals(3, result.getResult());
    }

    @Test
    void countSleepSessionInPeriodShouldReturnZeroForNullSessions() {
        CountSleepSessionInPeriod countSleepSessionInPeriod = new CountSleepSessionInPeriod();

        AnalysisResult<Integer> result = countSleepSessionInPeriod.apply(null);

        assertEquals(0, result.getResult());
    }

    @Test
    void badQualityCountSessionShouldCountBadQuality() {
        BadQualityCountSession badQualityCountSession = new BadQualityCountSession();

        AnalysisResult<Long> result = badQualityCountSession.apply(List.of(
                session(0, 60, SleepQuality.BAD),
                session(120, 240, SleepQuality.BAD),
                session(300, 450, SleepQuality.GOOD)
        ));

        assertEquals(2L, result.getResult());
    }

    @Test
    void badQualityCountSessionShouldReturnZeroForNullSessions() {
        BadQualityCountSession badQualityCountSession = new BadQualityCountSession();

        AnalysisResult<Long> result = badQualityCountSession.apply(null);

        assertEquals(0L, result.getResult());
    }

    @Test
    void averageDurationSessionShouldReturnAverageDurationInMinutes() {
        AverageDurationSession averageDurationSession = new AverageDurationSession();

        AnalysisResult<Double> result = averageDurationSession.apply(List.of(
                session(0, 60, SleepQuality.GOOD),
                session(120, 240, SleepQuality.BAD),
                session(300, 480, SleepQuality.NORMAL)
        ));

        assertEquals(120.0, result.getResult(), 0.001);
    }

    @Test
    void averageDurationSessionShouldReturnZeroForEmptySessions() {
        AverageDurationSession averageDurationSession = new AverageDurationSession();

        AnalysisResult<Double> result = averageDurationSession.apply(Collections.emptyList());

        assertEquals(0.0, result.getResult(), 0.001);
    }

    @Test
    void maxDurationSessionShouldReturnLongestDurationInMinutes() {
        MaxDurationSession maxDurationSession = new MaxDurationSession();

        AnalysisResult<Long> result = maxDurationSession.apply(List.of(
                session(0, 60, SleepQuality.GOOD),
                session(120, 270, SleepQuality.BAD),
                session(300, 420, SleepQuality.NORMAL)
        ));

        assertEquals(150L, result.getResult());
    }

    @Test
    void maxDurationSessionShouldReturnZeroForEmptySessions() {
        MaxDurationSession maxDurationSession = new MaxDurationSession();

        AnalysisResult<Long> result = maxDurationSession.apply(Collections.emptyList());

        assertEquals(0L, result.getResult());
    }

    @Test
    void minDurationSessionShouldReturnShortestDurationInMinutes() {
        MinDurationSession minDurationSession = new MinDurationSession();

        AnalysisResult<Long> result = minDurationSession.apply(List.of(
                session(0, 90, SleepQuality.GOOD),
                session(120, 180, SleepQuality.BAD),
                session(300, 420, SleepQuality.NORMAL)
        ));

        assertEquals(60L, result.getResult());
    }

    @Test
    void minDurationSessionShouldReturnZeroForEmptySessions() {
        MinDurationSession minDurationSession = new MinDurationSession();

        AnalysisResult<Long> result = minDurationSession.apply(Collections.emptyList());

        assertEquals(0L, result.getResult());
    }

    @Test
    void sleeplessNightCountSessionShouldReturnZeroForNullSessions() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        AnalysisResult<Long> result = sleeplessNightCountSession.apply(null);

        assertEquals(0L, result.getResult());
    }

    @Test
    void sleeplessNightCountSessionShouldReturnZeroWhenEachNightHasSleep() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        AnalysisResult<Long> result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 23, 0, 2026, 5, 2, 7, 0, SleepQuality.GOOD),
                session(2026, 5, 2, 23, 30, 2026, 5, 3, 6, 30, SleepQuality.GOOD),
                session(2026, 5, 3, 22, 45, 2026, 5, 4, 5, 45, SleepQuality.NORMAL)
        ));

        assertEquals(0L, result.getResult());
    }

    @Test
    void sleeplessNightCountSessionShouldCountNightWithoutSleepBetweenSessions() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        AnalysisResult<Long> result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 23, 0, 2026, 5, 2, 7, 0, SleepQuality.GOOD),
                session(2026, 5, 3, 23, 0, 2026, 5, 4, 7, 0, SleepQuality.GOOD)
        ));

        assertEquals(1L, result.getResult());
    }

    @Test
    void sleeplessNightCountSessionShouldIgnoreDaytimeSleep() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        AnalysisResult<Long> result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 14, 0, 2026, 5, 1, 16, 0, SleepQuality.GOOD),
                session(2026, 5, 2, 13, 0, 2026, 5, 2, 15, 0, SleepQuality.GOOD)
        ));

        assertEquals(1L, result.getResult());
    }

    @Test
    void sleeplessNightCountSessionShouldCountAcrossMonthBoundary() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        AnalysisResult<Long> result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 31, 23, 0, 2026, 6, 1, 7, 0, SleepQuality.GOOD),
                session(2026, 6, 2, 23, 0, 2026, 6, 3, 7, 0, SleepQuality.GOOD)
        ));

        assertEquals(1L, result.getResult());
    }

    @Test
    void userChronotypeSessionShouldReturnUndefinedForEmptySessions() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        AnalysisResult<Chronotype> result = userChronotypeSession.apply(Collections.emptyList());

        assertEquals(Chronotype.UNDEFINED, result.getResult());
    }

    @Test
    void userChronotypeSessionShouldClassifyOwlWhenOwlSessionsAreMajority() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        AnalysisResult<Chronotype> result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 23, 30, 2026, 5, 2, 9, 30, SleepQuality.GOOD),
                session(2026, 5, 2, 23, 45, 2026, 5, 3, 10, 0, SleepQuality.GOOD),
                session(2026, 5, 3, 21, 30, 2026, 5, 4, 6, 30, SleepQuality.GOOD)
        ));

        assertEquals(Chronotype.OWL, result.getResult());
    }

    @Test
    void userChronotypeSessionShouldClassifyLarkWhenLarkSessionsAreMajority() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        AnalysisResult<Chronotype> result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 21, 30, 2026, 5, 2, 6, 30, SleepQuality.GOOD),
                session(2026, 5, 2, 21, 45, 2026, 5, 3, 6, 45, SleepQuality.GOOD),
                session(2026, 5, 3, 23, 30, 2026, 5, 4, 9, 30, SleepQuality.GOOD)
        ));

        assertEquals(Chronotype.LARK, result.getResult());
    }

    @Test
    void userChronotypeSessionShouldReturnPigeonForTieBetweenOwlsAndLarks() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        AnalysisResult<Chronotype> result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 23, 30, 2026, 5, 2, 9, 30, SleepQuality.GOOD),
                session(2026, 5, 2, 21, 30, 2026, 5, 3, 6, 30, SleepQuality.GOOD)
        ));

        assertEquals(Chronotype.PIGEON, result.getResult());
    }

    @Test
    void userChronotypeSessionShouldReturnPigeonForOnlyDaytimeSleep() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        AnalysisResult<Chronotype> result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 13, 0, 2026, 5, 1, 14, 0, SleepQuality.GOOD),
                session(2026, 5, 2, 15, 0, 2026, 5, 2, 16, 0, SleepQuality.GOOD)
        ));

        assertEquals(Chronotype.PIGEON, result.getResult());
    }

    private static SleepingSession session(int startMinute, int endMinute, SleepQuality quality) {
        LocalDateTime baseTime = LocalDateTime.of(2026, 5, 1, 0, 0);
        return new SleepingSession(baseTime.plusMinutes(startMinute), baseTime.plusMinutes(endMinute), quality);
    }

    private static SleepingSession session(
            int startYear, int startMonth, int startDay, int startHour, int startMinute,
            int endYear, int endMonth, int endDay, int endHour, int endMinute,
            SleepQuality quality
    ) {
        return new SleepingSession(
                LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute),
                LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute),
                quality
        );
    }
}
