package com.grubstay.server.helper;

public class UserFoundException extends Exception{
    public UserFoundException(){
        super("User with this Username is alread exist in DB ! Pls try with another name");
    }

    public UserFoundException(String msg){
        super(msg);
    }
}
