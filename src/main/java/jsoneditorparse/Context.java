package jsoneditorparse;

import com.alibaba.fastjson.JSONObject;

import jsoneditorparse.annotation.ConfigEditorUIMeta;
import jsoneditorparse.parsehandler.AbstractParseHandler;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
@Setter
@Getter
public class Context {

    /**
     * 解析的类
     */
    private Class clazz;

    /**
     * 正在解析的field
     */
    private Field field;

    /**
     * 正在解析的主注解
     */
    private ConfigEditorUIMeta configEditorUIMeta;

    /**
     * List模式下的泛型擦除
     */
    private boolean clearGenericsClazz;

    /**
     * 配置
     */
    Config config;

    /**
     * 责任链模式 所有要执行解析的handler链
     */
    Deque<AbstractParseHandler> handlerQueue = new LinkedList<>();

    JSONObject result = new JSONObject(true);

    public Context(Class<?> clazz) {
        this.clazz = clazz;
        this.clearGenericsClazz = false;
    }

//    /**
//     *  clearGenericsClazz 是否擦除泛型信息
//     *          例如 List<String> xxx
//     *          true  ->  clazz = String
//     *          false ->  clazz = List
//     */
    public Context(Field field, boolean clearGenericsClazz) {
        this.field = field;
        this.clearGenericsClazz = clearGenericsClazz;
        if (!clearGenericsClazz) {
            this.clazz = field.getType();
        } else {
            ParameterizedType listGenericType = (ParameterizedType) field.getGenericType();
            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
            this.clazz = (Class<?>) listActualTypeArguments[0];
        }
        this.configEditorUIMeta = field.getAnnotation(ConfigEditorUIMeta.class);

    }
}
