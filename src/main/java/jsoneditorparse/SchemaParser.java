package jsoneditorparse;

import com.alibaba.fastjson.JSONObject;
import jsoneditorparse.parsehandler.AbstractParseHandler;

import java.lang.reflect.Field;

/**
 * Description:
 * User: Mochi
 * version: 1.0
 */
public class SchemaParser {

    Context context;

    public SchemaParser(Class clazz) {
        context = new Context(clazz);
    }

    public SchemaParser(Field field, boolean clearGenericsClazz) {
        context = new Context(field, clearGenericsClazz);
    }

    public void addHandlerFirst(AbstractParseHandler handler) {
        context.getHandlerQueue().addFirst(handler);
    }

    public void addHandlerLast(AbstractParseHandler handler) {
        context.getHandlerQueue().addLast(handler);
    }

    public JSONObject parse() {
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
