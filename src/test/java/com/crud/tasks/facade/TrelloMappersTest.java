package com.crud.tasks.facade;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrelloMappersTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @BeforeEach
    void setUp() {
        LOGGER.info("Start!");
    }

    @AfterEach
    void tearDown() {
        LOGGER.info("Stop!");
    }

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    @DisplayName("Test shouldWorkAllTrelloMappers")
    public void shouldWorkAllTrelloMappers() {
        // Given
        LOGGER.info("Test shouldWorkAllTrelloMappers is working!");

        TrelloListDto trelloListDto = new TrelloListDto("1L", "ListDto", true);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1L", "BoardDto", List.of(trelloListDto));
        TrelloCard trelloCard = new TrelloCard("Card", "New card", "2", "1L");
        TrelloCardDto trelloCardDto = new TrelloCardDto("CardDto", "New cardDto", "2", "1L");

        // When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(List.of(trelloBoardDto));
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        List<TrelloList> trelloLists = trelloMapper.mapToList(List.of(trelloListDto));
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        TrelloCard trelloCardTest = trelloMapper.mapToCard(trelloCardDto);
        TrelloCardDto trelloCardDtoTest = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals(1, trelloBoardList.size());
        assertEquals("BoardDto", trelloBoardList.get(0).getName());

        assertEquals(1, trelloBoardDtoList.size());
        assertEquals("BoardDto", trelloBoardDtoList.stream().iterator().next().getName());

        assertEquals(1, trelloLists.size());
        assertEquals("ListDto", trelloLists.get(0).getName());

        assertEquals(1, trelloListDtos.size());
        assertEquals("ListDto", trelloListDtos.get(0).getName());

        assertEquals("CardDto", trelloCardTest.getName());

        assertEquals("Card", trelloCardDtoTest.getName());
    }
}
