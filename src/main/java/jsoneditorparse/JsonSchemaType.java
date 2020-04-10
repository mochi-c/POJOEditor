package jsoneditorparse;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public enum JsonSchemaType {

    OBJECT("object"),

    ARRAY("array"),

    INTEGER("integer"),

    NUMBER("number"),

    STRING("string"),

    BOOLEAN("boolean"),

    ;

    private String name;

    JsonSchemaType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
