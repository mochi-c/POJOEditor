package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import jsoneditorparse.annotation.JsonEditorUIMeta;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
@AllArgsConstructor
public class EnumsTitleParseHandler extends AbstractParseHandler {
    List<String> titles;

    @Override
    public void handle() {
        JSONObject options = Optional.ofNullable(getResult().getJSONObject("options")).orElse(new JSONObject());
        if (getClazz().isEnum()) {
            options.put("enum_titles", getTilesFromEnum(getClazz()));
        } else {
            options.put("enum_titles", getTilesFromField(getField()));
        }
        getResult().put("options", options);
    }

    List<String> getTilesFromEnum(Class clazz) {
        List<String> list = Lists.newArrayList();
        for (Field field : clazz.getFields()) {
            if (field.isEnumConstant()) {
                JsonEditorUIMeta annotation = field.getAnnotation(JsonEditorUIMeta.class);
                if (annotation != null) {
                    annotation.title();
                    if (annotation.title().length() > 0) {
                        list.add(annotation.title());
                        continue;
                    }
                }
                list.add(field.getName());
            }
        }
        return list;
    }

    List<String> getTilesFromField(Field field) {
        return titles;
    }
}
