import com.google.common.collect.Lists;
import jsoneditorparse.JsonEditorFormat;
import jsoneditorparse.JsonEditorParserBuilder;
import jsoneditorparse.fieldfilter.SIMPLE_FIELD_FILTER;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class simpleTest {

    public static void main(String[] args) {
        System.out.println(JsonEditorParserBuilder.create(ExampleClass.class).parse().toString());

        System.out.println(JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilters(Lists.newArrayList(SIMPLE_FIELD_FILTER.EVERY_FIELD)).addFormatDictionary(String.class, JsonEditorFormat.TEXT_AREA).parse().toString());
    }
}
