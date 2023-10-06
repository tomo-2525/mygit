package org.example.commands;


import org.example.Index.Index;
import org.example.filesystem.GitFileSystem;

import java.util.Arrays;

public class LsFiles {

    public static void execute(String[] args) throws Exception {
        if(args.length == 1){
            lsFiles(".git/index");
        }
    }

    /**
     * git ls-files
     */
    public static void lsFiles(String path) throws Exception {
        byte[] bytes = GitFileSystem.readBytes(path);
        Index index = new Index(bytes);
        String[] fileNames = index.getFileNames();
        Arrays.stream(fileNames).forEach(fileName -> System.out.println("ファイル名：" + fileName));
    }

}
