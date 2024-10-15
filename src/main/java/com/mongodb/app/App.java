
package com.mongodb.app;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;  // For handling BSON documents (MongoDB documents)


public class App {
    public static void main(String[] args) {
        MongoDatabase db = MongoDbConnection.getDatabase();
        if (db == null) {
            System.out.println("Database connection failed.");
            return;
        }

        CRUDOperations crudOperations = new CRUDOperations(db);
        runMenu(crudOperations);
    }

    public static void runMenu(CRUDOperations crudOperations) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Create a new user");
            System.out.println("2. Read users");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    crudOperations.createUser();
                    break;
                case "2":
                    crudOperations.readUsers();
                    break;
                case "3":
                    crudOperations.updateUser();
                    break;
                case "4":
                    crudOperations.deleteUser();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
