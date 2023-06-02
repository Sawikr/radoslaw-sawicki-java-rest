package com.crud.tasks.controller;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor

public class TrelloController {

    private final TrelloClient trelloClient;
    private final TrelloFacade trelloFacade;
    //private final TrelloService trelloService;


    //change @GetMapping("boardsOne")
    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloFacade.fetchTrelloBoards());
        //return ResponseEntity.ok(trelloService.fetchTrelloBoards());
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
    public ResponseEntity<CreatedTrelloCardDto> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloFacade.createCard(trelloCardDto));
        //return ResponseEntity.ok(trelloService.createTrelloCard(trelloCardDto));
    }

    //change @PostMapping("cardWithParameters")
    @PostMapping("newWithParameters")
    public ResponseEntity<CreatedTrelloCardDto> createTrelloCardWithAdditionalParameters(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDto));
    }
}
