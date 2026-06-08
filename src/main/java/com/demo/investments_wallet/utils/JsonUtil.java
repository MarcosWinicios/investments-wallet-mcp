package com.demo.investments_wallet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public final class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param object the object to be serialized
     * @return the generated JSON string
     * @throws IllegalArgumentException if serialization fails
     */
    public static String toJson(Object object) {
        try {
            log.debug("JsonUtil[toJson] - input: {}", object.toString());
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil[toJson] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to serialize object to JSON",
                    e
            );
        }
    }

    /**
     * Converts an object to a formatted JSON string representation.
     *
     * @param object the object to be serialized
     * @return the generated formatted JSON string
     * @throws IllegalArgumentException if serialization fails
     */
    public static String toPrettyJson(Object object) {
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil[toPrettyJson] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to serialize object to formatted JSON",
                    e
            );
        }
    }

    /**
     * Deserializes a JSON string into an instance of the specified class.
     *
     * @param json the JSON content
     * @param clazz the target class
     * @param <T> the target type
     * @return the deserialized object
     * @throws IllegalArgumentException if deserialization fails
     */
    public static  <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil[fromJson] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to deserialize JSON to object",
                    e
            );
        }
    }

    /**
     * Deserializes a JSON string into a generic type.
     *
     * @param json the JSON content
     * @param typeReference the target type reference
     * @param <T> the target type
     * @return the deserialized object
     * @throws IllegalArgumentException if deserialization fails
     */
    public static  <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil[fromJson] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to deserialize JSON to generic type",
                    e
            );
        }
    }

    /**
     * Converts an object into a JsonNode tree.
     *
     * @param object the source object
     * @return the generated JsonNode
     */
    public static JsonNode toTree(Object object) {
        return objectMapper.valueToTree(object);
    }

    /**
     * Parses a JSON string into a JsonNode tree.
     *
     * @param json the JSON content
     * @return the parsed JsonNode
     * @throws IllegalArgumentException if parsing fails
     */
    public static JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil[readTree] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to parse JSON into JsonNode",
                    e
            );
        }
    }

    /**
     * Reads a JSON file and converts its content into the specified type.
     *
     * @param path the JSON file path
     * @param clazz the target class
     * @param <T> the target type
     * @return the deserialized object
     * @throws IllegalArgumentException if reading or deserialization fails
     */
    public static  <T> T readFile(Path path, Class<T> clazz) {
        try {
            return objectMapper.readValue(path.toFile(), clazz);
        } catch (IOException e) {
            log.error("JsonUtil[readFile] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to read JSON file",
                    e
            );
        }
    }

    /**
     * Writes an object as a formatted JSON file.
     *
     * @param path the destination file path
     * @param object the object to be serialized
     * @throws IllegalArgumentException if writing fails
     */
    public static void writeFile(Path path, Object object) {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), object);
        } catch (IOException e) {
            log.error("JsonUtil[writeFile] - error {}", e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to write JSON file",
                    e
            );
        }
    }
}