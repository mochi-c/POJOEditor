import com.google.common.collect.Maps;
import jsoneditorparse.ConfigEditorFormat;
import jsoneditorparse.annotation.ConfigEditorArray;
import jsoneditorparse.annotation.ConfigEditorDateTimeSelector;
import jsoneditorparse.annotation.ConfigEditorEnumBuilder;
import jsoneditorparse.annotation.ConfigEditorUIMeta;
import jsoneditorparse.formateutil.IEnumItemBuilder;


import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class ExampleClass {

    String autoField;

    //名称提示等
    @ConfigEditorUIMeta(title = "myTitle", desc = "myDesc", guide = "myGuide")
    String titleDesGuide;

    @ConfigEditorUIMeta
    String string;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TEXT_AREA)
    String textarea;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TIME_SELECTOR)
    @ConfigEditorDateTimeSelector(resultFormat = "m-d H:i:S")
    String timeWithSpecialResult;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.DATE_SELECTOR)
    String date;

    @ConfigEditorUIMeta
    int integer;

    @ConfigEditorUIMeta
    double number;

    @ConfigEditorUIMeta
    SimpleEnum simpleEnumSelection;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.SELECT)
    @ConfigEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    String dynamicEnumSelection;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TAGS)
    List<SimpleEnum> simpleEnumTags;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TAGS)
    @ConfigEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    List<String> dynamicEnumTags;

    @ConfigEditorUIMeta
    SimpleClass simpleClass;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TABLE)
    List<SimpleClass> table;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TABS)
    @ConfigEditorArray(titleTemplate = "myName A:{{self.objA}} B:{{self.objB}}")
    List<SimpleClass> specialNameTabs;

    @ConfigEditorUIMeta
    List<SimpleClass> array;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TABLE)
    @ConfigEditorArray(itemFormat = ConfigEditorFormat.SELECT)  //Can be omitted
    @ConfigEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    List<String> arrayStringToEnum;

    @ConfigEditorUIMeta(format = ConfigEditorFormat.TABLE)
    @ConfigEditorArray(itemFormat = ConfigEditorFormat.TIME_SELECTOR)   //Can be omitted
    @ConfigEditorDateTimeSelector                                       //Can be omitted
            List<String> arrayStringToTime;

    public static class DynamicEnum implements IEnumItemBuilder {
        @Override
        public Map<String, String> getItems() {
            Map<String, String> items = Maps.newHashMap();
            items.put("Now", "Now");
            items.put("TIME", System.currentTimeMillis() + "");
            items.put("NAME", "SPECIALNAME");
            return items;
        }
    }

    public static enum SimpleEnum {
        enumA,
        enumB,
        @ConfigEditorUIMeta(title = "SpecialName")
        enumC
    }

    public static class SimpleClass {
        @ConfigEditorUIMeta
        String objA;
        @ConfigEditorUIMeta
        String objB;
    }

}
