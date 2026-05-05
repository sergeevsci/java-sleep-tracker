package ru.yandex.practicum.sleeptracker.model;

public class AnalysisResult<T> {
    private final String description;
    private final T result;

    public AnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }
}
