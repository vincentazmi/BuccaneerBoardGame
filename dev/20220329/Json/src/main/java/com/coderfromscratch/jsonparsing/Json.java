package com.coderfromscratch.jsonparsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Serial;
import java.nio.file.Paths;

public class Json {

private static ObjectMapper objectMapper = getDefaultObjectMapper();

static ObjectMapper getDefaultObjectMapper(){
    ObjectMapper defaultObjectMapper = new ObjectMapper();
    defaultObjectMapper.registerModule(new JavaTimeModule());
    defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    return defaultObjectMapper;
}
//converts string into Json node
public static JsonNode parse(String src) throws IOException {
    return objectMapper.readTree(src);
}
//parses a JsonNode into an object that represents that JsonFile
public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
    return objectMapper.treeToValue(node, clazz);
}

public static JsonNode toJson (Object a){
    return objectMapper.valueToTree(a);
}

//returns a string
public static String stingify(JsonNode node) throws JsonProcessingException {
    return generateString(node, false);
}
//returns a string of node in pretty format
public static String prettyPrint(JsonNode node) throws JsonProcessingException {
    return generateString(node, true);
}
//returns a string oif the Json node
private static String generateString (JsonNode node, boolean pretty) throws JsonProcessingException {
    ObjectWriter objectWriter = objectMapper.writer();
    if (pretty){
        objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
    }
    return objectWriter.writeValueAsString(node);
}

}
