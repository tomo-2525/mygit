package org.example.objects;

import java.util.List;

public class Tree {

    public List<File> contents;

    public class File{
        public int mode;
        public String name;
        public byte[] hash;
    }

    // File
    public byte[] encode(){

    }

    // Tree
    public byte[] asByte(){

    }
}
