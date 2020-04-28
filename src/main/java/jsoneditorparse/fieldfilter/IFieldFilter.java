package jsoneditorparse.fieldfilter;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public interface IFieldFilter {

    /**
     * 是否过滤掉该field
     */
    boolean ignore(Field field);

}
