package jsoneditorparse.parsehandler;

import jsoneditorparse.JsonSchemaParseException;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public abstract class AbstractLinkedParseHandler extends AbstractParseHandler {
    @Override
    public void handle() throws JsonSchemaParseException {
        AbstractParseHandler next = linkedHandle();
        if (next != null) {
            addHandlerFirst(next);
        }
    }

    abstract AbstractParseHandler linkedHandle() throws JsonSchemaParseException;
}
