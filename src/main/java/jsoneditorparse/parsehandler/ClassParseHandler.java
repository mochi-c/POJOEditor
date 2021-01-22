package jsoneditorparse.parsehandler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import jsoneditorparse.JsonSchemaParseException;
import jsoneditorparse.SchemaParser;
import jsoneditorparse.JsonEditorParserBuilder;
import jsoneditorparse.fieldfilter.IFieldFilter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ClassParseHandler extends AbstractParseHandler {

    public Field[] getFields(Class<?> clazz) throws JsonSchemaParseException {
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
            throw new JsonSchemaParseException("get fields error:" + e.getMessage(), e);
        }
        return filedSet.toArray(new Field[]{});
    }

    boolean match(Field field) {
        List<IFieldFilter> fieldFilters = getContext().getConfig().getFieldFilter();
        for (IFieldFilter fieldFilter : fieldFilters) {
            if (!fieldFilter.match(field)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void handle() throws JsonSchemaParseException {
        JSONObject properties = new JSONObject(true);
        for (Field field : getFields(getClazz())) {
            SchemaParser parser = JsonEditorParserBuilder.SimpleParser(field, false, getContext().getConfig());
            properties.put(field.getName(), parser.parse());
        }
        getResult().put("properties", properties);
    }
}
