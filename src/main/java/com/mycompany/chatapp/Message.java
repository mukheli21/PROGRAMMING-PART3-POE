package com.mycompany.chatapp;
import java.io.FileWriter;
import java.io.File;                 // Part 3: open the file
import java.util.ArrayList;          
import java.util.Scanner;            
public class Message {
    
    // Variables to store the message details
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;
    
    // An array to keep the sent messages (max 100)
    private static String[] sentMessages = new String[100];
    
    // Counts how many messages have been sent
    private static int totalMessages = 0;
    
    // An array to keep the stored messages as JSON text (max 100)
    private static String[] storedMessages = new String[100];
    
    // Counts how many messages have been stored
    private static int totalStored = 0;
    
    // Part 3: the arrays
    
    // Text of the sent messages
    private static ArrayList<String> sentMessagesList = new ArrayList<String>();
    
    // Text of the disregarded messages
    private static ArrayList<String> disregardedMessages = new ArrayList<String>();
    
    // Text of the stored messages (read from the JSON file)
    private static ArrayList<String> storedMessagesList = new ArrayList<String>();
    
    // All the message hashes
    private static ArrayList<String> messageHashes = new ArrayList<String>();
    
    // All the message IDs
    private static ArrayList<String> messageIDs = new ArrayList<String>();
    
    // Parallel arrays: the same position is the same message
    private static ArrayList<String> messageRecipients = new ArrayList<String>();
    private static ArrayList<String> messageTexts = new ArrayList<String>();
    
    // Flag: "Sent", "Stored" or "Disregard"
    private static ArrayList<String> messageFlags = new ArrayList<String>();
    
