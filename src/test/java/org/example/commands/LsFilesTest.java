package org.example.commands;

import org.example.OutputPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LsFilesTest extends OutputPath {
    @Test
    @DisplayName("git ls-files テスト")
    void lsFIleTest() throws Exception {
        LsFiles.lsFiles(rootPath + "indexOriginal");
    }
}
