package org.example.Index;


import org.example.filesystem.GitFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.copyOfRange;

public class Index {

    public byte[] header = "DIRC".getBytes(); // 固定
    public byte[] version = {0, 0, 0, 2}; // 固定
    public byte[] entryNum;
    public List<IndexEntry> entries;
    public byte[] checkSum;

    public Index(byte[] bytes) throws Exception {
        entryNum = copyOfRange(bytes, 8, 12);
        entries = bytes2Entries(copyOfRange(bytes, 12, bytes.length - 20), ByteBuffer.wrap(entryNum).getInt());
        checkSum = copyOfRange(bytes, bytes.length - 20, bytes.length);
    }

    public byte[] Index2Bytes() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(header);
        outputStream.write(version);
        outputStream.write(entryNum);
        entries.forEach(entry -> {
            try {
                outputStream.write(entry.indexEntry2Byte());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        outputStream.write(checkSum);
        return outputStream.toByteArray();
    }

    public List<IndexEntry> bytes2Entries(byte[] bytes, int entryNum) {
        List<IndexEntry> entries = new java.util.ArrayList<>();
        int offset = 0;
        // 順番を保証する必要があるので、streamは使えない？
        // ファイル名でソートすれば、stream使ってもいいかも
        for (int i = 0; i < entryNum; i++) {
            int filePathSize = ByteBuffer.wrap(bytes, offset + 60, 2).getShort() & 0x0FFF;
            int paddingSize = 8 - (62 + filePathSize) % 8;

            byte[] ctime = copyOfRange(bytes, offset, offset + 4);
            byte[] ctimeNano = copyOfRange(bytes, offset + 4, offset + 8);
            byte[] mtime = copyOfRange(bytes, offset + 8, offset + 12);
            byte[] mtimeNano = copyOfRange(bytes, offset + 12, offset + 16);
            byte[] dev = copyOfRange(bytes, offset + 16, offset + 20);
            byte[] inode = copyOfRange(bytes, offset + 20, offset + 24);
            byte[] mode = copyOfRange(bytes, offset + 24, offset + 28);
            byte[] uid = copyOfRange(bytes, offset + 28, offset + 32);
            byte[] gid = copyOfRange(bytes, offset + 32, offset + 36);
            byte[] fileSize = copyOfRange(bytes, offset + 36, offset + 40);
            byte[] fileHash = copyOfRange(bytes, offset + 40, offset + 60);
            byte[] flags = copyOfRange(bytes, offset + 60, offset + 62);
            byte[] filePath =  copyOfRange(bytes, offset + 62, offset + 62 + filePathSize) ;
            byte[] padding = ("\0".repeat(paddingSize)).getBytes();
            entries.add(new IndexEntry(ctime, ctimeNano, mtime, mtimeNano, dev, inode, mode, uid, gid, fileSize, fileHash, flags, filePath, padding));
            offset += 62 + filePathSize + paddingSize;
        }
        return entries;
    }

    public String getVersion(){
        return new String(version);
    }

    public int getEntryNum(){
        return ByteBuffer.wrap(entryNum).getInt();
    }

    public void setEntryNum(byte[] entryNum){
        this.entryNum = entryNum.clone();
    }

    public void incrementEntryNum(){
        setEntryNum(ByteBuffer.allocate(4).putInt(getEntryNum() + 1).array());
    }

    public void addEntries(byte[] entry) throws Exception {
        entries.add(new IndexEntry(entry));
        incrementEntryNum();
        udpateCheckSum();
    }

    public void udpateCheckSum() throws Exception {
        byte[] indexBytes = Index2Bytes();
        checkSum = GitFileSystem.calcHash(copyOfRange(indexBytes, 0, indexBytes.length -20));
    }

    public String[] getFileNames(){
        return entries.stream().map(IndexEntry::getFilePath).toArray(String[]::new);
    }

    public String getCheckSum() {
        return GitFileSystem.bytes2Hex(checkSum);
    }


    @Override
    public String toString(){
        String output = "";
        output += "entry num: " + getEntryNum() + "\n\n";
        for(IndexEntry entry: entries){
            output += "create time: " + entry.getCtime() + "\n";
            output += "file path: " + entry.getFilePath() + "\n";
            output += "file hash: " + entry.getFileHash() + "\n\n";
        }
        output += "check sum: " + getCheckSum() + "\n";
        return output;
    }
}
