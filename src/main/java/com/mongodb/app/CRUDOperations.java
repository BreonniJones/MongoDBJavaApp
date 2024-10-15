package com.mongodb.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.Date;
import java.util.Scanner;

public class CRUDOperations {

    private MongoCollection<Document> collection;

    // Constructor to initialize the collection
    public CRUDOperations(MongoDatabase db) {
        this.collection = db.getCollection("user_collection");
    }

    // Create a new user and insert it into the collection
    public void createUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateName);

        System.out.print("Enter email: ");
        String email = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateEmail);

        System.out.print("Enter phone: ");
        String phone = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validatePhone);

        System.out.print("Enter address: ");
        String address = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateAddress);

        // Create the user document
        Document user = new Document("name", name)
            .append("email", email)
            .append("phone", phone)
            .append("address", address)
            .append("created_at", new Date());

        // Insert the new document into the collection
        collection.insertOne(user);
        System.out.println("User created successfully.");
    }

    // Read users and display them in a table-like format
    public void readUsers() {
        // Print a header for the table
        System.out.printf("%-15s %-30s %-15s %-30s %s%n", "Name", "Email", "Phone", "Address", "Created At");
        System.out.println("-------------------------------------------------------------------------------------------");

        // Iterate over the users in the collection and print each in the table format
        for (Document user : collection.find()) {
            String name = user.getString("name");
            String email = user.getString("email");
            String phone = user.getString("phone");
            String address = user.getString("address");
            Date createdAt = user.getDate("created_at");

            // Format each user as a row in the table
            System.out.printf("%-15s %-30s %-15s %-30s %s%n", name, email, phone, address, createdAt);
        }
    }

    // Update a user based on the name
    public void updateUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the user to update: ");
        String name = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateName);

        // Search for the user document by name
        Document query = new Document("name", name);
        Document user = collection.find(query).first();

        if (user != null) {
            System.out.println("Current user details: " + user.toJson());

            // Ask for the field to update
            System.out.print("Which field would you like to update (name, email, phone, address)? ");
            String field = scanner.nextLine();
            String newValue = "";

            // Validate and get new value for the chosen field
            switch (field) {
                case "name":
                    newValue = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateName);
                    break;
                case "email":
                    newValue = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateEmail);
                    break;
                case "phone":
                    newValue = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validatePhone);
                    break;
                case "address":
                    newValue = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateAddress);
                    break;
                default:
                    System.out.println("Invalid field.");
                    return;
            }

            // Update the document with the new value
            collection.updateOne(query, new Document("$set", new Document(field, newValue)));
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    // Delete a user based on the name
    public void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the user to delete: ");
        String name = ValidationHelper.getValidInput(scanner.nextLine(), ValidationHelper::validateName);

        // Create the query to find the user by name
        Document query = new Document("name", name);
        long deletedCount = collection.deleteOne(query).getDeletedCount();

        // Check if the user was found and deleted
        if (deletedCount > 0) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
