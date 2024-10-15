package com.mongodb.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    // Connection string to connect to the newly created MongoDB instance
    private static final String CONNECTION_URI = "mongodb://localhost:27017/testDB";
    
    public static MongoDatabase getDatabase() {
        try {
            // Establish the MongoDB client connection
            MongoClient client = MongoClients.create(CONNECTION_URI);
            MongoDatabase database = client.getDatabase("testDB");
            System.out.println("Connected to MongoDB successfully.");
            return database;
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
            return null;
        }
    }
}
