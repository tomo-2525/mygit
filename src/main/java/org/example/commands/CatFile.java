package org.example.commands;

import org.example.filesystem.GitFileSystem;

public class CatFile {
    public static void execute(String[] args) throws Exception {
        if(args.length > 2){
            if(args[1].equals("-p")) {
                catFileP(args[2]);
            }
        }else{
            System.out.println("引数がありません");
        }

    }

    /**
     * git cat-file -p sha1
     * */
    public static void catFileP(String sha1) throws Exception {
        String path = GitFileSystem.getGitObjectDirName(sha1) + GitFileSystem.getGitObjectFileName(sha1);
        byte[] bytes = GitFileSystem.readBytes(".git/objects/" + path);
        String content = GitFileSystem.decompress2String(bytes);
        System.out.println("オブジェクトの中身：" + content.split("\0")[1]);
        }

}
