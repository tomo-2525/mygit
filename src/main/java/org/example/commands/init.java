package org.example.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class init {

    public static  void main(String[] args){
        try{
            execute("mygit");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void execute(String dir) throws IOException {
        // .gitディレクトリの作成
        System.out.println(dir);
        File gitDir = new File(dir);
        gitDir.mkdir();

        // HEADファイルの作成
        Files.write(Paths.get(gitDir.getPath(), "HEAD"), "ref: refs/heads/main".getBytes());
        System.out.println("completed!!!");

        // configファイルの作成
        System.out.println(Paths.get(gitDir.getPath(), "config"));
        Files.write(Paths.get(gitDir.getPath(), "config"), "[core]\n\trepositoryformatversion = 0".getBytes());

        // descriptionファイルの作成
        Files.write(Paths.get(gitDir.getPath(), "description"), "Unnamed repository; edit this file 'description' to name the repository.".getBytes());

        // hooksディレクトリの作成
        new File(gitDir, "hooks").mkdir();

        // infoディレクトリの作成
        new File(gitDir, "info").mkdir();

        // objectsディレクトリの作成
        new File(gitDir, "objects/info").mkdirs();
        new File(gitDir, "objects/pack").mkdirs();

        //　refディレクトリの作成
        new File(gitDir, "refs/heads").mkdirs();
        new File(gitDir, "refs/tags").mkdirs();

    }
}
