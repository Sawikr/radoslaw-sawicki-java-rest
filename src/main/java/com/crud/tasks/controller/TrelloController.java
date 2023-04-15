package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloClient.getTrelloBoardsOne());
    }

    @GetMapping("boardsTwo")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoardsIfPresentKodilla() {
        return ResponseEntity.ok(trelloClient.getTrelloBoardsTwo());
        //earlySolutionMethod(trelloBoards);
    }

    private void earlySolutionMethod(List<TrelloBoardDto> trelloBoards) {
        trelloBoards = trelloClient.getTrelloBoardsTwo();
        trelloBoards.stream()
                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                .filter(p -> p.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
        });
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCard> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDto));
    }

    @PostMapping("cardsWithParameters")
    public ResponseEntity<CreatedTrelloCard> createTrelloCardWithAdditionalParameters(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDto));
    }
}
