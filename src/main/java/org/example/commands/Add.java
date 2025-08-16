package org.example.commands;

import org.example.commands.HashObject;
import static org.example.commands.HashObject.hashObjectW;


public class Add {

    public static void execute(String[] args) throws Exception {
        if(args.length == 2 ){
            add(args[1]);
        }else{
            System.out.println("ファイルは一つだけ指定してください");
        }
    }

    /**
     * git add
     */
    public static void add(String filepath) throws Exception {
        // git hash-object -w path
        HashObject.hashObjectW(filepath, ".git/objects/");

        // git update-index --add path
        UpdateIndex.updateIndexAdd(filepath,".git/index");

    }
}
