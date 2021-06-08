package application.spring.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("uniqueName.message");
    }
}
