package application.exceptions;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String msg){
        super(msg);
    }
}
