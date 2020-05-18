package jsoneditorparse;

import jsoneditorparse.parsehandler.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
@AllArgsConstructor
public enum JsonEditorFormat {

    STRING(null, null, JsonSchemaType.STRING),

    TEXT_AREA("textarea", null, JsonSchemaType.STRING),

    TIME_SELECTOR("datetime-local", TimeSelectorParseHandler.class, JsonSchemaType.STRING),

    DATE_SELECTOR("date", DateSelectorParseHandler.class, JsonSchemaType.STRING),

    INTEGER(null, null, JsonSchemaType.INTEGER),

    NUMBER(null, null, JsonSchemaType.NUMBER),

    BOOLEAN(null, null, JsonSchemaType.BOOLEAN),

    BOOLEAN_CHECK_BOX("checkbox", null, JsonSchemaType.BOOLEAN),

    ARRAY(null, ArrayParseHandler.class, JsonSchemaType.ARRAY),

    TABLE("table", ArrayParseHandler.class, JsonSchemaType.ARRAY),

    TABS("tabs", ArrayParseHandler.class, JsonSchemaType.ARRAY),

    TABS_TOP("tabs-top", ArrayParseHandler.class, JsonSchemaType.ARRAY),

    TAGS("selectize", TagsParseHandler.class, JsonSchemaType.ARRAY),

    SELECT("selectize", EnumBuilderParseHandler.class, JsonSchemaType.STRING),

    OBJECT(null, ClassParseHandler.class, JsonSchemaType.OBJECT),

    AUTO(null, null, null);

    @Getter
    String formatName;

    @Getter
    Class<? extends AbstractParseHandler> handler;

    @Getter
    JsonSchemaType type;
}
