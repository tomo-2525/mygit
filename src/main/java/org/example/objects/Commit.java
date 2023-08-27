package org.example.objects;

import java.util.Date;
import java.util.Optional;

public class Commit {

    public String tree;
    public Optional<String> parent;
    public User author;
    public User committer;
    public String message;

    public class User{
    public String name;
    public String email;
    public Date ts;
    }


    public

}
