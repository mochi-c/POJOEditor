
English | [简体中文](./README-CN.md)

<h1 align="center">POJO EDITOR</h1>

The POJO editor can automatically generate json schema according to the class information of the POJO, 
that enables developer easily edit the POJO with the visual editing tool based on the JsonSchema.

## Example

The following example shows most of the supported java data types.

<https://mochi-c.github.io/POJOEditor/>

 ![image](https://github.com/mochi-c/POJOEditor/blob/master/docs/view.gif?raw=true)

Here is the pojo corresponding of this example


```java
 public class ExampleClass {
 
     String autoField;
 
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

## How to use

### SchemaParser

JsonEditorParser is used to parse the pojo class information in the java environment and automatically generate json schema. The fields can be filtered. Some attributes and interaction mode can be set by annotations.
### Import

```xml
<dependency>
  <groupId>com.mochi-cell.tools</groupId>
  <artifactId>jsoneditorschemaparser</artifactId>
  <version>0.1.3</version>
</dependency>
```

#### Use

```java
JsonEditorParserBuilder.create(ExampleClass.class).parse();
JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilters(Lists.newArrayList(SIMPLE_FIELD_FILTER.EVERY_FIELD)).addFormatDictionary(String.class, JsonEditorFormat.TEXT_AREA).parse();
```

### JsonEditor

[Json-editor](https://github.com/json-editor/json-editor) takes a JSON Schema and uses it to generate an HTML form. It has full support for JSON Schema version 3 and 4 and can integrate with several popular CSS frameworks.
Some interaction mode in the SchemaParser needs to be used with selectize and flatpickr plugins.

#### Import

npm

```javascript
npm install @json-editor/json-editor
```

cdn

```javascript
<script src="https://cdn.jsdelivr.net/npm/@json-editor/json-editor@latest/dist/jsoneditor.min.js"></script>
```

#### Use

```javascript
const element = document.getElementById('editor_holder');
const editor = new JSONEditor(element, options);
```

#### More

<https://github.com/json-editor/json-editor>

## Base Annotation

### @JsonEditorUIMeta

The JsonEditorUIMeta annotation provides some basic information configuration for the field or class. It is also used as a field active sign when using ONLY_ANNOTATION filter. For fields without annotation, the default value will be used.

| Field | Info | Default Value |
| ---- | ---- | ---- |
| guide | '?' responsive prompt box | null(do not show)
| desc | descriptive information, usually appears below the edit box | null(do not show)
| format | used to indicate the interaction mode | AUTO(automatic judgment based on field class)
| title | the title show in json-editor | null(use field name) |

### @JsonEditorDependence

JsonEditorDependence annotation is used to add 'dependence' option for json schema. Used to control whether the field is valid.
This annotation is a repeatable annotation, The field will be valid when all conditions be effective at the same tim. Otherwise it will not be displayed or included in the generated json data.

|Field|Info|
| ----- | ------------|
|dependenceKey|name of the condition field|
|dependenceValue|value of the condition field|

```java
class Test {
    @JsonEditorUIMeta
    @JsonEditorDependence(dependenceKey = "simpleEnumSelection", dependenceValue = "enumA")
    @JsonEditorDependence(dependenceKey = "dynamicEnumSelection", dependenceValue = "Now")
    String dependenceItem;

    @JsonEditorUIMeta
    SimpleEnum simpleEnumSelection;

    @JsonEditorUIMeta(format = JsonEditorFormat.SELECT)
    @JsonEditorEnumBuilder(itemsBuilder = DynamicEnum.class)
    String dynamicEnumSelection;
}
```
In this example,'DependenceItem' will take effect only when simpleEnumSelection equals enumA and dynamicEnumSelection equals now.

## Interaction Mode(FORMAT)

Each field of POJO can flexibly choose the interactive mode by set format in JsonEditorUIMeta.
For the default value AUTO, the parser can automatically convert according to the class of the field,
The original automatic conversion supports primitive data type, wrapper class and collections. Users can also add or overwrite the mapping from class to format by themselves.

For some special interactive mode, the class of the field has certain restrictions,because parser need some information from it.

Some formats provide extended configuration, which can be set by using additional annotations.

The data type of each field in json data generated by the front-end page editor is determined by format, some of them may be different from the original type in POJO, users may need to deserialize by themselves

### All Supported Interaction Mode(FORMAT)

| Format | Info | Json Data Type |  Class Restrictions | Extended Configuration |
| --- |  --- | ------- | -------- | ---- |
|STRING|simple text box|String|×|×
|TEXT_AREA|resizable text box suitable for more text content|STRING|×|×
|TIME_SELECTOR|time picker|STRING|×|√|
|DATE_SELECTOR|date picker|STRING|×|√|
|INTEGER|integer|NUMBER|×|×
|NUMBER|numeral|NUMBER|×|×
|BOOLEAN|drop down selection of true or false|BOOLEAN|×|×
|BOOLEAN_CHECK_BOX|check box|BOOLEAN|×|×
|ARRAY|fully expanded array|ARRAY|√|√|
|TABLE|array of table type expanded with uniform header|ARRAY|√|√|
|TABS|only shows one array element at a time. It has tabs on the left for switching between items.|ARRAY|√|√|
|TABS_TOP|format place tabs on the top.|ARRAY|√|√|
|SELECT|drop down selection box|STRING|√|√|
|TAGS|tag label selector|ARRAY[STRING]|√|√|
|OBJECT|parse class objects, usually automatically called by the parser|OBJECT|-|-
|AUTO|automatic judgment based on field class|-|-|-|

### Default Format Mapping

|Java Class|Format|
| ----- | ------------|
|String|STRING|
|byte,short,int,long and their wrapper class|INTEGER|
|float,double and their wrapper class|NUMBER|
|boolean,Boolean|BOOLEAN|
|List/Set|ARRAY|
|enum|SELECTOR|

### Add Or Overwrite The Mapping From Class To Format
````java
JsonEditorParserBuilder.create(ExampleClass.class).addFormatDictionary(String.class, JsonEditorFormat.TEXT_AREA);
````
### Format With Specific Class

For array type like ARRAY,TABLE,TABS,TABS_TOP, the field must be collections like list,because the parser needs the generic information.

For TAGS, the field must be List\<Enum> or List\<String>, if use List\<String> should use @ConfigEditorEnumBuilder to provide enumeration items.

### Format Extended Configuration

#### @JsonEditorArray
Extended configuration for array type like ARRAY,TABLE,TABS,TABS_TOP.

|Field|Info|
|----|---|
|titleItem|set the array element title as field value of object by set titleItem as name of this field when use list of object.|
|showIndex|show index in array element title, default value is true|
|titleTemplate|set the array element title template, 'i' is index of array, 'self' is item of array, for example: {{i}} - {{self.xxx}} + {{self.bbb.aaa}}|
|itemFormat|overwrite the interaction mode (format) of the elements in the array, such as STRING to DATE_SELECTOR|

#### @JsonEditorDateTimeSelector
Extended configuration for TIME_SELECTOR and DATE_SELECTOR

|Field|Info|
|----|---|
|resultFormat|the format for generating the result, default value is 'Y-m-d H:i:S'|

#### @JsonEditorEnumBuilder
Change string to enum when use SELECT or TAGS, Using this annotation can dynamically generate enumeration when parse POJO to json schema.

|Field|Info|
|---|----|
|itemsBuilder|the interface to generate enumeration,return Map<String,String> key is string value of enum, value is enum name show in json editor|

### Some Special Interaction Mode(FORMAT)

For array type format add extended configuration on field can change the format of the array element, no need to set itemFormat in @JsonEditorArray. Such as @JsonEditorDateTimeSelector and JsonEditorEnumBuilder.

For SELECT and TAGS, java enum is supported. The default name and value is field name, name can be overridden by adding @ConfigEditorUIMeta with title to the field. Json editor will show name in HTML form but use value in json data.

## Field Filter

Filters are used to control whether a field participates in parsing. 
The filter supports chaining and can be added or reset. 
SchemaParser has two original filters, SIMPLE_FIELD_FILTER is the default.

|Filter|Info|
|----|---|
SIMPLE_FIELD_FILTER.EVERY_FIELD|all fields will be parsed|
SIMPLE_FIELD_FILTER.SIMPLE_FIELD_FILTER|only filed have @JsonEditorUIMeta annotation will be parsed|

````java
    static class MyField implements IFieldFilter {
        @Override
        public boolean match(Field field) {
            /**
             * true    pass
             * false   reject
             */
            return true;
        }
    }

    public static void main(String[] args) {
        //reset filter
        JsonEditorParserBuilder.create(ExampleClass.class).setFieldFilters(Lists.newArrayList(new MyField()));
        //add filter
        JsonEditorParserBuilder.create(ExampleClass.class).addFieldFilter(new MyField());
    }
````
