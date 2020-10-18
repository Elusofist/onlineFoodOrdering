package database;

public class DBException extends Exception{
    public static String wrongPassword = "Entered password isn't correct.";
    public DBException(String msg) {
        super(msg);
    }
}
