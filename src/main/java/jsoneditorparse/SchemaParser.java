package jsoneditorparse;

import com.alibaba.fastjson.JSONObject;
import jsoneditorparse.fieldfilter.IFieldFilter;
import jsoneditorparse.fieldfilter.SIMPLE_FIELD_FILTER;
import jsoneditorparse.parsehandler.AbstractParseHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class SchemaParser {

    Context context;

    Config config;

    private Config defaultConfig() {
        return new Config();
    }

    protected SchemaParser(Class<?> clazz) {
        this.context = new Context(clazz);
        this.config = defaultConfig();
    }

    protected SchemaParser(Field field, boolean clearGenericsClazz, Config config) {
        context = new Context(field, clearGenericsClazz);
        this.config = config;
    }

    public SchemaParser addHandlerFirst(AbstractParseHandler handler) {
        context.getHandlerQueue().addFirst(handler);
        return this;
    }

    public SchemaParser addHandlerLast(AbstractParseHandler handler) {
        context.getHandlerQueue().addLast(handler);
        return this;
    }

    public SchemaParser setConfig(Config config) {
        this.config = config;
        return this;
    }

    public SchemaParser setFieldFilters(List<IFieldFilter> fieldFilter) {
        this.config.fieldFilter = fieldFilter;
        return this;
    }

    public SchemaParser addFieldFilter(IFieldFilter fieldFilter) {
        this.config.fieldFilter.add(fieldFilter);
        return this;
    }

    public SchemaParser addFormatDictionary(Class<?> clazz, JsonEditorFormat format) {
        this.config.getFormatDictionary().put(clazz, format);
        return this;
    }

    public JSONObject parse() {
        context.setConfig(config);
        while (true) {
            AbstractParseHandler handler = context.getHandlerQueue().pollFirst();
            if (handler == null) {
                return context.getResult();
            } else {
                //handler可能在递归中调用
                handler.setContext(context);
                handler.handle();
            }
        }
    }

}
