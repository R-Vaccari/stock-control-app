package application.exceptions;

public class UnrecognizedUserException extends RuntimeException {

    public UnrecognizedUserException(String msg){
        super(msg);
    }
}
