package com.example.bingo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Entity
@Data
@Table(name = "Game")
public class Game {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
    private static final Random RANDOM = new Random();
    @Id
    private String id;
    private String pool;
    private int roll_num;

    public static String generateID() {
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            idBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return idBuilder.toString();
    }

    public void generatePool() {
        StringBuilder stringBuilder = new StringBuilder();

        // Append numbers from 1 to 75 separated by commas
        for (int i = 1; i <= 75; i++) {
            stringBuilder.append(i);
            if (i < 75) {
                stringBuilder.append(",");
            }
        }

        pool = stringBuilder.toString();
    }

    public Game() {
        roll_num = 0;
        generatePool();
    }

    public List<Integer> getPoolAsList() {
        List<Integer> numbersList = Arrays.stream(pool.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return numbersList;
    }

    public void roll() {
        List<Integer> poolList = getPoolAsList();
        if (roll_num < 75) {
            Random random = new Random();
            poolList.remove(random.nextInt(poolList.size()));

            StringBuilder stringBuilder = new StringBuilder();
            for (Integer number : poolList) {
                stringBuilder.append(number).append(",");
            }

            // Remove the last comma if it exists
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            pool = stringBuilder.toString();
            roll_num++;
        }
    }

}
