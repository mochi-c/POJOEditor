package jsoneditorparse.annotation;

import jsoneditorparse.JsonEditorFormat;

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
@Target(ElementType.FIELD)
public @interface JsonEditorArray {

    String titleItem() default "";

    boolean showIndex() default true;

    String titleTemplate() default "";

    JsonEditorFormat itemFormat() default JsonEditorFormat.AUTO;
}
