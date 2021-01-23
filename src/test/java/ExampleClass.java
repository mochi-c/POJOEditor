import com.google.common.collect.Maps;
import jsoneditorparse.JsonEditorFormat;
import jsoneditorparse.annotation.*;
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
    @JsonEditorUIMeta(title = "myTitle", desc = "myDesc", guide = "myGuide")
    String titleDesGuide;

    @JsonEditorUIMeta
    String string;

    @JsonEditorUIMeta(format = JsonEditorFormat.TEXT_AREA)
    String textarea;

    @JsonEditorUIMeta(format = JsonEditorFormat.TIME_SELECTOR)
    @JsonEditorDateTimeSelector(resultFormat = "m-d H:i:S")
    String timeWithSpecialResult;

    @JsonEditorUIMeta(format = JsonEditorFormat.DATE_SELECTOR)
    String date;

    @JsonEditorUIMeta
    int integer;

    @JsonEditorUIMeta
    double number;

    @JsonEditorUIMeta
    @JsonEditorDependence(dependenceKey = "simpleEnumSelection", dependenceValue = "enumA")
    @JsonEditorDependence(dependenceKey = "dynamicEnumSelection", dependenceValue = "Now")
    String dependenceItem;

    @JsonEditorUIMeta
    SimpleEnum simpleEnumSelection;

    @JsonEditorUIMeta(format = JsonEditorFormat.SELECT)
    @JsonEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    String dynamicEnumSelection;

    @JsonEditorUIMeta(format = JsonEditorFormat.TAGS)
    List<SimpleEnum> simpleEnumTags;

    @JsonEditorUIMeta(format = JsonEditorFormat.TAGS)
    @JsonEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    List<String> dynamicEnumTags;

    @JsonEditorUIMeta
    SimpleClass simpleClass;

    @JsonEditorUIMeta(format = JsonEditorFormat.TABLE)
    List<SimpleClass> table;

    @JsonEditorUIMeta(format = JsonEditorFormat.TABS)
    @JsonEditorArray(titleTemplate = "myName A:{{self.objA}} B:{{self.objB}}")
    List<SimpleClass> specialNameTabs;

    @JsonEditorUIMeta
    List<SimpleClass> array;

    @JsonEditorUIMeta(format = JsonEditorFormat.TABLE)
    @JsonEditorArray(itemFormat = JsonEditorFormat.SELECT)  //Can be omitted
    @JsonEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    List<String> arrayStringToEnum;

    @JsonEditorUIMeta(format = JsonEditorFormat.TABLE)
    @JsonEditorArray(itemFormat = JsonEditorFormat.TIME_SELECTOR)   //Can be omitted
    @JsonEditorDateTimeSelector                                       //Can be omitted
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
        @JsonEditorUIMeta(title = "SpecialName")
        enumC
    }

    public static class SimpleClass {
        @JsonEditorUIMeta
        String objA;
        @JsonEditorUIMeta
        String objB;
    }

}
