package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    // Класс получает результат работы функции и возвращает текст нормального вывода

    public static String format(Object result, String analysisType) {
        if (result == null) return "Данные отсутствуют";

        return switch (analysisType) {
            case "Count" -> "Общее количество сессий: " + result;
            case "MinDuration" -> "Минимальный сон: " + result + " мин.";
            case "MaxDuration" -> String.format("Самый долгий сон длился: %d мин. ", (Long) result);
            case "AverageDuration" -> String.format("Средняя продолжительность сна: %.1f мин. ", (Double) result);
            case "BadQualityCount" -> String.format("Количество «плохих» сессий сна: %d ", (Long) result);

            default -> "Результат (" + analysisType + "): " + result;
        };
    }
}
