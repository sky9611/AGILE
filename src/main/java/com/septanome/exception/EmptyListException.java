package com.septanome.exception;

public class EmptyListException extends ConstructorException{
    public EmptyListException(String troncon_list_is_empty) {
        super(troncon_list_is_empty);
    }
}
