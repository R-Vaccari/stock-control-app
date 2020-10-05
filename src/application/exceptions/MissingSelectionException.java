package application.exceptions;

public class MissingSelectionException extends RuntimeException {

    public MissingSelectionException(String msg){
        super(msg);
    }
}
