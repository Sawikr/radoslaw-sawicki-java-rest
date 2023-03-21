package com.crud.tasks.additional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.Map;
import static com.crud.tasks.additional.WeightedAverage.calculateWeightedAverage;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AveragingWeightedTest {

    @Test
    public void testAveragingWeighted() {
        //Given
        Map<Double, Integer> map = new HashMap<>();
        map.put(3.0, 4);
        map.put(1.0, 6);
        map.put(1.5, 8);
        map.put(5.0, 4);
        map.put(6.0, 4);
        map.put(4.0, 10);

        //When
        Double weightedAverage = calculateWeightedAverage(map);
        System.out.println("Weighted average is: " + weightedAverage);

        //Then
        assertEquals(3.1666666666666665, weightedAverage);
    }
}