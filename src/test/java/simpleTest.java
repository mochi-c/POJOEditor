import jsoneditorparse.SimpleJsonEditorParserBuilder;

/**
 * Description:
 * User: happy
 * Date: 2020-04-10
 * version: 1.0
 */
public class simpleTest {
    public static void main(String[] args) {
        System.out.println(SimpleJsonEditorParserBuilder.SimpleParser(SimpleJsonEditorParserBuilder.BB.class).parse().toString());
    }
}
