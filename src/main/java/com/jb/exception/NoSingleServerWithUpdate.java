package com.jb.exception;

public class NoSingleServerWithUpdate extends Exception {
    public NoSingleServerWithUpdate(String message){
        super(message);
    }
}
