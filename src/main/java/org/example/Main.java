package org.example;

import org.example.commands.Add;
import org.example.commands.CatFile;
import org.example.commands.HashObject;
import org.example.commands.Init;
import org.example.commands.LsFiles;
import org.example.commands.UpdateIndex;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            showHelp();
            return;
        }

        String command = args[0];

        switch (command) {
            /** TODO
             *  commit
             *  branch
             *  switch
             */
            case "init" -> { Init.execute(args);}
            case "hash-object" -> { HashObject.execute(args);}
            case "update-index" -> { UpdateIndex.execute(args);}
            case "cat-file" -> { CatFile.execute(args);}
            case "ls-files" -> { LsFiles.execute(args);}
            case "add" -> { Add.execute(args) ;}
            case "help" -> {showHelp();}
            default -> showHelp();
        }
    }

    private static void showHelp() {
        System.out.println("Usage:");
        System.out.println("\tadd <file>");
        System.out.println("\tcat-file -p <sha1>");
        System.out.println("\tls-files");
        System.out.println("\tupdate-index -add <file>: ");
        // ... 他のヘルプ情報 ...
    }
}
