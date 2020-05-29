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
  <version>0.1.1</version>
</dependency>
```

#### 使用

```java
JsonEditorParserBuilder.create(ExampleClass.class).parse();
JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilters(Lists.newArrayList(SIMPLE_FIELD_FILTER.EVERY_FIELD)).addFormatDictionary(String.class, JsonEditorFormat.TEXT_AREA).parse();
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

## @JsonEditorUIMeta

JsonEditorUIMeta注解为field或class提供一些基本信息的配置,在ONLY_ANNOTATION的过滤器中也用作参与编辑的标志,对于没有标明该注解的成员,参与解析时使用默认值

| 成员 | 含义 | 默认值 |
| ---- | ---- | ---- |
| guide | 元素名称边上的 ? 提示,鼠标悬停上去会展开展示 | null 不显示提示
| desc | 描述信息,通常出现在编辑框下方 | null 不显示描述
| format | 用来指明需要使用的交互类型 | AUTO 根据类型自动转换
| title | 该元素的别名 | null 使用成员的命名 |

## 交互方式(FORMAT)

对于各个字段的交互形式,可以通过JsonEditorUIMeta中的format来显示的指定.对于没有显示的指定的,解析器可以根据field的class自动转换,自动转换默认支持java的各基本类型和包装类,同时使用者也可以在配置中自己添加class到format的映射,默认的映射规则也是可以被取代的.

对于一些特殊的FORMAT存在对field的class限制,因为要从中获取一些必要的信息.还有一些format提供了一定的扩展功能,可以通过使用追加的注解来实现

前端页面编辑器中生成的json数据各个成员的类型与format是相对应的,对于一些特殊的类型,使用者需要自己处理前端页面编辑器得到的json数据到pojo的转换

### 支持的交互方式(FORMAT)

| 类型 | 效果 | Json类型 |  类型限制 | 扩展 |
| --- |  --- | ------- | -------- | ---- |
|STRING|简单的文本框|String|×|×
|TEXT_AREA|可调整大小的文本框,适合较多的文字内容|STRING|×|×
|TIME_SELECTOR|时间选择器|STRING|×|√|
|DATE_SELECTOR|日期选择器|STRING|×|√|
|INTEGER|整数|NUMBER|×|×
|NUMBER|数字|NUMBER|×|×
|BOOLEAN|True,False下拉选择|BOOLEAN|×|×
|BOOLEAN_CHECK_BOX|勾选框|BOOLEAN|×|×
|ARRAY|完全展开的数组|ARRAY|√|√|
|TABLE|使用统一的表头展开的表格类型的数组|ARRAY|√|√|
|TABS|一次查看一个元素,重复使用视图的TAB页类型数组,页签在左侧|ARRAY|√|√|
|TABS_TOP|一次查看一个元素,重复使用视图的TAB页类型数组,页签在顶部|ARRAY|√|√|
|SELECT|下拉选择框,可以直接输入进行快捷搜索|STRING|√|√|
|TAGS|标签选择器|ARRAY[STRING]|√|√|
|OBJECT|递归解析Class对象,一般由解析器自动调用|OBJECT|-|-
|AUTO|根据默认类型转换自动选择|-|-|-|

### 默认的FORMAT转换

|JAVA类型|默认FORMAT类型|
| ----- | ------------|
|STRING|STRING|
|byte,short,int,long,及其包装类|INTEGER|
|float,double,及其包装类|NUMBER|
|boolean,Boolean|BOOLEAN|
|List/Set|ARRAY|

### 指定Class到FORMAT的映射
````java
JsonEditorParserBuilder.create(ExampleClass.class).addFormatDictionary(String.class, JsonEditorFormat.TEXT_AREA);
````
### 类型限制的FORMAT

对于ARRAY,TABLE,TABS,TABS_TOP系列的ARRAY类型,要求对应的fiel一定是array,list,set,collection,因为解析器需要查询泛型的信息来递归解析array内部的信息

对于TAGS,要求对应的fiel一定是List<Enum>或者List<String>,对于List<String>还需要配合@ConfigEditorEnumBuilder注解提供枚举项

### FORMAT扩展

#### @JsonEditorArray
对ARRAY,TABLE,TABS,TABS_TOP系列的数组类型提供扩展

|成员|含义|
|----|---|
|titleItem|当List嵌套object时,使用对应object中的成员名作为List元素的别名,titleItem指明object中的成员名|
|showIndex|List元素的的名称中标明该元素在List中的下标,默认为true|
|titleTemplate|当List嵌套object时,List元素的别名的模板,其中i代表下标,self为list中的当前元素, 示例: {{i}} - {{self.xxx}} + {{self.bbb.aaa}}|
|itemFormat|强制指定List中元素的交互类型|

#### @JsonEditorDateTimeSelector
对TIME_SELECTOR,DATE_SELECTOR,时间/日期选择器提供扩展

|成员|含义|
|----|---|
|resultFormat|生成数据时的时间格式, 默认为 Y-m-d H:i:S|

#### @JsonEditorEnumBuilder
在SELECT,TAGS一类需要枚举的FORMAT中将String类型转换为枚举,使用该注解能够实现运行时动态的生成一枚举选项

|成员|含义|
|---|----|
|itemsBuilder|生成枚举项的接口,返回一个Map<String,String> key为枚举的值,value为选择器中显示的别名|

### 一些特殊的FORMAT

对于Array系列的Format可以可以直接使用扩展注解可以改变泛型的类型例如使用JsonEditorDateTimeSelector将list元素内转换为时间选择器,使用JsonEditorEnumBuilder将元素转换为枚举.

对于SELECT,TAGS一类需要枚举的FORMAT中,对于Enum类型可以直接解析.选择框中支持别名,Enum通过在枚举值中使用ConfigEditorUIMeta中的title定义别名,String依靠注解中的构造器指明别名.编辑器中将显示别名,但得到的json数据将是原值

## FIELD过滤器

通过FIELD可以对class内的field进行过滤,用以确认哪些field是参与编辑的,编辑器自带了两个过滤器,其中SIMPLE_FIELD_FILTER是默认的

SIMPLE_FIELD_FILTER.SIMPLE_FIELD_FILTER 所有添加了@JsonEditorUIMeta注解的field参与编辑,其余的不参与
SIMPLE_FIELD_FILTER.EVERY_FIELD 所有成员都参与编辑

同时可以设置多个过滤器,只要有一个过滤器返回该field参与解析即可生效,使用者也可以自己按需要追加或者重置过滤器
````java
    static class MyField implements IFieldFilter {
        @Override
        public boolean match(Field field) {
            /**
             * 该field是否生效,参与编辑
             * true    生效
             * false   不生效
             */
            return true;
        }
    }

    public static void main(String[] args) {
        //重置过滤器
        JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilters(Lists.newArrayList(new MyField()));
        //追加过滤器
        JsonEditorParserBuilder.create(ExampleClass.class).addFieldFilter(new MyField());
    }
````
