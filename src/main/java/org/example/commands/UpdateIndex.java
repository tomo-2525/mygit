package org.example.commands;

import org.example.Index.Index;
import org.example.filesystem.GitFileSystem;
import org.example.objects.Blob;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpdateIndex {
    public static void execute(String[] args) throws Exception{
        if(args.length > 1){
            if(args[1].equals("--add")){
                updateIndexAdd(args[2], ".git/index");
            }else{
                updateIndex(args[1], ".git/index");
            }
        }else{
            System.out.println("引数がありません");
        }
    }

    /**
     * git update-index
     * ステージングエリアにファイルがすでに追加されている場合に使う
     */
    public static void updateIndex(String readFilePath, String writeFilePath) throws Exception {
        if( Files.exists(Paths.get(writeFilePath))){
            byte[] indexBytes = GitFileSystem.readBytes(writeFilePath);
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
            GitFileSystem.writeBytes(writeFilePath, index.Index2Bytes());
        } else {
            System.out.println("indexファイルが存在しません");
        }
    }

    /**
     * git update-index --add
     *  ステージングエリアにファイルが追加されていない場合に--addをつける
     *  ※　ファイルがすでに追加されていても、されていなくも上のコマンドは正しく動作する
     */
    public static void updateIndexAdd(String readFilePath, String writeFilePath) throws Exception {
        if(Files.exists(Paths.get(writeFilePath))){
        // indexが存在する場合
            updateIndex(readFilePath, writeFilePath);
        } else{
        // indexが存在しない場合
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.out.println(".git/indexを作成します。");
            byte[] header = "DIRC".getBytes(); // 固定
            byte[] version = {0, 0, 0, 2}; // 固定
            byte[] entryNum = {0, 0, 0, 1}; // 初めてのエントリーなので、１にする
            byte[] stat = GitFileSystem.stat().getBytes();
            Blob blob = new Blob(GitFileSystem.readString(readFilePath));
            byte[] fileSize = ByteBuffer.allocate(4).putInt(blob.getSize()).array();
            byte[] fileHash = blob.calcHash();
            int filePathSize = readFilePath.length();
            byte[] flags = ByteBuffer.allocate(2).putShort((short)filePathSize).array();
            byte[] filePath = readFilePath.getBytes();
            int paddingSize =  8 - (62 + filePathSize) % 8;
            byte[] padding =  ("\0".repeat(paddingSize)).getBytes();
            byte[] checksum = new byte[20];
            outputStream.write(header);
            outputStream.write(version);
            outputStream.write(entryNum);
            outputStream.write(stat);
            outputStream.write(fileSize);
            outputStream.write(fileHash);
            outputStream.write(flags);
            outputStream.write(filePath);
            outputStream.write(padding);
            outputStream.write(checksum);
            Index index = new Index(outputStream.toByteArray());
            index.udpateCheckSum();
            GitFileSystem.writeBytes(writeFilePath, index.Index2Bytes());
        }
    }
}
