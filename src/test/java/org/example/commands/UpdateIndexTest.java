package org.example.commands;

import org.example.Index.Index;
import org.example.OutputPath;
import org.example.filesystem.GitFileSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateIndexTest extends OutputPath {
    @Test
    @DisplayName("git update-index -addテスト")
    void updateIndexTest() throws Exception {
        // git init（dotgit作成）
        if(Files.exists(Paths.get(dotGitPath))){
            GitFileSystem.removeDir(dotGitPath);
        }
        GitFileSystem.makeDir(dotGitPath);

        // ファイル作成
        String input = "hello, world";
        GitFileSystem.writeString(rootPath + "hello.txt" ,  input);
        assertTrue(Files.exists(Paths.get(rootPath + "hello.txt")));

        UpdateIndex.updateIndexAdd(rootPath + "hello.txt", indexFilePath);
        byte[] bytes = GitFileSystem.readBytes(indexFilePath);
        Index index = new Index(bytes);
        System.out.println(index);
//        GitFileSystem.removeDir(dotGitPath);
    }
}
