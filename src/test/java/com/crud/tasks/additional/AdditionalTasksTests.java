package com.crud.tasks.additional;

import com.crud.tasks.client.TrelloClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AdditionalTasksTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @BeforeEach
    void setUp() {
        LOGGER.info("Start!");
    }

    @AfterEach
    void tearDown() {
        LOGGER.info("Stop!");
    }

    @Test
    public void shouldCheckIfIsPrime() {
        //Given
        LOGGER.info("Test shouldCheckIfIsPrime is working!");

        int number = 2;

        // When
        boolean isPrime = IsPrime.isPrime(number);

        // Then
        assertTrue(isPrime);
    }

    @Test
    public void shouldSortCollection() {
        //Given
        LOGGER.info("Test shouldSortCollection is working!");

        int[] array = {3, 1, 1, 5, 6, 4};

        // When
        Optional<int[]> ascendingSortArray = Optional.ofNullable(Segregation.ascendingSegregation());
        Optional<int[]> descendingSortArray = Optional.ofNullable(Segregation.descendingSegregation());

        // Then
        assertTrue(ascendingSortArray.isPresent());
        assertTrue(descendingSortArray.isPresent());
    }

    @Test
    public void shouldConvertStringToInteger() {
        //Given
        LOGGER.info("Test shouldConvertStringToInteger is working!");

        String stringNumber = "2555";

        // When
        Optional<Integer> integerNumber = Optional.ofNullable(StringToInteger.convertStringToInteger(stringNumber));

        // Then
        assertTrue(integerNumber.isPresent());
    }

    @Test
    public void shouldConvertDecimalToHex() {
        //Given
        LOGGER.info("Test shouldConvertDecimalToHex is working!");

        int decimal = 1;

        // When
        Optional<String> hex = Optional.ofNullable(DecimalToHex.toHex(decimal));
        Optional<String> toHexString = Optional.of(Integer.toHexString(decimal));

        // Then
        assertTrue(hex.isPresent());
    }
}
