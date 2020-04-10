package jsoneditorparse.parsehandler;


import jsoneditorparse.SchemaParser;
import jsoneditorparse.SimpleJsonEditorParserBuilder;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ArrayParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        SchemaParser parser = SimpleJsonEditorParserBuilder.SimpleParser(getField(), true);
        parser.addHandlerLast(new ArrayItemTitleParseHandler());
        getResult().put("items", parser.parse());
    }
}
