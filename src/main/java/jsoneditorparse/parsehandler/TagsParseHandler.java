package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class TagsParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        addHandlerFirst(new ArrayParseHandler());
        getResult().put("uniqueItems", true);
        JSONObject options = Optional.fromNullable(getResult().getJSONObject("options")).or(new JSONObject());
        options.put("selectize", getTagOptions());
        getResult().put("options", options);
    }

    JSONObject getTagOptions() {
        JSONObject options = new JSONObject();
        options.put("create", false);
        options.put("hideSelected", true);
        return options;
    }
}
