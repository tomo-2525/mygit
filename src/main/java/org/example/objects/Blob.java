package org.example.objects;

import org.example.filesystem.GitFileSystem;

import java.nio.charset.StandardCharsets;


public class Blob {

    public String objectType = "blob";
    private final int size;
    private final String content;

    /**
     *  コンストラクタ
     */
    public Blob(String content){
        this.size = content.length();
        this.content = content;
    }

    // TODO
    // .git/objectの中を与えたら、それからblobオブジェクト作成
    public Blob(byte[] bytes){
        this.content = new String(bytes, StandardCharsets.UTF_8);
        this.size = content.length();
    }

    /**
     * 書き込むためのフォーマットにする
     */
    public byte[] asBytes(){
        String header = String.format("%s %d\0", objectType, size);
        String store = header + this.content;
        return store.getBytes(StandardCharsets.UTF_8);
    }

    public String asString(){
        String header = String.format("%s %d\0", objectType, size);
        return header + this.content;
    }

    /**
     * hash値を計算する(これはいらないかも)
     */
    public byte[] calcHash() throws Exception {
        return GitFileSystem.calcHash(asBytes());
    }

    /**
     * zlibで圧縮する(これはいらないかも)
     * */
    public byte[] applyZlib() throws Exception{
        return GitFileSystem.compress(asBytes());
    }

    public int getSize(){ return size;}
    public String getContent(){return content;}

    @Override
    public String toString(){
        return content;
    }
}
