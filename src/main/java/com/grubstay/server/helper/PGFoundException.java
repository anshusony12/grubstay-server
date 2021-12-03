package com.grubstay.server.helper;

public class PGFoundException extends Exception{
    public PGFoundException() {
        super("PG with this Id already Exist! Try with Another Name");
    }

    public PGFoundException(String message) {
        super(message);
    }
}
