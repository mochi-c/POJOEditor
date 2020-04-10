package jsoneditorparse.parsehandler;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public abstract class AbstractLinkedParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        AbstractParseHandler next = linkedHandle();
        if (next != null) {
            addHandlerFirst(next);
        }
    }

    abstract AbstractParseHandler linkedHandle();
}