    // Constructor to store the details
    public Message(String messageID, String recipient, String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
    }
    // Makes a random ten-digit message ID
    public static String generateMessageID() {
        String digits = "";
        
        // Add one random digit (0 to 9) fifteen times
        for (int i = 0; i < 15; i++) {
            int digit = (int) (Math.random() * 10);
            digits = digits + digit;
        }
        // substring takes only the first ten digits
        String id = digits.substring(0, 10);
        return id;
    }
    // Returns the message ID text to display
    public String getMessageIDText() {
        return "Message ID generated: " + messageID;
    }
    // Checks that the message ID is not more than ten characters
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }
    // Checks the recipient number and returns a message
    public String checkRecipientCell() {
        // Must start with + and be no longer than 13 characters
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    // Checks that the message is 250 characters or fewer
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int tooMany = message.length() - 250;
            return "Message exceeds 250 characters by " + tooMany + "; please reduce the size.";
        }
    }
    // Creates the hash, for example 00:0:HITONIGHT
    public String createMessageHash() {
        
        // First two numbers of the message ID
        String firstTwo = messageID.substring(0, 2);
        
        // Split the message into words
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        // Removes anything that is not a letter (like , or ?)
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        
        // Joins it together and change it to capital letters
        messageHash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        messageHash = messageHash.toUpperCase();
        return messageHash;
    }
    // Returns all the message details in the correct order
    public String getMessageDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + createMessageHash() + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + message;
    }
    // The user's choice: 1 = send, 2 = disregard, 3 = store
    public String SentMessage(int choice) {
        if (choice == 1) {
            // Save the message in the array and add 1 to the total
            sentMessages[totalMessages] = getMessageDetails();
            totalMessages++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }
    // Returns all the messages sent while the program is running
    public static String printMessages() {
        String all = "";
        // Loop through every message that was sent
        for (int i = 0; i < totalMessages; i++) {
            all = all + sentMessages[i] + "\n\n";
        }
        return all;
    }
    // Returns the total number of messages sent
    public static int returnTotalMessagess() {
        return totalMessages;
    }
    // Stores the message in a JSON file 
     // Attribution: [https://www.w3schools.com/js/js_json.asp]
    
    public void storeMessage() {
        // Remove any quotation marks so the JSON text stays correct
        String safeMessage = message.replace("\"", "'");
        
        storedMessages[totalStored] = "  {\"messageID\": \"" + messageID + "\", "
                + "\"messageHash\": \"" + createMessageHash() + "\", "
                + "\"recipient\": \"" + recipient + "\", "
                + "\"message\": \"" + safeMessage + "\"}";
        totalStored++;
        
        try {
            // This writes the whole file again so it is always one correct JSON array
            FileWriter writer = new FileWriter("messages.json");
            writer.write("[\n");
            
            // Writes every stored message
            for (int i = 0; i < totalStored; i++) {
                writer.write(storedMessages[i]);
             
                if (i < totalStored - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not store the message.");
        }
    }
    
    // Part 3 methods 
    
    // Adds a message to the arrays (1 = sent, 2 = disregard, 3 = store)
    public static void addToArrays(Message msg, int choice) {
        
        String flag = "";
        
        if (choice == 1) {
            flag = "Sent";
            sentMessagesList.add(msg.message);
        } else if (choice == 2) {
            flag = "Disregard";
            disregardedMessages.add(msg.message);
        } else if (choice == 3) {
            flag = "Stored";
            storedMessagesList.add(msg.message);
        } else {
            // Wrong choice, so add nothing
            return;
        }
        
        // Add to the parallel arrays
        messageIDs.add(msg.messageID);
        messageHashes.add(msg.createMessageHash());
        messageRecipients.add(msg.recipient);
        messageTexts.add(msg.message);
        messageFlags.add(flag);
    }
    
    // Reads messages.json into the stored array
    // Attribution: w3schools.com (n.d.) Java Read Files. Available at: https://www.w3schools.com/java/java_files_read.asp
    public static void loadStoredMessages() {
        try {
            // Opens the file and read it line by line
            File file = new File("messages.json");
            Scanner reader = new Scanner(file);
            
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                
                // Only message lines have "messageID"
                if (line.contains("\"messageID\"")) {
                    
                    // Split on the quote marks to get the values
                    String[] parts = line.split("\"");
                    
                    if (parts.length >= 16) {
                        String id = parts[3];
                        String hash = parts[7];
                        String recipient = parts[11];
                        String text = parts[15];
                        
                        // Skip it if the ID is already in the arrays
                        if (!messageIDs.contains(id)) {
                            storedMessagesList.add(text);
                            messageIDs.add(id);
                            messageHashes.add(hash);
                            messageRecipients.add(recipient);
                            messageTexts.add(text);
                            messageFlags.add("Stored");
                        }
                    }
                }
            }
            reader.close();
            
        } catch (Exception e) {
            // No file yet, so nothing to load
        }
        
        // Keep the old JSON array the same
        rebuildStoredArray();
    }
    
    // Rebuilds the old JSON text array from the new arrays
    private static void rebuildStoredArray() {
        totalStored = 0;
        
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored") && totalStored < 100) {
                storedMessages[totalStored] = "  {\"messageID\": \"" + messageIDs.get(i) + "\", "
                        + "\"messageHash\": \"" + messageHashes.get(i) + "\", "
                        + "\"recipient\": \"" + messageRecipients.get(i) + "\", "
                        + "\"message\": \"" + messageTexts.get(i).replace("\"", "'") + "\"}";
                totalStored++;
            }
        }
    }
    
    // Writes the stored messages to messages.json (used after a delete)
    public static void saveStoredToFile() {
        rebuildStoredArray();
        
        try {
            FileWriter writer = new FileWriter("messages.json");
            writer.write("[\n");
            
            for (int i = 0; i < totalStored; i++) {
                writer.write(storedMessages[i]);
                
                if (i < totalStored - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not save the file.");
        }
    }
    
    // Returns the sent messages, one per line
    public static String displaySentMessages() {
        String all = "";
        
        for (int i = 0; i < sentMessagesList.size(); i++) {
            if (!all.equals("")) {
                all = all + "\n";
            }
            all = all + sentMessagesList.get(i);
        }
        return all;
    }
    
    // a) Sender and recipient of all stored messages
    public static String displaySenderAndRecipient(String sender) {
        String all = "";
        
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                if (!all.equals("")) {
                    all = all + "\n";
                }
                all = all + "Sender: " + sender + ", Recipient: " + messageRecipients.get(i);
            }
        }
        
        if (all.equals("")) {
            return "No stored messages.";
        }
        return all;
    }
    
    // b) The longest stored message
    public static String longestStoredMessage() {
        String longest = "";
        
        // Keep the longest message found so far
        for (int i = 0; i < storedMessagesList.size(); i++) {
            if (storedMessagesList.get(i).length() > longest.length()) {
                longest = storedMessagesList.get(i);
            }
        }
        
        if (longest.equals("")) {
            return "No stored messages.";
        }
        return longest;
    }
    
    // c) Search by message ID
    public static String searchByMessageID(String id) {
        
        for (int i = 0; i < messageIDs.size(); i++) {
            // Skip disregarded messages
            if (messageIDs.get(i).equals(id) && !messageFlags.get(i).equals("Disregard")) {
                return "Recipient: " + messageRecipients.get(i) + "\n"
                        + "Message: " + messageTexts.get(i);
            }
        }
        return "Message ID not found.";
    }
    
    // d) Search for all messages for one recipient
    public static String searchByRecipient(String recipient) {
        String all = "";
        
        for (int i = 0; i < messageRecipients.size(); i++) {
            if (messageRecipients.get(i).equals(recipient) && !messageFlags.get(i).equals("Disregard")) {
                if (!all.equals("")) {
                    all = all + "\n";
                }
                all = all + messageTexts.get(i);
            }
        }
        
        if (all.equals("")) {
            return "No messages found for that recipient.";
        }
        return all;
    }
    
    // e) Delete a stored message using its hash
    public static String deleteByHash(String hash) {
        
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash) && messageFlags.get(i).equals("Stored")) {
                
                // Keep the text to show it
                String text = messageTexts.get(i);
                
                // Remove it from every array
                storedMessagesList.remove(text);
                messageIDs.remove(i);
                messageHashes.remove(i);
                messageRecipients.remove(i);
                messageTexts.remove(i);
                messageFlags.remove(i);
                
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }
    
    // f) Report with the full details of the messages
    public static String displayReport() {
        String report = "";
        
        for (int i = 0; i < messageIDs.size(); i++) {
            // Skip disregarded messages
            if (!messageFlags.get(i).equals("Disregard")) {
                report = report + "Message ID: " + messageIDs.get(i) + "\n"
                        + "Message Hash: " + messageHashes.get(i) + "\n"
                        + "Recipient: " + messageRecipients.get(i) + "\n"
                        + "Message: " + messageTexts.get(i) + "\n"
                        + "Flag: " + messageFlags.get(i) + "\n\n";
            }
        }
        
        if (report.equals("")) {
            return "No messages to report.";
        }
        return report;
    }
    
    // Empties all the arrays-used by the tests 
    public static void clearArrays() {
        sentMessagesList.clear();
        disregardedMessages.clear();
        storedMessagesList.clear();
        messageHashes.clear();
        messageIDs.clear();
        messageRecipients.clear();
        messageTexts.clear();
        messageFlags.clear();
        totalMessages = 0;
        totalStored = 0;
    }
}