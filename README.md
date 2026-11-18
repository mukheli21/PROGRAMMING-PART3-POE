# PROG5121 POE Part 3

**Name:** MUKHELI THAKHANI
**Student number:** ST10514049
**Module:** PROGRAMMING 1A (PROG5121)

## About the project

This is a Java console app called Chat app. A user registers, logs in and sends messages.
Part 3 adds arrays and a new stored messages menu.

## Features

**Part 1: Registration and login**
- Checks the username, password and South African cell number
- Logs the user in

**Part 2: Messages**
- Creates a message ID and message hash
- Checks the recipient number and message length (250 characters or fewer)
- The user chooses to send, disregard or store each message
- Stored messages are saved in `messages.json`

**Part 3: Arrays and Stored Messages**
- Five arrays are filled from what the user enters
  sent messages, disregarded messages, stored messages, message hashes and message IDs
- The stored messages are read from `messages.json` into an array
- A fourth main menu option, **Stored Messages**, with these choices:
  - a) Display the sender and recipient of all stored messages
  - b) Display the longest stored message
  - c) Search for a message ID and display the recipient and message
  - d) Search for all messages for a recipient
  - e) Delete a message using its hash
  - f) Display a report of the messages (ID, hash, recipient, message and flag)
  - g) Back

## Project files

 ## File and What it does 

ChatApp.java- Main class with the menus 
Login.java - Registration and login checks 
IUserValidation.java - Interface for the validation methods 
Message.java - Message details, the arrays and the Stored Messages methods
LoginTest.java - Unit tests for registration and login 
MessageTest.java - Unit tests for messages and the Part 3 features 
github/workflows/TestJava.yml Runs the unit tests automatically on GitHub 

## How to run

1. Open the project in NetBeans.
2. Right-click the project and choose **Run**. Choose ChatApp as the main.
3. Register, log in, then type how many messages you want to enter.

## How to run the unit tests

- In NetBeans, right-click the project and choose **Test**.
- Or in a terminal, run `mvn test`.

## Reference

w3schools.com (n.d.) *Java Read Files*. Available at: https://www.w3schools.com/java/java_files_read.asp (Accessed: 18 November 2026).

w3schools.com (n.d.) *JSON Introduction*. Available at: https://www.w3schools.com/js/js_json.asp (Accessed: 18 November 2026).
