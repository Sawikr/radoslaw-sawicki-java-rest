package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("v1/trello")
@CrossOrigin("*")
@RequiredArgsConstructor

public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boardsOne")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());

        System.out.println("This board contains lists: ");
        trelloBoardDto.getLists().forEach(trelloList -> {
            System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
            });
        });
    }

    @GetMapping("boardsTwo")
    public void getTrelloBoardsIfPresentKodilla() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                .filter(p -> p.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
        });
    }

    @PostMapping("cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}

