package jsoneditorparse;

import com.alibaba.fastjson.JSONObject;
import jsoneditorparse.fieldfilter.IFieldFilter;
import jsoneditorparse.fieldfilter.SIMPLE_FIELD_FILTER;
import jsoneditorparse.parsehandler.AbstractParseHandler;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class SchemaParser {

    Context context;

    Config config;

    private Config defaultConfig() {
        Config config = new Config();
        config.setFieldFilter(SIMPLE_FIELD_FILTER.ONLY_ANNOTATION);
        return config;
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

    public SchemaParser setFieldFilter(IFieldFilter fieldFilter) {
        this.config.fieldFilter = fieldFilter;
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
