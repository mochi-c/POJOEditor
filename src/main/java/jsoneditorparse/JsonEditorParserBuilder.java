package jsoneditorparse;

import jsoneditorparse.parsehandler.*;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class JsonEditorParserBuilder {

    public static SchemaParser create(Class<?> clazz) {
        SchemaParser schemaParser = new SchemaParser(clazz);
        baseHandler(schemaParser);
        return schemaParser;
    }

    public static SchemaParser SimpleParser(Field field, boolean clearGenericsClass, Config config) {
        SchemaParser schemaParser = new SchemaParser(field, clearGenericsClass, config);
        baseHandler(schemaParser);
        return schemaParser;
    }

    private static void baseHandler(SchemaParser schemaParser) {
        schemaParser.addHandlerLast(new FormatDispatcherParseHandler());  //format可能覆盖type
        schemaParser.addHandlerLast(new DependenceParseHandler());
        schemaParser.addHandlerLast(new GuideParseHandler());
        schemaParser.addHandlerLast(new DesParseHandler());
        schemaParser.addHandlerLast(new TitleParseHandler());
    }

}
