package exception;

public class ItemIdAlreadyExistException extends Exception{
    public ItemIdAlreadyExistException(String message){
        super(message);
    }
}
