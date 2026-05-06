package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analysis.*;
import ru.yandex.practicum.sleeptracker.io.SleepingReadingFile;
import ru.yandex.practicum.sleeptracker.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final String NAME_FILE = "sleep_log.txt";

    private static final List<Function<List<SleepingSession>, ? extends AnalysisResult<?>>> functionsForAnalyze = new ArrayList<>();

    public static void main(String[] args) {
        functionsForAnalyze.add(new CountSleepSessionInPeriod());
        functionsForAnalyze.add(new MinDurationSession());
        functionsForAnalyze.add(new MaxDurationSession());
        functionsForAnalyze.add(new AverageDurationSession());
        functionsForAnalyze.add(new BadQualityCountSession());
        functionsForAnalyze.add(new SleeplessNightCountSession());
        functionsForAnalyze.add(new UserChronotypeSession());

        SleepingReadingFile sleepingReadingFile = new SleepingReadingFile(NAME_FILE);
        List<SleepingSession> allSessions = sleepingReadingFile.readFile();

        functionsForAnalyze.forEach(func -> {
            AnalysisResult<?> analysisResult = func.apply(allSessions);
            System.out.println(SleepAnalysisResult.format(analysisResult));
        });
    }
}
