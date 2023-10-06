package org.example.index;

import org.example.Index.Index;
import org.example.OutputPath;
import org.example.filesystem.GitFileSystem;
import org.example.objects.Blob;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class IndexTest extends OutputPath {

    @Test
    @DisplayName("index 書き込みテスト")
    void indexFileTest() throws Exception{
        byte[] bytes = GitFileSystem.readBytes(rootPath + "indexOriginal");
        String indexFile = "index";
        Index index = new Index(bytes);
        byte[] indexBytes = index.Index2Bytes();
        GitFileSystem.writeBytes(rootPath + indexFile ,indexBytes);
        GitFileSystem.removeDir(rootPath + indexFile);
    }

    @Test
    @DisplayName("index entry追加のテスト")
    void addIndexEntryTest() throws Exception{

        String indexFile = "index";
        String input = "hello, world";
        String readFilePath = rootPath + "hello.txt";
        GitFileSystem.writeString(readFilePath ,  input);
        byte[] indexBytes = GitFileSystem.readBytes(rootPath + "indexOriginal");
        Index index = new Index(indexBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] stat = GitFileSystem.stat().getBytes();
        Blob blob = new Blob(GitFileSystem.readString(readFilePath));
        byte[] fileSize = ByteBuffer.allocate(4).putInt(blob.getSize()).array();
        byte[] fileHash = blob.calcHash();
        int filePathSize = readFilePath.length();
        byte[] flags = ByteBuffer.allocate(2).putShort((short)filePathSize).array();
        byte[] filePath = readFilePath.getBytes();
        int paddingSize =  8 - (62 + filePathSize) % 8;
        byte[] padding =  ("\0".repeat(paddingSize)).getBytes();
        outputStream.write(stat);
        outputStream.write(fileSize);
        outputStream.write(fileHash);
        outputStream.write(flags);
        outputStream.write(filePath);
        outputStream.write(padding);
        index.addEntries(outputStream.toByteArray());
        System.out.println(index);
    }

    @Test
    @DisplayName("index 標準出力テスト")
    void indexFileOutputTest() throws Exception{
        byte[] bytes = GitFileSystem.readBytes(rootPath + "indexOriginal");
        Index index = new Index(bytes);
        System.out.println(index);
    }
}
