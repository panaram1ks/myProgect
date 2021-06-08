package application.spring.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("notFound.message");
    }
}
