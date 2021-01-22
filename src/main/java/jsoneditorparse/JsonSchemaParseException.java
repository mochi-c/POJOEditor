package jsoneditorparse;

/**
 * Description:
 * User: Mochi
 * Date: 2021-01-22
 * version: 1.0
 */
public class JsonSchemaParseException extends Exception {

    public JsonSchemaParseException() {
        super();
    }

    public JsonSchemaParseException(String message) {
        super(message);
    }

    public JsonSchemaParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSchemaParseException(Throwable cause) {
        super(cause);
    }

    protected JsonSchemaParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
