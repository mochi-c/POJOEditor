package jsoneditorparse.fieldfilter;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public interface IFieldFilter {

    /**
     * 该field是否生效,参与编辑
     * @return  true    生效
     *          false   不生效
     */
    boolean match(Field field);

}
