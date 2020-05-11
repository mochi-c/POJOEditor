# POJO编辑器

POJO编辑器可以根据POJO的Class信息自动生成JosnSchema,搭配依据JsonSchema的可视化编辑工具轻松达到对POJO进行可视化,结构化,带有约束与校验的创建或编辑.

这可以使得非开发人员在编辑一些POJO对象描述的配置或者原始数据时更加直观高效,并能在一定程度上减少发生错误的情况.

## 使用示例

以下示例包含了编辑器支持的大部分类型
<https://mochi-c.github.io/POJOEditor/>

该示例所对应的pojo如下

```java
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
```

## 使用方式

编辑器由schemaParser和jsonEditor组合而成, 两者分别部署在服务之中,以下为一个典型的配置编辑流程参考

1. 后端服务从配置中心加载配置解析为pojo供上层使用

2. 后端解析pojo对应class的schema并由前端页面生成控件

3. 后端将该pojo实例json序列化并在编辑页面加载

4. 编辑页面完成修改由控件生成json数据回传到后端服务

5. 后端服务根据json数据反序列化得到新的配置信息并更新配置中心

### schemaParser

用于java端解析pojo格式生成jsonSchema,配合注解和过滤器可以实现field的过滤和交互方式的指定

### 引入

```xml
<dependency>
  <groupId>com.mochi-cell.tools</groupId>
  <artifactId>jsoneditorschemaparser</artifactId>
  <version>0.0.1</version>
</dependency>
```

#### 使用

```java
JsonEditorParserBuilder.create(ExampleClass.class).parse()
JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilter(SIMPLE_FIELD_FILTER.EVERY_FIELD).parse()
```

### jsonEditor

提供可视化编辑的js控件,使用了开源的[json-editor](https://github.com/json-editor/json-editor),部分交互形式需要搭配selectize和flatpickr插件

#### 引入

npm

```javascript
npm install @json-editor/json-editor
```

cdn

```javascript
<script src="https://cdn.jsdelivr.net/npm/@json-editor/json-editor@latest/dist/jsoneditor.min.js"></script>
```

#### 使用

```javascript
const element = document.getElementById('editor_holder');
const editor = new JSONEditor(element, options);
```

#### 详细信息

<https://github.com/json-editor/json-editor>

## ConfigEditorUIMeta

ConfigEditorUIMeta注解为field提供一些基本信息的配置,在ONLY_ANNOTATION的过滤器中也用作参与编辑的标志

| 成员 | 含义 | 默认值 |
| ---- | ---- | ---- |
| guide | 元素名称边上的 ? 提示,鼠标悬停上去会展开展示 | null 不显示提示
| desc | 描述信息,通常出现在编辑框下方 | null 不显示描述
| format | 用来指明需要使用的交互类型 | AUTO 根据类型自动转换
| title | 该元素的别名 | null 使用成员的命名 |

## 支持的交互方式(FORMAT)与JAVA类型

| 类型 | 效果 | Json类型 | 默认类型转换 | 类型限制 | 扩展 |
| --- |  --- | ------- | ----------- |-------- | ---- |
|STRING|简单的文本框|STRING|String|
|TEXT_AREA|可调整大小的文本框,适合较多的文字内容|STRING|
|TIME_SELECTOR|时间选择器|STRING| | |ConfigEditorDateTimeSelector|
|DATE_SELECTOR|日期选择器|STRING| | |ConfigEditorDateTimeSelector|
|INTEGER|整数|NUMBER|byte,short,int,long,及其包装类|
|NUMBER|数字|NUMBER|float,double,及其包装类|
|BOOLEAN|True,False下拉选择|BOOLEAN|boolean,Boolean|
|BOOLEAN_CHECK_BOX|勾选框|BOOLEAN|
|ARRAY|完全展开的数组|ARRAY|List/Set|List/Set|ConfigEditorArray|
|TABLE|使用统一的表头展开的表格类型的数组|ARRAY| |List/Set|ConfigEditorArray| 
|TABS|一次查看一个元素,重复使用视图的TAB页类型数组|ARRAY| |List/Set|ConfigEditorArray|
|SELECT|下拉选择框,可以直接输入进行快捷搜索|STRING|Enum/@ConfigEditorEnumBuilder|Enum/@ConfigEditorEnumBuilder|ConfigEditorEnumBuilder
|TAGS|标签选择器|ARRAY[STRING]|List+Format中的SELECT类型|List+Format中的SELECT类型|ConfigEditorEnumBuilder
|OBJECT|递归解析Class对象,一般由解析器自动调用|OBJECT|其余类型
|AUTO|根据默认类型转换自动选择


