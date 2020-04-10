package jsoneditorparse.parsehandler;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class TitleParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        if (getConfigEditorUIMeta() != null) {
            if (getConfigEditorUIMeta().title() != null && getConfigEditorUIMeta().title().length() > 0) {
                getResult().put("title", getConfigEditorUIMeta().title());
                return;
            }
        }
        if (getField() != null) {
            getResult().put("title", getField().getName());
            return;
        }
        getResult().put("title", getClazz().getSimpleName());

    }
}
