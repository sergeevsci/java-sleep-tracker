package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String quality;


    public SleepingSession(LocalDateTime start, LocalDateTime end, String quality) {

        this.start = start;
        this.end = end;
        this.quality = quality;
    }


    public LocalDateTime getStart() {

        return start;

    }


    public LocalDateTime getEnd() {

        return end;

    }


    public String getQuality() {

        return quality;

    }


    @Override
    public String toString() {

        return String.format("Сон с %s до %s, качество: %s", start, end, quality);
    }


}

