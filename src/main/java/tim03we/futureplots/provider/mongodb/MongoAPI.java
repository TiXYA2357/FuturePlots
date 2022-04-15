package tim03we.futureplots.provider.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.config.YamlConfig;

public class MongoAPI {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static String collection = "plots";

    public static void load() {
        YamlConfig config = new YamlConfig(FuturePlots.getInstance().getDataFolder() + "/config.yml");
        MongoClientURI mongoClientURI = new MongoClientURI(config.getString("mongodb.uri"));
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(config.getString(config.getString("mongodb.database")));
    }

    public static void change(Document searchDocument, String searchValue, Object newValue) {
        Document found = mongoDatabase.getCollection(collection).find(searchDocument).first();
        Document document1 = new Document(searchValue, newValue);
        Document document2 = new Document("$set", document1);
        mongoDatabase.getCollection(collection).updateOne(found, document2);
    }

    public static FindIterable<Document> find() {
        return mongoDatabase.getCollection(collection).find();
    }

    public static Document find(Document document) {
        return mongoDatabase.getCollection(collection).find(document).first();
    }

    public static void insert(Document document) {
        mongoDatabase.getCollection(collection).insertOne(document);
    }

    public static void delete(Document document) {
        mongoDatabase.getCollection(collection).deleteOne(document);
    }
}
