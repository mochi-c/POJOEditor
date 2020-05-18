package jsoneditorparse.annotation;

import jsoneditorparse.formateutil.IEnumItemBuilder;

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
public @interface JsonEditorEnumBuilder {

    Class<? extends IEnumItemBuilder> itemsBuilder();

}
