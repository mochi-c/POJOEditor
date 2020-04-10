package jsoneditorparse.parsehandler;

import com.google.common.collect.Maps;
import jsoneditorparse.ConfigEditorFormat;
import jsoneditorparse.annotation.ConfigEditorArray;
import jsoneditorparse.annotation.ConfigEditorDateTimeSelector;
import jsoneditorparse.annotation.ConfigEditorEnumBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class FormatDispatcherParseHandler extends AbstractLinkedParseHandler {

    private static Map<Class, ConfigEditorFormat> simpleFormatMaps = Maps.newHashMap();

    static {
        simpleFormatMaps.put(String.class, ConfigEditorFormat.STRING);
        simpleFormatMaps.put(Character.class, ConfigEditorFormat.STRING);
        simpleFormatMaps.put(char.class, ConfigEditorFormat.STRING);
        simpleFormatMaps.put(Enum.class, ConfigEditorFormat.STRING);
        simpleFormatMaps.put(Boolean.class, ConfigEditorFormat.BOOLEAN);
        simpleFormatMaps.put(boolean.class, ConfigEditorFormat.BOOLEAN);
        simpleFormatMaps.put(Byte.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(byte.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(Short.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(short.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(Integer.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(int.class, ConfigEditorFormat.INTEGER);
        simpleFormatMaps.put(Float.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(float.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(Long.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(long.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(Double.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(double.class, ConfigEditorFormat.NUMBER);
        simpleFormatMaps.put(List.class, ConfigEditorFormat.ARRAY);
        simpleFormatMaps.put(Set.class, ConfigEditorFormat.ARRAY);
    }

    @Override
    AbstractParseHandler linkedHandle() {
        ConfigEditorFormat format = chooseFormat();
        getResult().put("type", format.getType().getName());
        if (format.getFormatName() != null) {
            getResult().put("format", format.getFormatName());
        }
        if (format.getHandler() != null) {
            try {
                return format.getHandler().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("create handler error", e);
            }
        }
        return null;
    }

    ConfigEditorFormat chooseFormat() {
        ConfigEditorFormat format = ConfigEditorFormat.AUTO;
        if (!isClearGenericsClazz()) {  //最优先使用显示指明的Format
            if (getConfigEditorUIMeta() != null) {
                format = getConfigEditorUIMeta().format();
            }
        } else {
            if (getField().getAnnotation(ConfigEditorArray.class) != null) {
                format = getField().getAnnotation(ConfigEditorArray.class).itemFormat();
            } else {
                if (getClazz().isEnum()) {
                    format = ConfigEditorFormat.SELECT;
                }
                if (isClearGenericsClazz() && getField().getAnnotation(ConfigEditorEnumBuilder.class) != null) {
                    format = ConfigEditorFormat.SELECT;
                }
                if (isClearGenericsClazz() && getField().getAnnotation(ConfigEditorDateTimeSelector.class) != null) {
                    format = ConfigEditorFormat.DATE_SELECTOR;
                }
            }
        }
        if (format == ConfigEditorFormat.AUTO) {    //自动解析
            Class clazz = getClazz();
            ConfigEditorFormat ff = simpleFormatMaps.get(clazz);
            if (ff != null) {
                format = ff;
            } else {
                if (clazz.isEnum()) {
                    format = ConfigEditorFormat.SELECT;
                }
                if (clazz.isAssignableFrom(Collection.class)) {
                    format = ConfigEditorFormat.ARRAY;
                }
                if (clazz.isArray()) {
                    format = ConfigEditorFormat.ARRAY;
                }
            }
        }
        if (format == ConfigEditorFormat.AUTO) {    //不属于上述情况则为CLASS
            format = ConfigEditorFormat.OBJECT;
        }
        return format;
    }
}
