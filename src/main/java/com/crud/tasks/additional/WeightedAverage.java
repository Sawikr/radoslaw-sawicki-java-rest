package com.crud.tasks.additional;

import java.util.Map;

public class WeightedAverage {

    static Double calculateWeightedAverage(Map<Double, Integer> map) throws ArithmeticException {
        double num = 0;
        double denom = 0;
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            num += entry.getKey() * entry.getValue();
            denom += entry.getValue();
        }
        return num / denom;
    }
}
