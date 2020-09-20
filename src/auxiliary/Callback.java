package auxiliary;

public class Callback {
    private final String message;
    private final boolean success;

    public Callback(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
