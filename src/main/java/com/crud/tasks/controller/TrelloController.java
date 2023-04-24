package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
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
    private final TrelloService trelloService;

    //change @GetMapping("boardsOne")
    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloService.fetchTrelloBoards());
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
        return ResponseEntity.ok(trelloService.createTrelloCard(trelloCardDto));
    }

    //change @PostMapping("cardWithParameters")
    @PostMapping("newWithParameters")
    public ResponseEntity<CreatedTrelloCard> createTrelloCardWithAdditionalParameters(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDto));
    }
}
