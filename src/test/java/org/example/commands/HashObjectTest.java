package org.example.commands;

import org.example.OutputPath;
import org.example.filesystem.GitFileSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashObjectTest extends OutputPath {

    @Test
    @DisplayName("git hash-object テスト")
    void hashObjectTest() throws Exception {
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        assertTrue(Files.exists(Paths.get(rootPath + "hello.txt")));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        HashObject.hashObject(rootPath + "hello.txt");
        assertEquals("SHA1ハッシュ値：8c01d89ae06311834ee4b1fab2f0414d35f01102\n", outContent.toString());
    }

    @Test
    @DisplayName("git hash-object -w テスト")
    void hashObjectWTest() throws Exception {
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        assertTrue(Files.exists(Paths.get(rootPath + "hello.txt")));
        HashObject.hashObjectW(rootPath + "hello.txt", objectFilePath);
        // ファイルが存在しているかの確認
        String targetFilePath = objectFilePath + "8c/01d89ae06311834ee4b1fab2f0414d35f01102";
        Path directoryPath = Paths.get(targetFilePath);
        assertTrue(Files.exists(directoryPath));

        // ファイルの中身の確認
        byte[] bytes = GitFileSystem.readBytes(targetFilePath);
        String compressedString = GitFileSystem.byte2string(bytes);
        assertEquals("78014bcac94f52303462c848cdc9c9d75128cf2fca49010042f306ab", compressedString);
        GitFileSystem.removeDir(dotGitPath);
        GitFileSystem.removeDir(rootPath + "hello.txt");

    }
}
