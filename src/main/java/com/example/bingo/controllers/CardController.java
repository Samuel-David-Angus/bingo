package com.example.bingo.controllers;

import com.example.bingo.models.Card;
import com.example.bingo.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/getCard")
    public Card getCard(@RequestParam String gamecode) throws ChangeSetPersister.NotFoundException {
        return cardService.getCard(gamecode);
    }

    @GetMapping("/checkWin")
    public int checkCard(@RequestParam String playcardtoken) {
        return cardService.checkWin(playcardtoken);
    }
}
