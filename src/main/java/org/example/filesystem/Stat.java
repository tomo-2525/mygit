package org.example.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Stat {
    byte[] ctime;
    byte[] ctimeNano;
    byte[] mtime;
    byte[] mtimeNano;
    byte[] dev;
    byte[] ino;
    byte[] mode;
    byte[] uid;
    byte[] gid;

    public Stat(){
        long time = System.currentTimeMillis();
        ctime = ByteBuffer.allocate(4).putInt((int)(time/1000)).array();
        ctimeNano = ByteBuffer.allocate(4).putInt((int)(time%1000)).array();
        mtime = ByteBuffer.allocate(4).putInt((int)(time/1000)).array();
        mtimeNano = ByteBuffer.allocate(4).putInt((int)(time%1000)).array();
        dev = new byte[]{0, 0, 0, 0};
        ino = new byte[]{0, 0, 0, 0};
        mode = ByteBuffer.allocate(4).putInt(33188).array();
        uid = new byte[]{0, 0, 0, 0};
        gid = new byte[]{0, 0, 0, 0};
    }

    public byte[] getBytes() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(ctime);
        outputStream.write(ctimeNano);
        outputStream.write(mtime);
        outputStream.write(mtimeNano);
        outputStream.write(dev);
        outputStream.write(ino);
        outputStream.write(mode);
        outputStream.write(uid);
        outputStream.write(gid);
        return outputStream.toByteArray();
    }
}
