package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.function.Function;

public class SleepTrackerApp {

    static final private String NAME_FILE = "sleep_log.txt";

    private ArrayList<Function> functionsForAnalyze = new ArrayList<>(); // Список аналитических функций, выполняем по очереди


    public static void main(String[] args) {
        // здесь инициируем чтение sleep_log.txt и запускаем аналитические функции
        SleepingReadingFile sRF = new SleepingReadingFile(NAME_FILE);  // здесь инициируем чтение sleep_log.txt
        System.out.println(sRF.readFile());
    }
}