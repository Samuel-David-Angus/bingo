package com.example.bingo.services;

import com.example.bingo.models.Card;
import com.example.bingo.models.Game;
import com.example.bingo.repos.CardRepository;
import com.example.bingo.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private GameRepository gameRepository;

    public Card getCard(String gamecode) throws ChangeSetPersister.NotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(gamecode);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            Card card = new Card(game);
            String playcard_token;
            do {
                playcard_token = Card.generateID();
            }
            while (!cardRepository.findByPlaycardToken(playcard_token).isEmpty());
            card.setPlaycard_token(playcard_token);
            return card;
        } else {
            // Handle the case when the game is not found
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public int checkWin(String playcard_token) {
        return 1;
    }
}
