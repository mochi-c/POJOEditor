package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import jsoneditorparse.annotation.JsonEditorDependence;

import java.util.Optional;

/**
 * Description:
 * User: Mochi
 * Date: 2021-01-22
 * version: 1.0
 */
public class DependenceParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        if (getField() != null) {
            JsonEditorDependence[] dependencies = getField().getAnnotationsByType(JsonEditorDependence.class);
            if (dependencies != null && dependencies.length > 0) {
                JSONObject dependenciesObj = new JSONObject();
                for (JsonEditorDependence x : dependencies) {
                    dependenciesObj.put(x.dependenceKey(), x.dependenceValue());
                }
                JSONObject options = Optional.ofNullable(getResult().getJSONObject("options")).orElse(new JSONObject());
                options.put("dependencies", dependenciesObj);
                getResult().put("options", options);
            }
        }
    }
}