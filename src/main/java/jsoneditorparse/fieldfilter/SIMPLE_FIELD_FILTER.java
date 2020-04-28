package jsoneditorparse.fieldfilter;

import jsoneditorparse.annotation.ConfigEditorUIMeta;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public enum SIMPLE_FIELD_FILTER implements IFieldFilter {

    ONLY_ANNOTATION {
        @Override
        public boolean ignore(Field field) {
            return field.getAnnotation(ConfigEditorUIMeta.class) == null;
        }
    },

    EVERY_FIELD {
        @Override
        public boolean ignore(Field field) {
            return false;
        }
    }

}
