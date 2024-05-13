package com.example.bingo.controllers;

import com.example.bingo.models.Game;
import com.example.bingo.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam String gamecode, Model model) {
        model.addAttribute("gamecode", gamecode);
        Game game = gameService.findById(gamecode);
        if (game != null) {
            model.addAttribute("pool", game.getPoolAsList());
        }
        return "dashboard";
    }

    @PostMapping("/newGame")
    public String newGame() {
        Game game = gameService.newGame();
        return "redirect:/dashboard?gamecode=" + game.getId();
    }

    @PostMapping("/roll")
    public String roll(@RequestParam String gamecode) {
        Game game = gameService.rollGame(gamecode);

        return "redirect:/dashboard?gamecode=" + game.getId();
    }

}
