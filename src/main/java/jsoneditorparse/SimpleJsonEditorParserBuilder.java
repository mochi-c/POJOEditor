package jsoneditorparse;

import jsoneditorparse.annotation.ConfigEditorUIMeta;
import jsoneditorparse.parsehandler.DesParseHandler;
import jsoneditorparse.parsehandler.FormatDispatcherParseHandler;
import jsoneditorparse.parsehandler.GuideParseHandler;
import jsoneditorparse.parsehandler.TitleParseHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class SimpleJsonEditorParserBuilder {

    static public SchemaParser SimpleParser(Class clazz) {
        SchemaParser schemaParser = new SchemaParser(clazz);
        baseHandler(schemaParser);
        return schemaParser;
    }

    static public SchemaParser SimpleParser(Field field, boolean clearGenericsClass) {
        SchemaParser schemaParser = new SchemaParser(field, clearGenericsClass);
        baseHandler(schemaParser);
        return schemaParser;
    }

    private static void baseHandler(SchemaParser schemaParser) {
        schemaParser.addHandlerLast(new FormatDispatcherParseHandler());  //format可能覆盖type
        schemaParser.addHandlerLast(new GuideParseHandler());
        schemaParser.addHandlerLast(new DesParseHandler());
        schemaParser.addHandlerLast(new TitleParseHandler());
    }

    public static class Test {
        @ConfigEditorUIMeta
        String a;
        @ConfigEditorUIMeta
        String b;
    }

    public static class BB {
        @ConfigEditorUIMeta
        List<Test> array;
    }

}
