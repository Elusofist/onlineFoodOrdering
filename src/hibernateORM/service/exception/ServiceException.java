package hibernateORM.service.exception;

public class ServiceException extends Exception{
    public static String nullInputs = "You have null inputs!";
    public static String unfilledRequiredFields = "Required fields aren't filled.";
    public static String nullFoodOrdering = "FoodOrdering class object is not created.";

    public ServiceException(String message) {
        super(message);
    }
}
