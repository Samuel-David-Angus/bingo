package com.example.bingo.services;

import com.example.bingo.models.Game;
import com.example.bingo.repos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    public Game newGame() {
        String id;
        do {
            id = Game.generateID();
        }
        while (!gameRepository.findById(id).isEmpty());

        Game game = new Game();
        game.setId(id);
        System.out.println(game.getId());
        return gameRepository.save(game);
    }
    
    public Game rollGame(String id) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game != null) {
            game.roll();
            return gameRepository.save(game);
        }
        return null;
    }

    public Game findById(String id) {
        return gameRepository.findById(id).orElse(null);
    }
}
