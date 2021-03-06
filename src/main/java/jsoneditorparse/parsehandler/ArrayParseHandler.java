package jsoneditorparse.parsehandler;


import jsoneditorparse.JsonSchemaParseException;
import jsoneditorparse.SchemaParser;
import jsoneditorparse.JsonEditorParserBuilder;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ArrayParseHandler extends AbstractParseHandler {
    @Override
    public void handle() throws JsonSchemaParseException {
        SchemaParser parser = JsonEditorParserBuilder.SimpleParser(getField(), true, getContext().getConfig());
        parser.addHandlerLast(new ArrayItemTitleParseHandler());
        getResult().put("items", parser.parse());
    }
}
