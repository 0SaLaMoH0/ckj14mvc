package com.example.demo.exeptions;

public class NoSuchNoteExeption extends RuntimeException{
    public NoSuchNoteExeption(int id){
        super("Note with id "+id+" does not exist.");
    }
}
