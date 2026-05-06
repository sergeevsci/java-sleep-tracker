package ru.yandex.practicum.sleeptracker.model;

public enum Chronotype {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь"),
    UNDEFINED("Не определен");

    private final String displayName;

    Chronotype(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {

        return displayName;
    }
}
