package org.example;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            showHelp();
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length > 1) {
                    String fileName = args[1];
                    System.out.println("Adding file: " + fileName);
                    // ... 実際の処理 ...
                } else {
                    System.out.println("File name is missing for 'add' command.");
                }
                break;

            case "commit":
                if (args.length > 1) {
                    String message = args[1];
                    System.out.println("Committing with message: " + message);
                    // ... 実際の処理 ...
                } else {
                    System.out.println("Commit message is missing.");
                }
                break;

            default:
                showHelp();
                break;
        }
    }

    private static void showHelp() {
        System.out.println("Usage:");
        System.out.println("  add [filename]");
        System.out.println("  commit [message]");
        // ... 他のヘルプ情報 ...
    }
}
