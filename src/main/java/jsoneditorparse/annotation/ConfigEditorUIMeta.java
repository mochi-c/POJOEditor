package jsoneditorparse.annotation;


import jsoneditorparse.ConfigEditorFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ConfigEditorUIMeta {

    /*默认没有显式指定format, 保持type默认的format*/
    ConfigEditorFormat format() default ConfigEditorFormat.AUTO;

    /* field的别名, 用于UI显示 */
    String title() default "";

    /* field的注释, 用于UI显示 */
    String desc() default "";

    /* 问号提示, 用于UI显示 */
    String guide() default "";
}
