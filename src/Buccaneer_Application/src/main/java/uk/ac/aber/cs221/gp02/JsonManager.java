package uk.ac.aber.cs221.gp02;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class deals with saving, loading and updating data from/to given JSON file
 *
 */
public class JsonManager {
    private final String filename = "players.json";
    private final JSONObject jsonData;


    /**
     * This constructor creates JSON Object from given file.
     *
     * @throws IOException .
     * @throws ParseException .
     */
    public JsonManager() throws IOException, ParseException {
        FileReader fr = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fr);
        jsonData = (JSONObject) obj;
    }

    /**
     * This method is called after any method that modify our data.
     * It writes modified JSON Object to JSON file.
     */
    private void updateJson() throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(jsonData.toJSONString());
        fw.flush();

    }

    /**
     * This method saves data
     * @throws IOException
     */
    public void saveData() throws IOException {
        savePlayers();
        savePorts();
        saveIslands();
        jsonData.put("turn", Game.getTurn());
        updateJson();
    }

    /**
     * This method saves Islands
     */
    private void saveIslands(){
        PirateIsland pirateIsland = Game.board.getPirateIsland();
        JSONObject jsonPirateIsland = new JSONObject();
        jsonPirateIsland.put("Treasures", saveTreasure(pirateIsland.getTreasures()));
        jsonPirateIsland.put("CrewCards", saveCrewCardsFromCard(pirateIsland.getCrewCards()));
        jsonData.put("PirateIsland", jsonPirateIsland);

        FlatIsland flatIsland = Game.board.getFlatIsland();
        JSONObject jsonFlatIsland = new JSONObject();
        jsonFlatIsland.put("Treasures", saveTreasure(flatIsland.getTreasures()));
        jsonFlatIsland.put("CrewCards", saveCrewCardsFromCard(flatIsland.getCrewCards()));
        jsonData.put("FlatIsland", jsonFlatIsland);

        TreasureIsland treasureIsland = Game.board.getTreasureIsland();
        JSONObject jsonTreasureIsland = new JSONObject();
        jsonTreasureIsland.put("Treasures", saveTreasure(treasureIsland.getTreasures()));
        jsonTreasureIsland.put("ChanceCards", saveChanceCardsFromCard(treasureIsland.getChanceCards()));
        jsonData.put("TreasureIsland", jsonTreasureIsland);
    }

    /**
     * This method saves players
     */
    private void savePlayers(){
        JSONArray jsonUsers = new JSONArray();
        for (Player p : Game.getPlayers()) {
            JSONObject player = new JSONObject();
            player.put("name", p.getName());
            player.put("XCor", p.getXCor());
            player.put("YCor", p.getYCor());
            player.put("Color", p.getColor());
            player.put("Direction", p.directionToString());
            player.put("HomePort", p.getHomePort().getName());
            player.put("Treasures", saveTreasure(p.getTreasures()));
            player.put("CrewCards", saveCrewCards(p.getCards()));
            jsonUsers.add(p.getPlayerOrder(), player);
        }
        jsonData.put("users", jsonUsers);
    }

    /**
     * This method saves ports
     */
    private void savePorts() {
        JSONArray jsonPorts = new JSONArray();
        Port[] ports = Game.board.getPorts();
        for (Port p : ports) {
            jsonPorts.add(savePort(p));
        }
        jsonData.put("ports", jsonPorts);

    }

    /**
     * It saves crew cards
     * @param crewCards list
     * @return json array
     */
    private JSONArray saveCrewCardsFromCard(List<Card> crewCards) {
        JSONArray jsonArray = new JSONArray();
        for (Card c : crewCards) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", ((CrewCard) (c)).getValue());
            jsonObject.put("color", ((CrewCard) (c)).getColor());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * It saves chance cards
     * @param chanceCards list
     * @return json array
     */
    private JSONArray saveChanceCardsFromCard(List<Card> chanceCards) {
        JSONArray jsonArray = new JSONArray();
        for (Card c : chanceCards) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Desc", ((ChanceCard) (c)).getDescription());
            jsonObject.put("Id", ((ChanceCard) (c)).getId());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * Save cards to the file
     * @param crewCards list
     * @return array
     */
    private JSONArray saveCrewCards(ArrayList<CrewCard> crewCards) {
        JSONArray jsonArray = new JSONArray();
        for (CrewCard c : crewCards) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", c.getValue());
            jsonObject.put("color", c.getColor());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * Save treasures to the file
     * @param treasures list
     * @return array
     */
    private JSONArray saveTreasure(ArrayList<Treasure> treasures) {
        JSONArray jsonArray = new JSONArray();
        for (Treasure t : treasures) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", t.typeToString());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * It saves port
     * @param port port
     * @return json object
     */
    private JSONObject savePort(Port port) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", port.getName());
        jsonObject.put("treasure", saveTreasure(port.getTreasure()));
        jsonObject.put("cards", saveCrewCards(port.getCards()));
        jsonObject.put("safeZone", saveTreasure(port.getSafeZone()));
        return jsonObject;
    }


    /**
     * This function loads data from file
     */
    public void load() {
        loadPlayers();
        long turn = (long) jsonData.get("turn");
        Game.setTurn((int) turn);

        loadPorts();
        loadIslands();


    }

    /**
     * It loads players
     */
    private void loadPlayers() {
        JSONArray users = (JSONArray) jsonData.get("users");
        ArrayList<Player> players = Game.getPlayers();
        for (int i = 0; i < 4; i++) {

            JSONObject user = (JSONObject) (users.get(i));

            String portName = (String) user.get("HomePort");

            Port p = determinePort(portName);


            int j = getPlayerIndex(p);

            long x = (long) (user.get("XCor"));
            players.get(j).setXCor((int) x);
            long y = (long) (user.get("YCor"));
            players.get(j).setYCor((int) y);

            long color = (long) user.get("Color");
            players.get(j).setColor((int) color);

            Direction dir = determineDirection((String) user.get("Direction"));
            players.get(j).setDirection(dir);

            String name = (String) user.get("name");
            players.get(j).setName(name);

            JSONArray cards = (JSONArray) user.get("CrewCards");
            players.get(j).setCards(getCrewCards(cards));

            players.get(j).calculateSteps();
            JSONArray treasures = (JSONArray) user.get("Treasures");
            players.get(j).setTreasures(getTreasures(treasures));

        }
    }

    /**
     * It loads ports
     */
    private void loadPorts() {
        JSONArray jsonPorts = (JSONArray) jsonData.get("ports");
        for (int i = 0; i < 6; i++){
            JSONObject p = (JSONObject) jsonPorts.get(i);

            JSONArray crewCards = (JSONArray) p.get("cards");
            Game.board.getPorts()[i].setCards(getCrewCards(crewCards));

            JSONArray treasure = (JSONArray) p.get("treasure");
            Game.board.getPorts()[i].setTreasures(getTreasures(treasure));

            if(i % 5 != 0){
                JSONArray safeZone = (JSONArray) p.get("safeZone");
                Game.board.getPorts()[i].setTreasures(getTreasures(safeZone));
            }
        }
    }

    /**
     * It loads islands
     */
    private void loadIslands(){
        JSONObject flatIsland = (JSONObject) jsonData.get("FlatIsland");

        JSONArray treasure = (JSONArray) flatIsland.get("Treasures");
        Game.board.getFlatIsland().setTreasures(getTreasures(treasure));

        JSONArray cards = (JSONArray) flatIsland.get("CrewCards");
        ArrayList<CrewCard> crewCards = getCrewCards(cards);
        ArrayList<Card> cardsForDeck = new ArrayList<>();

        for(CrewCard c : crewCards){
            cardsForDeck.add(c);
        }
        Game.board.getFlatIsland().setCrewCards(new Deck(cardsForDeck));

        //////
        JSONObject treasureIsland = (JSONObject) jsonData.get("TreasureIsland");

        treasure = (JSONArray) treasureIsland.get("Treasures");
        Game.board.getTreasureIsland().setTreasures(getTreasures(treasure));

        cards = (JSONArray) treasureIsland.get("ChanceCards");
        ArrayList<ChanceCard> chanceCards = getChanceCards(cards);
        cardsForDeck = new ArrayList<>();

        for(ChanceCard c : chanceCards){
            cardsForDeck.add(c);
        }
        Game.board.getFlatIsland().setCrewCards(new Deck(cardsForDeck));

        ///
        JSONObject pirateIsland = (JSONObject) jsonData.get("PirateIsland");

        treasure = (JSONArray) pirateIsland.get("Treasures");
        Game.board.getPirateIsland().setTreasures(getTreasures(treasure));

        cards = (JSONArray) pirateIsland.get("CrewCards");
        crewCards = getCrewCards(cards);
        cardsForDeck = new ArrayList<>();

        for(CrewCard c : crewCards){
            cardsForDeck.add(c);
        }
        Game.board.getFlatIsland().setCrewCards(new Deck(cardsForDeck));



    }

    /**
     * Load chance cards
     * @param chanceCards json array
     * @return list
     */
    private ArrayList<ChanceCard> getChanceCards(JSONArray chanceCards){
        ArrayList<ChanceCard> result = new ArrayList<>();
        for (Object o : chanceCards) {
            JSONObject card = (JSONObject) o;

            String desc = (String) card.get("Desc");
            long id = (long) card.get("Id");

            result.add(new ChanceCard((int) id, desc));
        }
        return result;
    }
    /**
     * Load crewCards
     * @param crewCards json array
     * @return list
     */
    private ArrayList<CrewCard> getCrewCards(JSONArray crewCards) {
        ArrayList<CrewCard> result = new ArrayList<>();
        for (Object o : crewCards) {
            JSONObject card = (JSONObject) o;
            long value = (long) card.get("value");
            long color = (long) card.get("color");

            result.add(new CrewCard((int) color, (int) value));
        }
        return result;
    }

    /**
     * Load treasures
     * @param treasures json array
     * @return list
     */
    private ArrayList<Treasure> getTreasures(JSONArray treasures) {
        ArrayList<Treasure> result = new ArrayList<>();
        for (Object o : treasures) {
            JSONObject treasure = (JSONObject) o;
            String type = (String) treasure.get("type");

            result.add(determineTreasure(type));
        }
        return result;
    }

    private Direction determineDirection(String direction) {
        switch (direction) {
            case "NORTH":
                return Direction.NORTH;
            case "EAST":
                return Direction.EAST;
            case "WEST":
                return Direction.WEST;
            case "SOUTH":
                return Direction.SOUTH;
            case "SOUTHEAST":
                return Direction.SOUTHEAST;
            case "SOUTHWEST":
                return Direction.SOUTHWEST;
            case "NORTHEAST":
                return Direction.NORTHEAST;
            case "NORTHWEST":
                return Direction.NORTHWEST;
            default:
                return Direction.NORTH;
        }
    }

    private Treasure determineTreasure(String treasureName) {
        Treasure t;
        switch (treasureName) {
            case "DIAMOND" -> t = new Treasure("Diamond", TreasureType.DIAMOND);
            case "RUBIN" -> t = new Treasure("Rubin", TreasureType.RUBIN);
            case "GOLD" -> t = new Treasure("Bar of gold", TreasureType.GOLD);
            case "PEARL" -> t = new Treasure("Pearl", TreasureType.PEARL);
            default -> t = new Treasure("Barrel of rum", TreasureType.BARREL);
        }
        return t;
    }

    private Port determinePort(String portName) {
        for (Port p : Game.board.getPorts()) {
            if (p.getName().equals(portName))
                return p;
        }
        return null;
    }

    private int getPlayerIndex(Port p) {
        for (int i = 0; i < 4; i++) {
            if (Game.getPlayers().get(i).getHomePort().equals(p))
                return i;
        }
        return 0;
    }
    /**
     * This method loads list of users from JSON file
     * @return list of users
     */
//    public ArrayList<User> getUsers() {
//        JSONArray jsonUsers = (JSONArray) jsonData.get("users");
//
//        for (Object u : jsonUsers) {
//            JSONObject jsonUser = (JSONObject) u;
//            String type = (String) jsonUser.get("type");
//            User user;
//
//            if (type.equals("Student")) {
//                user = new Student();
//            } else {
//                user = new Teacher();
//            }
//            user.load(jsonUser);
//            users.add(user);
//        }
//
//        return users;
//    }

    /**
     * This method loads list of modules from JSON file.
     * Language of questions must be provided.
     * @param language "eng" for english and "pl" for polish
     * @return list of modules
     */
//    public ArrayList<Module> getModules(String language) {
//        JSONArray jsonModules = (JSONArray) jsonData.get("modules");
//
//        for (Object m : jsonModules) {
//            JSONObject jsonModule = (JSONObject) m;
//            Module module = new Module();
//            module.load(jsonModule, language);
//            modules.add(module);
//        }
//        return modules;
//    }

//    /**
//     * This method returns JSON Array of banks of module of given ID
//     * @param moduleId is ID of the module
//     * @return jsonArray of banks associated with module of given ID
//     */
//    private JSONArray searchForBanks(String moduleId) {
//        JSONArray jsonModules = (JSONArray) jsonData.get("modules");
//        JSONObject jsonModule = null;
//        for (Object m : jsonModules)
//            if (((JSONObject) m).get("id").equals(moduleId)) {
//                jsonModule = (JSONObject) m;
//                break;
//            }
//        return (JSONArray) jsonModule.get("banks");
//    }
//
//    /**
//     * This method returns JSON Array of questions of bank of given ID
//     * @param banksId is ID of the bank
//     * @param jsonBanks is JSON Array of banks
//     * @return
//     */
//    private JSONArray searchForQuestions(String banksId, JSONArray jsonBanks) {
//        JSONObject jsonQuestion = null;
//        for (Object m : jsonBanks)
//            if (((JSONObject) m).get("id").equals(banksId))
//                jsonQuestion = (JSONObject) m;
//        return (JSONArray) jsonQuestion.get("questions");
//    }

//    /**
//     * This method allows adding new user to JSON file
//     * @param newUser is either Teacher or Student
//     * @throws IOException
//     */
//    public void addUser(User newUser) throws IOException {
//        JSONArray jsonUsers = (JSONArray) jsonData.get("users");
//        JSONObject jsonUser = new JSONObject();
//        newUser.save(jsonUser);
//        jsonUsers.add(jsonUser);
//        updateJson();
//    }
//
//    /**
//     * This method allows updating user's stats
//     * @param user is either Teacher or Student
//     * @throws IOException
//     */
//    public void updateUser(User user) throws IOException {
//        JSONArray jsonUsers = (JSONArray) jsonData.get("users");
//        JSONObject jsonUser;
//        for (Object u : jsonUsers) {
//            jsonUser = ((JSONObject) u);
//            if (jsonUser.get("username").equals(user.getUsername())) {
//                if (user.getClass() == Student.class) {
//                    jsonUser.replace("takenQuestions", ((Student) user).getTakenQuestions());
//                    jsonUser.replace("correctAnswers", ((Student) user).getCorrectAnswers());
//                    jsonUser.replace("totalTime", ((Student) user).getTotalTime().getValue());
//
//                } else {
//                    jsonUser.replace("createdQuestions", ((Teacher) user).getCreatedQuestions());
//                }
//                break;
//            }
//        }
//        updateJson();
//    }
//
//    /**
//     * This method allows adding new bank to existing module.
//     * @param newBank is bank object to be added to JSON file
//     * @param moduleId is ID of module associated with new bank
//     * @throws IOException
//     */
//    public void addBank(Bank newBank, String moduleId) throws IOException {
//        JSONArray jsonBanks = searchForBanks(moduleId);
//        JSONObject jsonBank = new JSONObject();
//        newBank.save(jsonBank);
//        jsonBanks.add(jsonBank);
//        updateJson();
//    }
//
//    /**
//     * This method allows deleting bank from the module.
//     * @param bankId is ID of bank to be removed
//     * @param moduleId is ID of module associated with bank
//     * @throws IOException
//     */
//    public void deleteBank(String bankId, String moduleId) throws IOException {
//        JSONArray jsonBanks = searchForBanks(moduleId);
//        int i = 0;
//        for (Object b : jsonBanks) {
//            JSONObject bank = (JSONObject) b;
//            if (bank.get("id").equals(bankId)) {
//                jsonBanks.remove(i);
//                break;
//            }
//            i++;
//        }
//        updateJson();
//    }
//
//    /**
//     * This method allows adding new question to existing bank.
//     * @param newQuestionEng is question object in english language
//     * @param newQuestionPl is question object in polish language
//     * @param moduleId is ID of module which associated with new question
//     * @param bankId is ID of bank which associated with new question
//     * @throws IOException
//     */
//    public void addQuestion(Question newQuestionEng, Question newQuestionPl, String moduleId, String bankId) throws IOException {
//        JSONArray jsonBanks = searchForBanks(moduleId);
//        JSONArray jsonQuestions = searchForQuestions(bankId, jsonBanks);
//        JSONObject jsonQuestion = new JSONObject();
//        if (newQuestionEng.getClass() == SingleChoiceQuestion.class)
//            jsonQuestion.put("type", "SingleChoice");
//        else
//            jsonQuestion.put("type", "FillBlank");
//
//        JSONObject question = new JSONObject();
//        newQuestionEng.save(question);
//        jsonQuestion.put("eng", question);
//
//        question = new JSONObject();
//        newQuestionPl.save(question);
//        jsonQuestion.put("pl", question);
//
//        jsonQuestions.add(jsonQuestion);
//        updateJson();
//    }
//
//    /**
//     * This method allows deleting question from bank.
//     * @param questionId is ID of question to be deleted
//     * @param moduleId is ID of module which associated with new question
//     * @param bankId is ID of bank which associated with new question
//     * @throws IOException
//     */
//    public void deleteQuestion(int questionId, String moduleId, String bankId) throws IOException {
//        JSONArray jsonBanks = searchForBanks(moduleId);
//        JSONArray jsonQuestions = searchForQuestions(bankId, jsonBanks);
//        jsonQuestions.remove(questionId);
//        updateJson();
//    }

}
