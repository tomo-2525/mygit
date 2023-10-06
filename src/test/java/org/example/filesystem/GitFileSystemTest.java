package org.example.filesystem;

import org.example.OutputPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GitFileSystemTest extends OutputPath {

    @Test
    @DisplayName("サブディレクトリも含むディレクトリ作成テスト")
    void makeDirTest() throws Exception {
        GitFileSystem.makeDir(dotGitPath);
        Path directoryPath = Paths.get(dotGitPath);
        assertTrue(Files.exists(directoryPath) && Files.isDirectory(directoryPath));
        GitFileSystem.removeDir(dotGitPath);
    }

    @Test
    @DisplayName("サブディレクトリも含むディレクトリ削除テスト")
    void removeDirTest() throws Exception {
        GitFileSystem.makeDir(dotGitPath);
        Path directoryPath = Paths.get(dotGitPath);
        GitFileSystem.removeDir(dotGitPath);
        assertFalse(Files.exists(directoryPath));
    }

    @Test
    @DisplayName("ファイル書き込み(string)テスト")
    void writeStringTest() throws Exception {
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        assertTrue(Files.exists(Paths.get(rootPath + "hello.txt")));
        GitFileSystem.removeDir(rootPath + "hello.txt");
    }

    @Test
    @DisplayName("ファイル読み込み(String)テスト")
    void readStringTest() throws Exception {
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        String result = GitFileSystem.readString(rootPath + "hello.txt");
        assertEquals(result, input);
        GitFileSystem.removeDir(rootPath + "hello.txt");
    }

    @Test
    @DisplayName("ファイル書き込み(byte[])テスト")
    void writeBytesTest() throws Exception {
        byte[] input = {0, 1, 2, 3, 4, 5};
        String fileName = "binary.txt";
        GitFileSystem.writeBytes(rootPath + fileName ,  input);
        GitFileSystem.removeDir(rootPath + fileName);
    }

    @Test
    @DisplayName("ファイル読み込み(byte[])テスト")
    void readBytesTest() throws Exception {
        byte[] input = {0, 1, 2, 3, 4, 5};
        String fileName = "binary.txt";
        GitFileSystem.writeBytes(rootPath + fileName ,  input);
        byte[] output = GitFileSystem.readBytes(rootPath + fileName);
        assertArrayEquals(output, input);
        GitFileSystem.removeDir(rootPath + fileName);
    }



    @Test
    @DisplayName("statテスト")
    void statTest() throws Exception {
        Stat stat = GitFileSystem.stat();
    }
}
