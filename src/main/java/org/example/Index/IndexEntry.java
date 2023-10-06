package org.example.Index;

import org.example.filesystem.GitFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;

public class IndexEntry {
    public byte[] cTime;
    public byte[] cTimeNano;
    public byte[] mTime;
    public byte[] mTimeNano;
    public byte[] dev;
    public byte[] inode;
    public byte[] mode;
    public byte[] uid;
    public byte[] gid;
    public byte[] fileSize;
    public byte[] fileHash;
    public byte[] flags;
    public byte[] filePath;
    public byte[] padding;

    public IndexEntry(
            byte[] cTime,
            byte[] cTimeNano,
            byte[] mTime,
            byte[] mTimeNano,
            byte[] dev,
            byte[] inode,
            byte[] mode,
            byte[] uid,
            byte[] gid,
            byte[] fileSize,
            byte[] fileHash,
            byte[] flags,
            byte[] filePath,
            byte[] padding
    ) {
        this.cTime = cTime;
        this.cTimeNano = cTimeNano;
        this.mTime = mTime;
        this.mTimeNano = mTimeNano;
        this.dev = dev;
        this.inode = inode;
        this.mode = mode;
        this.uid = uid;
        this.gid = gid;
        this.fileSize = fileSize;
        this.fileHash = fileHash;
        this.flags = flags;
        this.filePath = filePath;
        this.padding = padding;
    }

    public IndexEntry(byte[] bytes){
        this.cTime = Arrays.copyOfRange(bytes, 0, 4);
        this.cTimeNano = Arrays.copyOfRange(bytes, 4, 8);
        this.mTime = Arrays.copyOfRange(bytes, 8, 12);
        this.mTimeNano = Arrays.copyOfRange(bytes, 12, 16);
        this.dev = Arrays.copyOfRange(bytes, 16, 20);
        this.inode = Arrays.copyOfRange(bytes, 20, 24);
        this.mode = Arrays.copyOfRange(bytes, 24, 28);
        this.uid = Arrays.copyOfRange(bytes, 28, 32);
        this.gid = Arrays.copyOfRange(bytes, 32, 36);
        this.fileSize = Arrays.copyOfRange(bytes, 36, 40);
        this.fileHash = Arrays.copyOfRange(bytes, 40, 60);
        this.flags = Arrays.copyOfRange(bytes, 60, 62);
        int filePathSize = ByteBuffer.wrap(flags).getShort();
        this.filePath = Arrays.copyOfRange(bytes, 62, 62 + filePathSize);
        this.padding = Arrays.copyOfRange(bytes, 62 + filePathSize, 62 + filePathSize + 8 - (62 + filePathSize) % 8);
    }


    public Date getCtime(){
        return new Date(ByteBuffer.wrap(cTime).getInt() * 1000L);
    }

    public Date getMtime(){
        return new Date(ByteBuffer.wrap(mTime).getInt() * 1000L);
    }

    public int getFileSize(){
        return ByteBuffer.wrap(fileSize).getInt();
    }

    public String getFileHash(){
        return GitFileSystem.bytes2Hex(fileHash);
    }

    public String getFilePath(){
        return new String(filePath);
    }


    public byte[] indexEntry2Byte() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(cTime);
        outputStream.write(cTimeNano);
        outputStream.write(mTime);
        outputStream.write(mTimeNano);
        outputStream.write(dev);
        outputStream.write(inode);
        outputStream.write(mode);
        outputStream.write(uid);
        outputStream.write(gid);
        outputStream.write(fileSize);
        outputStream.write(fileHash);
        outputStream.write(flags);
        outputStream.write(filePath);
        outputStream.write(padding);
        return outputStream.toByteArray();
    }


}
