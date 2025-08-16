package org.example.commands;

import org.example.OutputPath;
import org.example.filesystem.GitFileSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CatFileTest extends OutputPath {

    @Test
    @DisplayName("git cat-file -p テスト")
    void catFilePTest() throws Exception {
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        HashObject.hashObjectW(rootPath + "hello.txt", objectFilePath);
        String sha1 = "8c01d89ae06311834ee4b1fab2f0414d35f01102";
        System.out.println("対象のsha1：" + sha1);
        // CatFile.catFileP(sha1);
        GitFileSystem.removeDir(dotGitPath);
    }

}
