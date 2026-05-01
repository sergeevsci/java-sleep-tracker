package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    // Класс получает результат работы функции и возвращает текст нормального вывода

    public static String format(Object result, String analysisType) {
        if (result == null) return "Данные отсутствуют";

        return switch (analysisType) {
            case "Count" -> // для CountSleepSessionInPeriod
                    "Общее количество сессий: " + result;
            case "MinDuration" -> // для MinDurationSession
                    "Минимальный сон: " + result + " мин.";
            default -> "Результат (" + analysisType + "): " + result;
        };
    }
}
