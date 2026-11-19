/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package poepart3.pkg3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Message {

    ArrayList<String> sentMessages = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    HashSet<String> messageIds = new HashSet<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();
    ArrayList<String> disregardedMessages = new ArrayList<>();
    ArrayList<String> storedMessages = new ArrayList<>();
    ArrayList<String> senders = new ArrayList<>();

    Random random = new Random();
    Scanner input = new Scanner(System.in);

    int numMessageSent = 0;

    public void sendingMessages() {
        int numberOfMessages = 0;
        while (true) {
            String inputStr = JOptionPane.showInputDialog(null,
                    "How many messages do you want to send",
                    "Send Messages",
                    JOptionPane.QUESTION_MESSAGE);
            if (inputStr == null) {
                return;
            }
            try {
                numberOfMessages = Integer.parseInt(inputStr);
                if (numberOfMessages < 1) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a number greater than 0",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        input.nextLine();

        for (int i = 1; i <= numberOfMessages; i++) {
            System.out.println("\n==========================");
            System.out.println("MESSAGE " + i);
            System.out.println("==========================");

            String messageID = generateMessageID();

            System.out.print("Enter recipient cell number: ");
            String recipient = input.nextLine();

            String recipientResult = checkRecipientCell(recipient);

            if (!recipientResult.equals("Valid number")) {
                System.out.println(recipientResult);
                i--;
                continue;
            }

            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            if (messageText.length() > 250 || messageText.trim().isEmpty()) {
                System.out.println("Please enter a message of 1 to 250 characters.");
                i--;
                continue;
            }

            int messageNumber = sentMessages.size() + 1;
            String messageHash = createMessageHash(messageID, messageNumber, messageText);
            msgHash.add(messageHash);

            sentMessages.add(messageText);
            messageIdsList.add(messageID);
            messageIds.add(messageID);
            recipientNumber.add(recipient);

            System.out.print("Enter sender name: ");
            String sender = input.nextLine();
            senders.add(sender);

            String status = SentMessage();
            messageStatus.add(status);

            if (status.equals("Sent")) {
                numMessageSent++;
            }
        }

        printMessages();
        System.out.println("Total messages sent: " + returnTotalMessages());
    }

    public int returnTotalMessages() {
        return numMessageSent;
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoNumbers = messageID.substring(0, 2);
        String cleanMessage = messageText.trim();
        String[] words = cleanMessage.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String generateMessageID() {
        String messageID;
        do {
            long number = 1000000000L + random.nextLong(9000000000L);
            messageID = String.valueOf(number);
        } while (messageIds.contains(messageID));
        return messageID;
    }

    public String checkRecipientCell(String cellphone) {
        if (cellphone == null || !cellphone.startsWith("+27") || cellphone.length() != 12) {
            return "Cellphone number is incorrectly formatted or does not contain an international code.";
        }
        return "Valid number";
    }

    public String SentMessage() {
        System.out.println("\nChoose what you want to do with this message!");
        System.out.println("1. Send");
        System.out.println("2. Disregard");
        System.out.println("3. Store");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Message successfully sent");
                return "Sent";
            }
            case 2 -> {
                System.out.println("Press 0 to delete the message");
                int deleteChoice = input.nextInt();
                input.nextLine();

                if (deleteChoice == 0) {
                    System.out.println("Message deleted");
                    int lastIndex = sentMessages.size() - 1;
                    String messageID = messageIdsList.get(lastIndex);
                    messageIds.remove(messageID);
                    recipientNumber.remove(lastIndex);
                    sentMessages.remove(lastIndex);
                    msgHash.remove(lastIndex);
                    messageIdsList.remove(lastIndex);
                    senders.remove(lastIndex);
                    return "Disregarded";
                } else {
                    System.out.println("Invalid option");
                    return "Disregarded";
                }
            }
            case 3 -> {
                System.out.println("Message successfully stored");
                return "Stored";
            }
            default -> {
                System.out.println("Invalid option");
                return "Disregarded";
            }
        }
    }

    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();
        System.out.println("\n===== All Messages =====");

        for (int i = 0; i < sentMessages.size(); i++) {
            int messageNumber = i + 1;

            allMessages.append("\nMessage Number: ").append(messageNumber);
            allMessages.append("\nMessage ID: ").append(messageIdsList.get(i));
            allMessages.append("\nRecipient: ").append(recipientNumber.get(i));
            allMessages.append("\nMessage: ").append(sentMessages.get(i));
            allMessages.append("\nMessage Hash: ").append(msgHash.get(i));
            allMessages.append("\nStatus: ").append(messageStatus.get(i));

            System.out.println("\n--------------------------");
            System.out.println("Message Number: " + messageNumber);
            System.out.println("Message ID: " + messageIdsList.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
            System.out.println("Message: " + sentMessages.get(i));
            System.out.println("Message Hash: " + msgHash.get(i));
            System.out.println("Status: " + messageStatus.get(i));
        }
        return allMessages.toString();
    }

    public void messageManagementMenu() {
        while (true) {
            System.out.println();
            System.out.println("1. Show disregarded messages");
            System.out.println("2. Show all stored messages");
            System.out.println("3. Delete a message by Hash ID");
            System.out.println("4. Display full report");
            System.out.println("5. Search for a message");
            System.out.println("6. Display the longest message");
            System.out.println("7. Back to menu");
            System.out.print("Choose an option (1-7): ");
            String option = input.nextLine();

            switch (option) {
                case "1" -> showDisregardedMessages();
                case "2" -> showAllStoredMessages();
                case "3" -> deleteMessageByHashID();
                case "4" -> displayFullReport();
                case "5" -> searchMessageByHashID();
                case "6" -> findLongestMessage();
                case "7" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Stub methods for the menu - add your logic from the other screenshots here
  public void showDisregardedMessages() {
    System.out.println("--- Disregarded Messages ---");
    for (String m : disregardedMessages) System.out.println(m);
}

public void showAllStoredMessages() {
    System.out.println("--- Stored Messages: Sender + Recipient ---");
    for (int i = 0; i < storedMessages.size(); i++) {
        System.out.println("Sender: You | Recipient: " + recipientNumber.get(i) + " | Message: " + storedMessages.get(i));
    }
}

public void displayFullReport() {
    System.out.println("--- Full Report (Sent Messages) ---");
    for (int i = 0; i < sentMessages.size(); i++) {
        System.out.println("Hash: " + msgHash.get(i) + " | Recipient: " + recipientNumber.get(i) + " | Message: " + sentMessages.get(i));
    }
}

public void searchMessageByID() {
    System.out.print("Enter Message ID: ");
    String id = input.nextLine();
    int index = messageIdsList.indexOf(id);
    if (index != -1) System.out.println("Recipient: " + recipientNumber.get(index) + " | Message: " + sentMessages.get(index));
    else System.out.println("Not found");
}

public void deleteMessageByHashID() {
    System.out.print("Enter Message Hash: ");
    String hash = input.nextLine();
    int index = msgHash.indexOf(hash);
    if (index != -1) {
        System.out.println("Message: \"" + sentMessages.get(index) + "\" successfully deleted.");
        sentMessages.remove(index);
        msgHash.remove(index);
        messageIdsList.remove(index);
        recipientNumber.remove(index);
        storedMessages.remove(sentMessages.get(index)); // also remove from stored if it was there
    } else System.out.println("Hash not found");
}

public void findLongestMessage() {
    String longest = "";
    for (String m : storedMessages) {
        if (m.length() > longest.length()) longest = m;
    }
    System.out.println("Longest: " + longest);
}
public void searchByRecipient() {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter recipient number to search: ");
    String rec = input.nextLine();
    boolean found = false;

   for (int i = 0; i < recipientNumber.size(); i++) {
    if (recipientNumber.get(i).equals(rec)) {
        }
    }
    if (!found) {
        System.out.println("No messages found for that recipient.");
    }
}

   public void searchMessageByHashID() {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter message hash to search: ");
    String hash = input.nextLine();

    for (int i = 0; i < msgHash.size(); i++) {
        if (msgHash.get(i).equals(hash)) {
            System.out.println("FOUND: " + sentMessages.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
        }
    }
    System.out.println("Message hash not found.");
}
   
   public void saveStoredToJSON() {
    try {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            json.append("{\n\"hash\":\"").append(msgHash.get(i))
            .append(",\n\"recipient\":\"").append(recipientNumber.get(i))
            .append(",\n\"message\":\"").append(storedMessages.get(i)).append("\"\n}\n");
        }
        json.append("]\n");
        java.nio.file.Files.writeString(java.nio.file.Paths.get("messages.json"), json.toString());
    } catch (java.io.IOException e) {
        System.out.println("JSON save failed");
    }
   }
}
   