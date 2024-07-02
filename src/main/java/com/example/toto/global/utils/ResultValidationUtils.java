package com.example.toto.global.utils;

import com.example.toto.exception.InvalidValueException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultValidationUtils {
    private final Map<Integer, String> GameResultMap = new HashMap<>();
    private final Map<Integer, String> BettingResultMap = new HashMap<>();

    public String gameResultValidation(Integer key) {
        String result = GameResultMap.getOrDefault(key, null);
        if (result == null) throw new InvalidValueException();
        return result;
    }
    public String BettingResultValidation(Integer key) {
        String result = BettingResultMap.getOrDefault(key, null);
        if (result == null) throw new InvalidValueException();
        return result;
    }

    public ResultValidationUtils() {
        GameResultMap.put(0, "");
        GameResultMap.put(1, "HomeWin");
        GameResultMap.put(2, "AwayWin");
        GameResultMap.put(3, "Draw");
        GameResultMap.put(4, "Canceled");

        BettingResultMap.put(0, "");
        BettingResultMap.put(1, "Lose");
        BettingResultMap.put(2, "Win");
    }
}
