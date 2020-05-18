package jsoneditorparse.fieldfilter;

import jsoneditorparse.annotation.JsonEditorUIMeta;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public enum SIMPLE_FIELD_FILTER implements IFieldFilter {

    ONLY_ANNOTATION {
        @Override
        public boolean match(Field field) {
            return field.getAnnotation(JsonEditorUIMeta.class) != null;
        }
    },

    EVERY_FIELD {
        @Override
        public boolean match(Field field) {
            return true;
        }
    }

}
