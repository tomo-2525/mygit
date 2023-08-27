package org.example.objects;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class Blob {
    private int size;
    private String content;

    /**
     *  コンストラクタ
     */
    public Blob(String content){
        this.size = content.length();
        this.content = content;
    }

    /**
     *
     * */
    public void setSize(int size){ this.size = size; }
    public int getSize(){ return this.size;}
    public void setContent(String content){ this.content = content; }
    public String getContent(){ return this.content; }

    //　public Option<self> from は一旦むし

    public byte[] asBytes(){
        String header = String.format("blob %d\0", this.size);
        String store = header + this.content;
        return store.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] calcHash(byte[] input){
        try{
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = sha1.digest(input);
        return hashBytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // エラーの場合はnullを返すか、適切なエラーハンドリングを行う
        }
    }
}
