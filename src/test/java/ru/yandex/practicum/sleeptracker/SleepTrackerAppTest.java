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


    private static SleepingSession session(int startMinute, int endMinute, String quality) {
        LocalDateTime baseTime = LocalDateTime.of(2026, 5, 1, 0, 0);
        return new SleepingSession(baseTime.plusMinutes(startMinute), baseTime.plusMinutes(endMinute), quality);
    }
}
