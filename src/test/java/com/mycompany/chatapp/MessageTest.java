package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Test 1: Check that a message with fewer than 250 characters is accepted
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        // Check that the correct message is returned
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // Test 2: Check that a message with more than 250 characters is rejected
    @Test
    public void testMessageLengthFailure() {

        // Create a message with 260 characters
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }

        Message msg = new Message("0012345678", "+27718693002", longText, 0);

        // Check that the message is 10 characters over the limit
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.checkMessageLength());
    }

    // Test 3: Check that a correctly formatted recipient number is accepted
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // Test 4: Check that a recipient number without an international code is rejected
    @Test
    public void testRecipientFailure() {
        Message msg = new Message("0112345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);

        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // Test 5: Check that the message hash is created correctly
    @Test
    public void testMessageHashCase1() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    // Test 6: Check that the correct hash is created for each message in the array
    @Test
    public void testMessageHashLoop() {
        String[] ids = {"0012345678", "0112345678"};
        String[] texts = {"Hi Mike, can you join us for dinner tonight?", "Hi Keegan, did you receive the payment?"};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};

        // Goes through the messages and check each hash
        for (int i = 0; i < ids.length; i++) {
            Message msg = new Message(ids[i], "+27718693002", texts[i], i);
            assertEquals(expected[i], msg.createMessageHash());
        }
    }

    // Test 7: The message ID is created and is 10 characters or fewer
    @Test
    public void testMessageIDCreated() {
        String id = Message.generateMessageID();
        Message msg = new Message(id, "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        // The ID must be exactly 10 characters long
        assertEquals(10, id.length());
        assertTrue(msg.checkMessageID());
        assertEquals("Message ID generated: " + id, msg.getMessageIDText());
    }

    // Test 8: Check that the message is sent when the user chooses option 1
    @Test
    public void testMessageSent() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    // Test 9: Check that the correct response is returned when the user chooses option 2
    @Test
    public void testMessageDisregarded() {
        Message msg = new Message("0112345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);

        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    // Test 10: user selected Store Message
    @Test
    public void testMessageStored() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully stored.", msg.SentMessage(3));

        // Check that the JSON file was created
        java.io.File file = new java.io.File("messages.json");
        assertTrue(file.exists());
    }

    // Test 11: Check that the total number of messages increases by one after sending
    @Test
    public void testTotalMessages() {

        // Get the total number of messages before sending
        int before = Message.returnTotalMessagess();

        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        msg.SentMessage(1);

        // Check that the total has increased by one
        assertEquals(before + 1, Message.returnTotalMessagess());
    }

    // Part 3 unit tests 

    // Clears the arrays and adds the 5 test messages (1 = sent, 2 = disregard, 3 = stored)
    private void addTestData() {

        Message.clearArrays();

        // Message 1: Sent
        Message m1 = new Message("0000000001", "+27834557896", "Did you get the cake?", 0);
        Message.addToArrays(m1, 1);

        // Message 2: Stored
        Message m2 = new Message("0100000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 1);
        Message.addToArrays(m2, 3);

        // Message 3: Disregard
        Message m3 = new Message("0200000003", "+27834484567", "Yohoooo, I am at your gate.", 2);
        Message.addToArrays(m3, 2);

        // Message 4: Sent
        Message m4 = new Message("0300000004", "0838884567", "It is dinner time!", 3);
        Message.addToArrays(m4, 1);

        // Message 5: Stored
        Message m5 = new Message("0400000005", "+27838884567", "Ok, I am leaving without you.", 4);
        Message.addToArrays(m5, 3);
    }

    // Test 12: sent array has messages 1 and 4
    @Test
    public void testSentMessagesArrayPopulated() {
        addTestData();

        assertEquals("Did you get the cake?\nIt is dinner time!", Message.displaySentMessages());
    }

    // Test 13: longest stored message
    @Test
    public void testLongestMessage() {
        addTestData();

        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.longestStoredMessage());
    }

    // Test 14: search by message ID (message 4)
    @Test
    public void testSearchByMessageID() {
        addTestData();

        assertEquals("Recipient: 0838884567\nMessage: It is dinner time!", Message.searchByMessageID("0300000004"));
    }

    // Test 15: message ID not found
    @Test
    public void testSearchByMessageIDNotFound() {
        addTestData();

        assertEquals("Message ID not found.", Message.searchByMessageID("9999999999"));
    }

    // Test 16: search by recipient (messages 2 and 5)
    @Test
    public void testSearchByRecipient() {
        addTestData();

        assertEquals("Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you.",
                Message.searchByRecipient("+27838884567"));
    }

    // Test 17: delete message 2 using its hash
    @Test
    public void testDeleteByHash() {
        addTestData();

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                Message.deleteByHash("01:1:WHERETIME"));

        // The message must be gone
        assertEquals("Message ID not found.", Message.searchByMessageID("0100000002"));
    }

    // Test 18: hash not found
    @Test
    public void testDeleteByHashNotFound() {
        addTestData();

        assertEquals("Message hash not found.", Message.deleteByHash("99:9:NOTHERE"));
    }

    // Test 19: report shows hash, recipient and message
    @Test
    public void testDisplayReport() {
        addTestData();

        String report = Message.displayReport();

        // Message 1 is in the report
        assertTrue(report.contains("Message Hash: 00:0:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));

        // Message 2 is in the report
        assertTrue(report.contains("Message Hash: 01:1:WHERETIME"));
        assertTrue(report.contains("Recipient: +27838884567"));

        // Message 3 (disregarded) is not
        assertFalse(report.contains("Yohoooo"));
    }

    // Test 20: read the stored message back from the JSON file
    @Test
    public void testLoadStoredMessagesFromJSON() {

        // Store a message so the file is written
        Message.clearArrays();
        Message msg = new Message("0500000006", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        msg.SentMessage(3);

        // Empty the arrays, then read the file
        Message.clearArrays();
        Message.loadStoredMessages();

        assertEquals("Recipient: +27718693002\nMessage: Hi Mike, can you join us for dinner tonight?",
                Message.searchByMessageID("0500000006"));
    }
}