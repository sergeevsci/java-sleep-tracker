package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {

    public static String format(Object result, String analysisType) {
        if (result == null) {
            return "Данные отсутствуют";
        }

        return switch (analysisType) {
            case "Count" -> "Общее количество сессий: " + result;
            case "MinDuration" -> "Минимальный сон: " + result + " мин.";
            case "MaxDuration" -> String.format("Самый долгий сон длился: %d мин. ", (Long) result);
            case "AverageDuration" -> String.format("Средняя продолжительность сна: %.1f мин. ", (Double) result);
            case "BadQualityCount" -> String.format("Количество «плохих» сессий сна: %d ", (Long) result);
            case "SleeplessNightCount" -> String.format(
                    "Количество бессонных ночей (00:00-06:00 без сна): %d",
                    (Long) result
            );
            case "UserChronotype" -> String.format("Ваш хронотип: %s", result);
            default -> "Результат (" + analysisType + "): " + result;
        };
    }
}
