package com.example.bingo.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<Character, List<Integer>>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(HashMapConverter.class);
    @Override
    public String convertToDatabaseColumn(Map<Character, List<Integer>> card) {

        String cardJson = null;
        try {
            cardJson = objectMapper.writeValueAsString(card);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return cardJson;
    }

    @Override
    public Map<Character, List<Integer>> convertToEntityAttribute(String cardJSON) {

        Map<Character, List<Integer>> card= null;
        try {
            card = objectMapper.readValue(cardJSON,
                    new TypeReference<HashMap<Character, List<Integer>>>() {});
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return card;
    }
}
