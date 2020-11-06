package hibernateORM.data.exception;

public class DatabaseException extends Exception {
    public final static String NULL_RECORD ="Proper record not found!";
    public final static String NULL_Entity ="Required Entity is Empty!";
    public static String WRONG_PASSWORD = "Entered password isn't correct.";


    public DatabaseException(String errMsg) {
        super(errMsg);
    }
}
