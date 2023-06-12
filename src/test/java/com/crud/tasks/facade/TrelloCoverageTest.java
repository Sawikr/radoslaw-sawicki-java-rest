package com.crud.tasks.facade;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloCoverageTest {

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
    private TrelloConfig trelloConfig;

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @InjectMocks
    private SimpleEmailService simpleEmailService2;

    @Mock
    private SimpleMailMessage simpleMailMessage;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private TrelloDto trelloDto;

    @Test
    @DisplayName("Test shouldNotWorkTrelloConfig")
    public void shouldNotWorkTrelloConfig() {
        // Given & When
        LOGGER.info("Test shouldNotWorkTrelloConfig is working!");

        Optional<String> adminMailTest = Optional.ofNullable(adminConfig.getAdminMail());
        Optional<String> trelloApiEndpointTest = Optional.ofNullable(trelloConfig.getTrelloApiEndpoint());
        Optional<String> trelloAppKeyTest = Optional.ofNullable(trelloConfig.getTrelloAppKey());
        Optional<String> trelloTokenTest = Optional.ofNullable(trelloConfig.getTrelloToken());
        Optional<String> trelloUserNameTest = Optional.ofNullable(trelloConfig.getTrelloUserName());

        // Then
        assertFalse(adminMailTest.isPresent());
        assertFalse(trelloApiEndpointTest.isPresent());
        assertFalse(trelloAppKeyTest.isPresent());
        assertFalse(trelloTokenTest.isPresent());
        assertFalse(trelloUserNameTest.isPresent());
    }

    @Test
    @DisplayName("Test shouldWorkTrelloService")
    public void shouldWorkTrelloService() {
        // Given
        LOGGER.info("Test shouldWorkTrelloService is working!");

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "BoardTest", null);
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "New card", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "TrelloCard", "https://...", null);
        Mail mail = new Mail("sawikr10@gmail.com",
                "Tasks: New Trello card",
                "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                "sawikr@op.pl");

        when(trelloClient.getTrelloBoardsOne()).thenReturn(List.of(trelloBoardDto));
        when(adminConfig.getAdminMail()).thenReturn("sawikr@op.pl");
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(simpleMailMessage.getSubject()).thenReturn("Radek");

        // When
        List<TrelloBoardDto> trelloClientList = trelloClient.getTrelloBoardsOne();
        List<TrelloBoardDto> trelloBoardDtoList = trelloService.fetchTrelloBoards();
        CreatedTrelloCardDto createdTrelloCardDtoTest = trelloService.createTrelloCard(trelloCardDto);

        // Then
        assertTrue(trelloBoardDtoList.stream().map(trelloBoardDtoTest -> trelloClientList.get(0)).findFirst().isPresent());
        assertEquals("TrelloCard", createdTrelloCardDtoTest.getName());
        assertTrue(Objects.requireNonNull(simpleMailMessage.getSubject()).contains("Radek"));
    }

    @Test
    @DisplayName("Test shouldWorkSimpleEmailService")
    public void shouldWorkSimpleEmailService() {
        // Given
        LOGGER.info("Test shouldWorkSimpleEmailService is working!");

        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "New card", "1", "1");
        Mail mail = new Mail("sawikr10@gmail.com",
                "Tasks: New Trello card",
                "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                "sawikr@op.pl");

        // When
        simpleEmailService2.send(mail);

        // Then
        assertTrue(Optional.ofNullable(mail.getMailTo()).isPresent());
        assertTrue(Optional.ofNullable(mail.getSubject()).isPresent());
        assertTrue(Optional.ofNullable(mail.getMessage()).isPresent());
        assertTrue(Optional.ofNullable(mail.getToCc()).isPresent());
    }

    @Test
    @DisplayName("Test shouldMapToTask")
    public void shouldMapToTask() {
        //Given
        LOGGER.info("Test shouldMapToTask is working!");

        TaskDto taskDto = new TaskDto(1L, "Test", "Test task");
        TaskMapper taskMapper = new TaskMapper();

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, task.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Test task", task.getContent());
        assertNotEquals(2L, task.getId());
        assertNotEquals("test", task.getTitle());
        assertNotEquals("Test Task", task.getContent());
    }

    @Test
    @DisplayName("Test shouldMapToTaskDto")
    public void shouldMapToTaskDto() {
        // Given
        LOGGER.info("Test shouldMapToTaskDto is working!");

        Task task = new Task(1L, "Test", "Test task");
        TaskMapper taskMapper = new TaskMapper();

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("Test", taskDto.getTitle());
        assertEquals("Test task", taskDto.getContent());
        assertNotEquals(2L, taskDto.getId());
        assertNotEquals("test", taskDto.getTitle());
        assertNotEquals("test Task", taskDto.getContent());
    }

    @Test
    @DisplayName("Test shouldMapToTaskDtoList")
    public void shouldMapToTaskDtoList() {
        //Given
        LOGGER.info("Test shouldMapToTaskDtoList is working!");

        List<Task> taskList = List.of(new Task(1L, "Test", "Test task"));
        TaskMapper taskMapper = new TaskMapper();

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(1, taskDtoList.size());
        assertNotEquals(2, taskDtoList.size());
        assertEquals(1L, taskDtoList.get(0).getId());
        assertEquals("Test", taskDtoList.get(0).getTitle());
        assertEquals("Test task", taskDtoList.get(0).getContent());
    }


    @Test
    @DisplayName("Test shouldMapToBoards")
    public void shouldMapToBoards() {
        //Given
        LOGGER.info("Test shouldMapToBoards is working!");

        List<TrelloBoardDto> trelloBoardDto = List.of(new TrelloBoardDto("1", "test", new ArrayList<>()));
        TrelloMapper trelloMapper = new TrelloMapper();

        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDto);

        //Then
        assertEquals(1, result.size());
        assertNotEquals(2, result.size());
    }

    @Test
    @DisplayName("Test shouldMapToBoardsDto")
    public void shouldMapToBoardsDto() {
        // Given
        LOGGER.info("Test shouldMapToBoardsDto is working!");

        List<TrelloBoard> trelloBoard = List.of(new TrelloBoard("1", "test", new ArrayList<>()));
        TrelloMapper trelloMapper = new TrelloMapper();
        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(trelloBoard);
        //Then
        assertEquals(1, result.size());
        assertNotEquals(2, result.size());
    }

    @Test
    @DisplayName("Test shouldMapToList")
    public void shouldMapToList() {
        //Given
        LOGGER.info("Test shouldMapToList is working!");

        List<TrelloListDto> TrelloListDto = List.of(new TrelloListDto("id", "test", true));
        TrelloMapper trelloMapper = new TrelloMapper();

        //When
        List<TrelloList> result = trelloMapper.mapToList(TrelloListDto);

        //Then
        assertEquals(1, result.size());
        assertNotEquals(2, result.size());
    }

    @Test
    @DisplayName("Test shouldMapToListDto")
    public void shouldMapToListDto() {
        //Given
        LOGGER.info("Test shouldMapToListDto is working!");

        List<TrelloList> trelloLists = List.of(new TrelloList("1", "test", true));
        TrelloMapper trelloMapper = new TrelloMapper();

        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(1, result.size());
        assertNotEquals(2, result.size());
    }

    @Test
    @DisplayName("Test shouldWorkTrelloDto")
    public void shouldWorkTrelloDto() {
        // Given
        LOGGER.info("Test shouldWorkTrelloDto is working!");

        trelloDto.setBoard(1);
        trelloDto.setCard(1);

        // When
        Integer board = trelloDto.getBoard();
        Integer card = trelloDto.getCard();

        // Then
        assertEquals(1, board);
        assertEquals(1, card);
    }
}
