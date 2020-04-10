package jsoneditorparse.parsehandler;

import jsoneditorparse.annotation.ConfigEditorArray;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ArrayItemTitleParseHandler extends AbstractParseHandler {


    @Override
    public void handle() {
        ConfigEditorArray annotation = getField().getAnnotation(ConfigEditorArray.class);
        if (annotation != null) {
            String template = "";
            if (annotation.titleTemplate().length() > 0) {
                template = annotation.titleTemplate();
            } else {
                if (annotation.showIndex()) {
                    template = "{{i}} ";
                }
                if (annotation.titleItem().length() > 0) {
                    template += "{{self." + annotation.titleItem() + "}}";
                }
            }
            getResult().put("headerTemplate", template);
        }
    }
}
