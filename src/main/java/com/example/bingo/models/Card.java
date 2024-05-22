package com.example.bingo.models;

import com.example.bingo.converters.HashMapConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@Table(name = "Card")
@NoArgsConstructor
public class Card {
    @Id
    @Column(name = "playcard_token")
    private String playcardToken;
    @JsonIgnore
    @ManyToOne
    private Game game;
    @JsonIgnore
    private String cardJSON;
    @Convert(converter = HashMapConverter.class)
    private Map<Character, List<Integer>> card;

    public void generateNums() {
        Random random = new Random();
        List<Integer> pool = new ArrayList<Integer>();
        String bingo = "BINGO";
        card = new HashMap<>();

        for (int i = 1; i <= 75; i++) {
            pool.add(i);
        }

        for (char c: bingo.toCharArray()) {
            List<Integer> row = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                int randomNumberIndex = random.nextInt(pool.size());
                row.add(pool.remove(randomNumberIndex));
            }
            card.put(c, row);
        }

    }

    public static String generateID() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        Random RANDOM = new Random();
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            idBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return idBuilder.toString();
    }

    public Card(Game game) {
        generateNums();
        this.game = game;
    }

}
