import jsoneditorparse.JsonEditorParserBuilder;
import jsoneditorparse.annotation.ConfigEditorUIMeta;
import jsoneditorparse.fieldfilter.SIMPLE_FIELD_FILTER;

import java.util.List;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class simpleTest {

    public static class Test {
        @ConfigEditorUIMeta
        String a;
        @ConfigEditorUIMeta
        String b;
        String c;
    }

    public static class BB {
        @ConfigEditorUIMeta
        List<Test> array;
    }

    public static void main(String[] args) {
        System.out.println(JsonEditorParserBuilder.create(BB.class).parse().toString());

        System.out.println(JsonEditorParserBuilder.create(BB.class).setFieldFilter(SIMPLE_FIELD_FILTER.EVERY_FIELD).parse().toString());
    }
}
