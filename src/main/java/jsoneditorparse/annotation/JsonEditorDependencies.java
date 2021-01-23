package jsoneditorparse.annotation;

import java.lang.annotation.*;

/**
 * Description:
 * User: Mochi
 * Date: 2021-01-22
 * version: 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonEditorDependencies {

    JsonEditorDependence[] value();
}
