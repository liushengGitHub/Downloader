package liusheng.downloadCore.util;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

public class BindUtils {
    public static <T> void bind(Property<T> source, ObservableValue<? extends T> observable){
        source.bind(observable);
    }
}
