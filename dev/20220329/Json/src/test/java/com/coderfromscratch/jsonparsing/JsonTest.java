package com.coderfromscratch.jsonparsing;

import com.coderfromscratch.jsonparsing.pojo.AuthorPOJO;
import com.coderfromscratch.jsonparsing.pojo.BookPOJO;
import com.coderfromscratch.jsonparsing.pojo.DayPOJO;
import com.coderfromscratch.jsonparsing.pojo.SimpleTestCaseJsonPOJO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {
    private String simpleTestCaseJsonSource = "{\n" +
            "  \"title\":\"Coder from Scratch\",\n" +
            "  \"author\":\"David\"\n"+
            "}";

    private String dayScenario1 = "{\n" +
            " \"date\": \"2022-03-27\",\n" +
            "  \"name\": \"today\"\n " +
            "}";

    private String authorBookScenario = "{ \n" +
            " \"authorName\": \"David\", \n" +
            " \"books\": [\n" +
            "   {\n" +
            "     \"title\":\"title1\",\n" +
            "     \"inPrint\": true,\n" +
            "     \"publishDate\": \"2022-03-27\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\": \"title2\",\n" +
            "      \"inPrint\": false,\n" +
            "      \"publishDate\": \"2022-01-01\"\n" +
                "}\n" +
            "  ]\n" +
            "}";

    @Test
    void parse() throws IOException {

        JsonNode node = Json.parse(simpleTestCaseJsonSource);
        assertEquals(node.get("title").asText(), "Coder from Scratch");
    }

    @Test
    void fromJson() throws IOException {

        JsonNode node = Json.parse(simpleTestCaseJsonSource);
        SimpleTestCaseJsonPOJO pojo = Json.fromJson(node, SimpleTestCaseJsonPOJO.class);

        assertEquals(pojo.getTitle(), "Coder from Scratch");

    }
    @Test
    void toJson (){

        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("testing 123");

        JsonNode node = Json.toJson(pojo);

        assertEquals(node.get("title").asText(), "testing 123");


    }

    @Test
    void stingify() throws JsonProcessingException {

        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("testing 123");

        JsonNode node = Json.toJson(pojo);

        System.out.println(Json.stingify(node));
        System.out.println(Json.prettyPrint(node));


    }
    @Test
    void dayTestScenario1() throws IOException {

        JsonNode node = Json.parse(dayScenario1);
        DayPOJO pojo = Json.fromJson(node, DayPOJO.class);

        assertEquals("2022-03-27", pojo.getDate().toString());


    }
    @Test
    void setAuthorBookScenario() throws IOException {

        JsonNode node = Json.parse(authorBookScenario);
        AuthorPOJO pojo = Json.fromJson(node, AuthorPOJO.class);
        System.out.println("Author : " + pojo.getAuthorName());
        for (BookPOJO bp : pojo.getBooks()) {
            System.out.println("Book : " + bp.getTitle());
            System.out.println("Is in Print? " + bp.isInPrint());
            System.out.println("Date: " + bp.getPublishDate());
        }


    }
    @Test
    void write(){
        File file = new File("book.json");
        ObjectMapper mapper = new ObjectMapper();

        try{
            JsonGenerator g = mapper.getFactory().createGenerator(new FileOutputStream(file));
            ArrayList<BookPOJO> books = new ArrayList<>();
            books.add(new BookPOJO("title1", true, "2022-33-33"));
            books.add( new BookPOJO("title2", false, "2022-01-01"));
//            mapper.writeValue(Paths.get("book.json").toFile(), books);

            g.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void read(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> map = mapper.readValue(Paths.get("book.json").toFile(), Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}