package database;

import bot.player.consumables.Coins;
import bot.player.hero.attributes.Statistics;
import bot.player.utility.Activity;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import config.Config;
import bot.player.hero.experience.Level;
import bot.player.Player;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;

import java.util.*;

public class DatabaseManager {
    private static final MongoClient client = MongoClients.create(Config.get("MONGOURI"));
    private DatabaseManager() {}

    //public member operations
    public static List<Player> getMembersList(String databaseName, String collectionName){
        List<Player> players = new ArrayList<>();
        Iterator documents = getDatabase(databaseName).getCollection(collectionName).find().iterator();

        while(documents.hasNext()){
            players.add(buildMemberFromDocument((Document)documents.next()));
        }

        return players;
    }

    public static Player getMember(String databaseName, String collectionName, String memberID){
        MongoCollection<Document> collection = getCollection(databaseName, collectionName);
        Document document = getDocument(collection, memberID);

        return buildMemberFromDocument(document);
    }

    public static void updateMember(String databaseName, String collectionName, Player player){
        MongoCollection<Document> collection = getDatabase(databaseName).getCollection(collectionName);

        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("level", player.getLevel()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("xp", player.getXp()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("coins", player.getCoins()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("messages", player.getMessages()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("voiceActivity", player.getVoiceTime()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("grabbedDaily", player.isDailyGrabbed()));
        collection.updateOne(Filters.eq("memberID", player.getMemberID()), Updates.set("stats", buildDBListFromStatistics(player.getStatistics())));
    }

    public static void removeMember(String databaseName, String collectionName, String memberID){
        MongoCollection<Document> collection = getDatabase(databaseName).getCollection(collectionName);

        collection.deleteOne(Filters.eq("memberID", memberID));
    }

    private static BasicDBList buildDBListFromStatistics(Statistics stats){
        BasicDBList statsList = new BasicDBList();

        statsList.add(new BasicDBObject("int", stats.getIntelligence()));
        statsList.add(new BasicDBObject("str", stats.getStrength()));
        statsList.add(new BasicDBObject("dex", stats.getDexterity()));

        return statsList;
    }

    //private db operations

    private static MongoDatabase getDatabase(String databaseName){
        return client.getDatabase(databaseName);
    }

    //collections operations
    private static MongoCollection<Document> getCollection(String databaseName, String collectionName){
        if(!collectionExists(databaseName, collectionName)){
            createNewCollection(databaseName, collectionName);
        }

        return getDatabase(databaseName).getCollection(collectionName);
    }

    private static void createNewCollection(String databaseName, String collectionName){
        MongoDatabase database = getDatabase(databaseName);

        database.createCollection(collectionName);
    }

    private static boolean collectionExists(String databaseName, String collectionName){
        MongoDatabase database = getDatabase(databaseName);
        Iterator names = database.listCollectionNames().iterator();

        while(names.hasNext()){
            if(names.next().equals(collectionName)){
                return true;
            }
        }

        return false;
    }

    //documents operations
    private static void createDefaultDocument(MongoCollection<Document> collection, String memberID){
        Document document = new Document();
        BasicDBList stats = new BasicDBList();

        stats.add(new BasicDBObject("int", 10));
        stats.add(new BasicDBObject("str", 10));
        stats.add(new BasicDBObject("dex", 10));

        document.append("memberID", memberID);
        document.append("xp", PlayerDefaults.XP_VALUE);
        document.append("level", PlayerDefaults.LEVEL_VALUE);
        document.append("coins", PlayerDefaults.COINS_VALUE);
        document.append("messages", PlayerDefaults.MESSAGES_VALUE);
        document.append("voiceActivity", PlayerDefaults.VOICE_ACTIVITY_VALUE);
        document.append("grabbedDaily", false);
        document.append("stats", stats);

        collection.insertOne(document);
    }

    private static boolean documentExists(MongoCollection<Document> collection, String memberID){
        return collection.find(Filters.eq("memberID", memberID)).first() != null;
    }

    private static Document getDocument(MongoCollection<Document> collection, String memberID){
        if(!documentExists(collection, memberID)){
            createDefaultDocument(collection, memberID);
        }

        return collection.find(Filters.eq("memberID", memberID)).first();
    }

    private static Player buildMemberFromDocument(Document document){
        try{
            String memberID = (String)document.get("memberID");
            Statistics stats = getStatisticsFromArray((ArrayList<Object>)document.get("stats"));
            int level = (int)document.get("level");
            int xp = (int)document.get("xp");
            double coins = (double)document.get("coins");
            int messages = (int)document.get("messages");
            int voiceActivity = (int)document.get("voiceActivity");
            boolean grabbedDaily = (boolean)document.get("grabbedDaily");

            return new Player(memberID, stats, new Level(level, xp), new Coins(coins), new Activity(voiceActivity, messages, grabbedDaily));
        } catch(NullPointerException e){
            System.out.println("Error while fetching member data from document, possible null field in database");
            return new Player("", new Statistics(0, 0, 0), new Level(0, 0), new Coins(0), new Activity(0, 0, false));
        }
    }

    private static Statistics getStatisticsFromArray(List<Object> stats){
        Document intelligence = (Document) stats.get(0);
        Document strength = (Document) stats.get(1);
        Document dexterity = (Document) stats.get(2);

        return new Statistics((int)intelligence.get("int"), (int)strength.get("str"), (int)dexterity.get("dex"));
    }
}
