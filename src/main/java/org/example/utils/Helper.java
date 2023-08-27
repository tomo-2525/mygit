package org.example.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.InflaterInputStream;

public class Helper {

    /**
     * @parm hash
     *
     *
     * 引数のhash値に対応するGitObjectの中身を表示する
     */
    public static GitObject catFile(String hash) throws IOException {
        String subDir = hash.substring(0, 2);
        String file = hash.substring(2);
        String path = ".git/objects/" + subDir + "/" + file;

        byte[] data = Files.readAllBytes(Paths.get(path));

        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inflaterInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] inflatedData = outputStream.toByteArray();

            GitObject gitObject = GitObject.newObject(inflatedData);

            if (gitObject == null) {
                throw new IOException("Invalid data");
            }

            return gitObject;
        }
    }

    /**
     * 渡されたファイルをblobにする
     */
    public  static  void hashObject(){
        System.out.println("");
    }

    /**
     * indexファイルを更新する
     */
    public  static  void updateIndex(){
        System.out.println("");
    }


    /**
     *
     */
    public static void writeObject(){
        System.out.println("");
    }

    /**
     *
     */
    public static void writeIndex(){
        System.out.println("");
    }

    /**
     * index ファイルを元に tree を生成する
     */
    public  static void writeTree(){
        System.out.println("");
    }

    /**
     * commitメッセージとrootのTreeのhash値を受け取りCommitを生成する
     */
    public static void commitTree(){
        System.out.println("");
    }

    /**
     * 任意の ref を更新する
     */
    public static void updateRef(){
        System.out.println("");
    }

}
