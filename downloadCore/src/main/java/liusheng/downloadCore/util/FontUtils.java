package liusheng.downloadCore.util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FontUtils {
    public static void setFontAndColor(Labeled searchButton) {
        searchButton.setFont(Font.font("微软雅黑", FontWeight.BOLD, 15));
        searchButton.setTextFill(Paint.valueOf("red"));
    }

}
