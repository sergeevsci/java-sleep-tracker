package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    void countSleepSessionInPeriodShouldReturnSessionsCount() {
        CountSleepSessionInPeriod countSleepSessionInPeriod = new CountSleepSessionInPeriod();

        Integer result = countSleepSessionInPeriod.apply(List.of(
                session(0, 60, "GOOD"),
                session(120, 240, "BAD"),
                session(300, 450, "NORMAL")
        ));

        assertEquals(3, result);
    }

    @Test
    void countSleepSessionInPeriodShouldReturnZeroForNullSessions() {
        CountSleepSessionInPeriod countSleepSessionInPeriod = new CountSleepSessionInPeriod();

        Integer result = countSleepSessionInPeriod.apply(null);

        assertEquals(0, result);
    }

    @Test
    void badQualityCountSessionShouldCountBadQualityIgnoringCase() {
        BadQualityCountSession badQualityCountSession = new BadQualityCountSession();

        Long result = badQualityCountSession.apply(List.of(
                session(0, 60, "BAD"),
                session(120, 240, "bad"),
                session(300, 450, "GOOD")
        ));

        assertEquals(2L, result);
    }

    @Test
    void badQualityCountSessionShouldReturnZeroForNullSessions() {
        BadQualityCountSession badQualityCountSession = new BadQualityCountSession();

        Long result = badQualityCountSession.apply(null);

        assertEquals(0L, result);
    }

    @Test
    void averageDurationSessionShouldReturnAverageDurationInMinutes() {
        AverageDurationSession averageDurationSession = new AverageDurationSession();

        Double result = averageDurationSession.apply(List.of(
                session(0, 60, "GOOD"),
                session(120, 240, "BAD"),
                session(300, 480, "NORMAL")
        ));

        assertEquals(120.0, result, 0.001);
    }

    @Test
    void averageDurationSessionShouldReturnZeroForEmptySessions() {
        AverageDurationSession averageDurationSession = new AverageDurationSession();

        Double result = averageDurationSession.apply(Collections.emptyList());

        assertEquals(0.0, result, 0.001);
    }

    @Test
    void maxDurationSessionShouldReturnLongestDurationInMinutes() {
        MaxDurationSession maxDurationSession = new MaxDurationSession();

        Long result = maxDurationSession.apply(List.of(
                session(0, 60, "GOOD"),
                session(120, 270, "BAD"),
                session(300, 420, "NORMAL")
        ));

        assertEquals(150L, result);
    }

    @Test
    void maxDurationSessionShouldReturnZeroForEmptySessions() {
        MaxDurationSession maxDurationSession = new MaxDurationSession();

        Long result = maxDurationSession.apply(Collections.emptyList());

        assertEquals(0L, result);
    }

    @Test
    void minDurationSessionShouldReturnShortestDurationInMinutes() {
        MinDurationSession minDurationSession = new MinDurationSession();

        Long result = minDurationSession.apply(List.of(
                session(0, 90, "GOOD"),
                session(120, 180, "BAD"),
                session(300, 420, "NORMAL")
        ));

        assertEquals(60L, result);
    }

    @Test
    void minDurationSessionShouldReturnZeroForEmptySessions() {
        MinDurationSession minDurationSession = new MinDurationSession();

        Long result = minDurationSession.apply(Collections.emptyList());

        assertEquals(0L, result);
    }

    @Test
    void sleeplessNightCountSessionShouldReturnZeroForNullSessions() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        Long result = sleeplessNightCountSession.apply(null);

        assertEquals(0L, result);
    }

    @Test
    void sleeplessNightCountSessionShouldReturnZeroWhenEachNightHasSleep() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        Long result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 23, 0, 2026, 5, 2, 7, 0, "GOOD"),
                session(2026, 5, 2, 23, 30, 2026, 5, 3, 6, 30, "GOOD"),
                session(2026, 5, 3, 22, 45, 2026, 5, 4, 5, 45, "NORMAL")
        ));

        assertEquals(0L, result);
    }

    @Test
    void sleeplessNightCountSessionShouldCountNightWithoutSleepBetweenSessions() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        Long result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 23, 0, 2026, 5, 2, 7, 0, "GOOD"),
                session(2026, 5, 3, 23, 0, 2026, 5, 4, 7, 0, "GOOD")
        ));

        assertEquals(1L, result);
    }

    @Test
    void sleeplessNightCountSessionShouldIgnoreDaytimeSleep() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        Long result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 1, 14, 0, 2026, 5, 1, 16, 0, "GOOD"),
                session(2026, 5, 2, 13, 0, 2026, 5, 2, 15, 0, "GOOD")
        ));

        assertEquals(1L, result);
    }

    @Test
    void sleeplessNightCountSessionShouldCountAcrossMonthBoundary() {
        SleeplessNightCountSession sleeplessNightCountSession = new SleeplessNightCountSession();

        Long result = sleeplessNightCountSession.apply(List.of(
                session(2026, 5, 31, 23, 0, 2026, 6, 1, 7, 0, "GOOD"),
                session(2026, 6, 2, 23, 0, 2026, 6, 3, 7, 0, "GOOD")
        ));

        assertEquals(1L, result);
    }

    @Test
    void userChronotypeSessionShouldReturnUndefinedForEmptySessions() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        String result = userChronotypeSession.apply(Collections.emptyList());

        assertEquals("Не определен", result);
    }

    @Test
    void userChronotypeSessionShouldClassifyOwlWhenOwlSessionsAreMajority() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        String result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 23, 30, 2026, 5, 2, 9, 30, "GOOD"),
                session(2026, 5, 2, 23, 45, 2026, 5, 3, 10, 0, "GOOD"),
                session(2026, 5, 3, 21, 30, 2026, 5, 4, 6, 30, "GOOD")
        ));

        assertEquals("Сова", result);
    }

    @Test
    void userChronotypeSessionShouldClassifyLarkWhenLarkSessionsAreMajority() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        String result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 21, 30, 2026, 5, 2, 6, 30, "GOOD"),
                session(2026, 5, 2, 21, 45, 2026, 5, 3, 6, 45, "GOOD"),
                session(2026, 5, 3, 23, 30, 2026, 5, 4, 9, 30, "GOOD")
        ));

        assertEquals("Жаворонок", result);
    }

    @Test
    void userChronotypeSessionShouldReturnPigeonForTieBetweenOwlsAndLarks() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        String result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 23, 30, 2026, 5, 2, 9, 30, "GOOD"),
                session(2026, 5, 2, 21, 30, 2026, 5, 3, 6, 30, "GOOD")
        ));

        assertEquals("Голубь", result);
    }

    @Test
    void userChronotypeSessionShouldReturnPigeonForOnlyDaytimeSleep() {
        UserChronotypeSession userChronotypeSession = new UserChronotypeSession();

        String result = userChronotypeSession.apply(List.of(
                session(2026, 5, 1, 13, 0, 2026, 5, 1, 14, 0, "GOOD"),
                session(2026, 5, 2, 15, 0, 2026, 5, 2, 16, 0, "GOOD")
        ));

        assertEquals("Голубь", result);
    }

    private static SleepingSession session(int startMinute, int endMinute, String quality) {
        LocalDateTime baseTime = LocalDateTime.of(2026, 5, 1, 0, 0);
        return new SleepingSession(baseTime.plusMinutes(startMinute), baseTime.plusMinutes(endMinute), quality);
    }

    private static SleepingSession session(
            int startYear, int startMonth, int startDay, int startHour, int startMinute,
            int endYear, int endMonth, int endDay, int endHour, int endMinute,
            String quality
    ) {
        return new SleepingSession(
                LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute),
                LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute),
                quality
        );
    }
}
