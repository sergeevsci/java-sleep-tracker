package ru.yandex.practicum.sleeptracker.model;

public class SleepAnalysisResult {

    public static String format(AnalysisResult<?> analysisResult) {
        if (analysisResult == null || analysisResult.getResult() == null) {
            return "Данные отсутствуют";
        }

        return analysisResult.getDescription() + ": " + formatValue(analysisResult.getResult());
    }

    private static String formatValue(Object result) {
        if (result instanceof Chronotype chronotype) {
            return chronotype.getDisplayName();
        }

        if (result instanceof Double doubleResult) {
            return String.format("%.1f", doubleResult);
        }

        return result.toString();
    }
}
