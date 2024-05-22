package com.example.bingo.services;

import com.example.bingo.models.Card;
import com.example.bingo.models.Game;
import com.example.bingo.repos.CardRepository;
import com.example.bingo.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
            card.setPlaycardToken(playcard_token);
            return cardRepository.save(card);
        } else {
            // Handle the case when the game is not found
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public int checkWin(String playcard_token) {
        Optional<Card> optionalCard = cardRepository.findByPlaycardToken(playcard_token);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            Optional<Game> optionalGame = gameRepository.findById(card.getGame().getId());
            if (optionalGame.isPresent()) {
                Game game = optionalGame.get();
                List<Integer> gamePool = game.getPoolAsList();
                Map<Character, List<Integer>> cardNums = card.getCard();
                boolean is_bingo = false;
                String bingo = "BINGO";
                int rowcount = 0, colcount = 0, dlcount = 0, drcount = 0;
                // check for rows, columns and diagonals
                for (int i = 0; i < 5; i++) {
                    //check row
                    for (int j = 0; j < 5; j++) {
                        if (!gamePool.contains(cardNums.get(bingo.charAt(i)).get(j))) {
                            rowcount++;
                        }
                    }
                    //check column
                    for (int j = 0; j < 5; j++) {
                        if (!gamePool.contains(cardNums.get(bingo.charAt(j)).get(i))) {
                            colcount++;
                        }
                    }
                    //check diagonals
                    if (!gamePool.contains(cardNums.get(bingo.charAt(i)).get(i))) {
                        drcount++;
                    }
                    if (!gamePool.contains(cardNums.get(bingo.charAt(4-i)).get(4-i))) {
                        dlcount++;
                    }
                    // check if any of row or column is true
                    if (rowcount >= 5 || colcount >= 5) {
                        is_bingo = true;
                        break;
                    }

                }

                //check if diagonals is true
                if (drcount >= 5 || dlcount >= 5) {
                    is_bingo = true;
                }

                if (is_bingo) {
                    return 1;
                } else {
                    return 0;
                }

            }
        }
        return -1;
    }
}
