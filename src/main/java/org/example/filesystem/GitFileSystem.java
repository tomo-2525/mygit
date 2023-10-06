package org.example.filesystem;

import org.example.Index.Index;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Date;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static java.util.Arrays.copyOf;

public class GitFileSystem {

    public static void makeDir(String path) {
        Path p = Paths.get(path).toAbsolutePath();
        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            System.out.println("ディレクトリが作成できません");
            e.printStackTrace();
        }
    }

    public static void removeDir(String path) throws Exception {
        Path absolutePath = Paths.get(path).toAbsolutePath();
        if (Files.isDirectory(absolutePath)) {
            Stream<Path> entries = Files.walk(absolutePath);
            entries.sorted((p1, p2) -> p2.compareTo(p1))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } else {
            Files.delete(absolutePath);
        }
    }

    public static void writeBytes(String filePath, byte[] bytes) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath();
        // ディレクトリがなかったら作成する
        int lastIndex = filePath.lastIndexOf('/');
        if (!Files.exists(path) && lastIndex != -1) {
            makeDir(filePath.substring(0, lastIndex));
        }

        try {
            java.io.FileOutputStream fos = new FileOutputStream(path.toFile());
            fos.write(bytes);
        } catch (IOException e) {
            System.out.println("ファイルの書き込みができません");
            e.printStackTrace();
        }
    }

    public static void writeString(String filePath, String string) throws Exception {
        byte[] bytes = GitFileSystem.string2byte(string);
        writeBytes(filePath, bytes);
    }


    public static byte[] readBytes(String filePath) {
        Path path = Paths.get(filePath).toAbsolutePath();
        byte[] data = {};
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
            e.printStackTrace();
        }
        return data;
    }

    public static String readString(String filePath) {
        Path path = Paths.get(filePath).toAbsolutePath();
        String data = "";
        try {
            data = Files.readString(path);
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
            e.printStackTrace();
        }
        return data;
    }

    public static String getGitObjectDirName(String path) {
        return path.substring(0, 2) + "/";
    }

    public static String getGitObjectFileName(String path) {
        return path.substring(2);
    }

    public static byte[] calcHash(byte[] bytes) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        return sha1.digest(bytes);
    }

    static int MAX_LENGTH = 1000;

    public static byte[] compress(byte[] input) {
        byte[] output = new byte[MAX_LENGTH];
        Deflater compressor = new Deflater(1);
        compressor.setInput(input);
        compressor.finish();
        int compressedDataLength = compressor.deflate(output);
        compressor.end();
        return copyOf(output, compressedDataLength);
    }

    public static byte[] decompress2Byte(byte[] input) {
        byte[] output = new byte[MAX_LENGTH];
        int decompressedDataLength = 0;
        Inflater decompressor = new Inflater();
        decompressor.setInput(input);
        try {
            decompressedDataLength = decompressor.inflate(output);
            decompressor.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copyOf(output, decompressedDataLength);
    }

    public static String decompress2String(byte[] input) throws Exception {
        byte[] output = new byte[MAX_LENGTH];
        int decompressedDataLength = 0;
        Inflater decompressor = new Inflater();
        decompressor.setInput(input);
        try {
            decompressedDataLength = decompressor.inflate(output);
            decompressor.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(copyOf(output, decompressedDataLength), 0, decompressedDataLength, "UTF-8");
    }

    public static String byte2string(byte[] bytes) {
        return String.format("%x", new java.math.BigInteger(1, bytes));
    }

    public static byte[] string2byte(String string) {
        return string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    public static Stat stat() { return new Stat(); }

    public static String bytes2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String bytes2Hex(byte[] bytes, int start, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            sb.append(String.format("%02x", bytes[i]));
        }
        return sb.toString();
    }




    /**
     *
     */
//    public static void readObject(){
//        System.out.println("");
//    }


    /**
     *
     */
//    public static void writeObject(){
//        System.out.println("");
//    }


    /**
     *
     */
//    public static void headRef(){
//    }


    /**
     *
     */
//    public static void readRef(){
//    }


    /**
     *
     */
//    public static void writeRef(){
//    }


    /**
     * git write-tree
     * index ファイルを元に tree を生成する
     */
//    public  static void writeTree(){
//        System.out.println("");
//    }


    /**
     * git commit-tree
     * commitメッセージとrootのTreeのhash値を受け取りCommitを生成する
     */
//    public static void commitTree(){
//        System.out.println("");
//    }

    /**
     * git update-ref
     */
//    public static void updateRef(){
//
//    }


    /**
     *
     */
//    public static void diffApply(){
//
//    }


    /**
     *
     */
//    public static void tree2index(){
//    }


    /**
     *
     */
//    public static void helperTree2index(){
//    }

}
