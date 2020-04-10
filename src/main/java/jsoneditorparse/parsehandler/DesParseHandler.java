package jsoneditorparse.parsehandler;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class DesParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        if (getConfigEditorUIMeta() != null) {
            if (getConfigEditorUIMeta().desc() != null && getConfigEditorUIMeta().desc().length() > 0) {
                getResult().put("description", getConfigEditorUIMeta().desc());
            }
        }
    }
}
