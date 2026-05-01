package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    static final private String NAME_FILE = "sleep_log.txt";

    private static ArrayList<Function> functionsForAnalyze = new ArrayList<>(); // Список аналитических функций, выполняем по очереди


    public static void main(String[] args) {

        // Сначала добавим все нужные функции в список
        functionsForAnalyze.add(new CountSleepSessionInPeriod());
        functionsForAnalyze.add(new MinDurationSession());

        // здесь инициируем чтение sleep_log.txt и запускаем аналитические функции
        SleepingReadingFile sRF = new SleepingReadingFile(NAME_FILE);  // здесь инициируем чтение sleep_log.txt
        List<SleepingSession> allSessions = sRF.readFile();
        //System.out.println(allSessions);


        // Пройдемся по функциям и выполним
        functionsForAnalyze.forEach(func -> {
            // Выполняем функцию
            Object rawResult = func.apply(allSessions);

            // Определяем тип для форматтера (по имени класса)
            String type = func.getClass().getSimpleName()
                    .replace("Session", "")
                    .replace("InPeriod", "");

            // Форматируем и выводим
            System.out.println(SleepAnalysisResult.format(rawResult, type));
        });


    }
}