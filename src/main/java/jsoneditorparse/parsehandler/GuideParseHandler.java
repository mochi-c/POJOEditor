package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class GuideParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        if (getConfigEditorUIMeta() != null && getConfigEditorUIMeta().guide().length() > 0) {
            JSONObject options = Optional.fromNullable(getResult().getJSONObject("options")).or(new JSONObject());
            options.put("infoText", getConfigEditorUIMeta().guide());
            getResult().put("options", options);
        }
    }
}
