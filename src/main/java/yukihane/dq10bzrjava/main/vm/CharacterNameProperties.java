package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * ログインキャラクタ名表示箇所に関するプロパティ
 * @author yuki
 */
public class CharacterNameProperties {

    final ReadOnlyStringWrapper value = new ReadOnlyStringWrapper("");

    public ReadOnlyStringProperty valueProperty() {
        return value;
    }

    public String getValue() {
        return value.get();
    }
}
