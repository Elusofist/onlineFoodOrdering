package ordering;

public class BusinessException extends Exception{
    public static String nullInputs = "You have null inputs!";
    public static String unfilledRequiredFields = "Required fields aren't filled.";

    BusinessException(String message) {
        super(message);
    }
}
