package jsoneditorparse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jsoneditorparse.fieldfilter.IFieldFilter;
import jsoneditorparse.fieldfilter.SIMPLE_FIELD_FILTER;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
@Data
public class Config {

    List<IFieldFilter> fieldFilter = Lists.newArrayList(SIMPLE_FIELD_FILTER.ONLY_ANNOTATION);

    Map<Class<?>, JsonEditorFormat> formatDictionary = Maps.newHashMap();

    public Config() {
        formatDictionary.put(String.class, JsonEditorFormat.STRING);
        formatDictionary.put(Character.class, JsonEditorFormat.STRING);
        formatDictionary.put(char.class, JsonEditorFormat.STRING);
        formatDictionary.put(Enum.class, JsonEditorFormat.STRING);
        formatDictionary.put(Boolean.class, JsonEditorFormat.BOOLEAN);
        formatDictionary.put(boolean.class, JsonEditorFormat.BOOLEAN);
        formatDictionary.put(Byte.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(byte.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(Short.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(short.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(Integer.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(int.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(Long.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(long.class, JsonEditorFormat.INTEGER);
        formatDictionary.put(Float.class, JsonEditorFormat.NUMBER);
        formatDictionary.put(float.class, JsonEditorFormat.NUMBER);
        formatDictionary.put(Double.class, JsonEditorFormat.NUMBER);
        formatDictionary.put(double.class, JsonEditorFormat.NUMBER);
        formatDictionary.put(List.class, JsonEditorFormat.ARRAY);
        formatDictionary.put(Set.class, JsonEditorFormat.ARRAY);
    }

}
