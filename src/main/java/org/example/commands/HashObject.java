package org.example.commands;

import org.example.filesystem.GitFileSystem;
import org.example.objects.Blob;

import java.util.Arrays;

public class HashObject {

    public static void execute(String[] args) throws Exception {
        if(args.length > 1){
            if(args[1].equals("-w")){
                hashObjectW(args[2], ".git/objects/");
            }else{
                hashObject(args[1]);
            }
        }else{
            System.out.println("引数がありません");
        }
    }

    /**
     * git hash-object
     * */
    public static void hashObject(String readFilePath) throws Exception{
        String content = GitFileSystem.readString(readFilePath);
        Blob blob = new Blob(content);
        String sha1 = GitFileSystem.byte2string(blob.calcHash());
        System.out.println("SHA1ハッシュ値："+ sha1);
    }


    /**
    * git hash-object -w path
    * */
    public static void hashObjectW(String readFilePath, String  writeFilePath){
        String content = GitFileSystem.readString(readFilePath);
        Blob blob = new Blob(content);
        try{
            String sha1 = GitFileSystem.byte2string(blob.calcHash());
            String dirName = GitFileSystem.getGitObjectDirName(sha1);
            String fileName = GitFileSystem.getGitObjectFileName(sha1);
            System.out.println( writeFilePath + dirName + fileName + "を作成します。");
            GitFileSystem.makeDir(writeFilePath + dirName );
            GitFileSystem.writeBytes( writeFilePath +  dirName + fileName, GitFileSystem.compress(blob.asBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
