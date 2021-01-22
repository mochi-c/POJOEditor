package jsoneditorparse.parsehandler;

import jsoneditorparse.JsonEditorFormat;
import jsoneditorparse.JsonSchemaParseException;
import jsoneditorparse.annotation.JsonEditorArray;
import jsoneditorparse.annotation.JsonEditorDateTimeSelector;
import jsoneditorparse.annotation.JsonEditorEnumBuilder;

import java.util.Collection;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class FormatDispatcherParseHandler extends AbstractLinkedParseHandler {

    @Override
    AbstractParseHandler linkedHandle() throws JsonSchemaParseException {
        JsonEditorFormat format = chooseFormat();
        getResult().put("type", format.getType().getName());
        if (format.getFormatName() != null) {
            getResult().put("format", format.getFormatName());
        }
        if (format.getHandler() != null) {
            try {
                return format.getHandler().newInstance();
            } catch (Exception e) {
                throw new JsonSchemaParseException("create handler error: " + e.getMessage(), e);
            }
        }
        return null;
    }

    JsonEditorFormat chooseFormat() {
        JsonEditorFormat format = JsonEditorFormat.AUTO;
        if (!isClearGenericsClazz()) {  //最优先使用显示指明的Format
            if (getConfigEditorUIMeta() != null) {
                format = getConfigEditorUIMeta().format();
            }
        } else {
            if (getField().getAnnotation(JsonEditorArray.class) != null) {
                format = getField().getAnnotation(JsonEditorArray.class).itemFormat();
            } else {
                if (getClazz().isEnum()) {
                    format = JsonEditorFormat.SELECT;
                }
                if (getField().getAnnotation(JsonEditorEnumBuilder.class) != null) {
                    format = JsonEditorFormat.SELECT;
                }
                if (getField().getAnnotation(JsonEditorDateTimeSelector.class) != null) {
                    format = JsonEditorFormat.DATE_SELECTOR;
                }
            }
        }
        if (format == JsonEditorFormat.AUTO) {    //自动解析
            Class clazz = getClazz();
            JsonEditorFormat ff = getContext().getConfig().getFormatDictionary().get(clazz);
            if (ff != null) {
                format = ff;
            } else {
                if (clazz.isEnum()) {
                    format = JsonEditorFormat.SELECT;
                }
                if (clazz.isAssignableFrom(Collection.class)) {
                    format = JsonEditorFormat.ARRAY;
                }
                if (clazz.isArray()) {
                    format = JsonEditorFormat.ARRAY;
                }
            }
        }
        if (format == JsonEditorFormat.AUTO) {    //不属于上述情况则为CLASS
            format = JsonEditorFormat.OBJECT;
        }
        return format;
    }
}
