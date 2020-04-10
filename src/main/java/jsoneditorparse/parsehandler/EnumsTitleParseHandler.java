package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import jsoneditorparse.annotation.ConfigEditorUIMeta;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

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
        JSONObject options = Optional.fromNullable(getResult().getJSONObject("options")).or(new JSONObject());
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
                ConfigEditorUIMeta annotation = field.getAnnotation(ConfigEditorUIMeta.class);
                if (annotation != null) {
                    if (annotation.title() != null && annotation.title().length() > 0) {
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
