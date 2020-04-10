package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import jsoneditorparse.SchemaParser;
import jsoneditorparse.SimpleJsonEditorParserBuilder;
import jsoneditorparse.annotation.ConfigEditorUIMeta;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ClassParseHandler extends AbstractParseHandler {

    public Field[] getFields(Class clazz) {
        Set<Field> filedSet = Sets.newLinkedHashSet();
        try {
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {//获取本身和父级对象
                Field[] fields = clazz.getDeclaredFields();//获取所有私有字段
                for (Field field : fields) {
                    if (match(field)) {
                        filedSet.add(field);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filedSet.toArray(new Field[]{});
    }

    boolean match(Field field) {
        if (field.getAnnotation(ConfigEditorUIMeta.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void handle() {
        JSONObject properties = new JSONObject(true);
        for (Field field : getFields(getClazz())) {
            SchemaParser parser = SimpleJsonEditorParserBuilder.SimpleParser(field, false);
            properties.put(field.getName(), parser.parse());
        }
        getResult().put("properties", properties);
    }
}
