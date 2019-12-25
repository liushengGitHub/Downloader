package liusheng.downloadCore.pane;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import liusheng.downloadCore.executor.FailListExecutorService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BackgroundMenu extends Menu {
    public BackgroundMenu(VBox main) {
        this("", main);
    }

    public BackgroundMenu(String text, VBox main) {
        super(text);
        this.setStyle("-fx-background-color: transparent");
        class BackgroundAction implements EventHandler<ActionEvent> {
            Path path;

            public BackgroundAction(Path path) {
                this.path = path;
            }

            @Override
            public void handle(ActionEvent event) {
                FailListExecutorService.commonExecutorServicehelp().execute(() -> {
                    try {

                        Background background = new Background(new BackgroundImage(
                                new Image(Files.newInputStream(path)),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT
                        ));
                        Platform.runLater(() -> {
                            main.setBackground(background);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        }


        class ColorBackground implements EventHandler<ActionEvent> {

            private String color;

            public ColorBackground(String color) {
                this.color = color;
            }

            @Override
            public void handle(ActionEvent event) {

                main.setStyle("-fx-background-color: " + color);
            }
        }

        Path path = Paths.get("background");
        try {
            List<MenuItem> menuItems = Files.list(path).map(file -> {
                MenuItem menuItem = new MenuItem(file.getFileName().toString());
                menuItem.setOnAction(new BackgroundAction(file));
                return menuItem;
            }).collect(Collectors.toList());

            this.getItems().addAll(menuItems);

            if (menuItems.size() > 0) {
                menuItems.get(0).fire();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<MenuItem> items = Files.lines(Paths.get("background/colors")).map(str -> {
                String[] pair = str.split(" ");
                if (pair.length > 1) {
                    MenuItem menuItem = new MenuItem(pair[1]);
                    menuItem.setOnAction(new ColorBackground(pair[0]));
                    return menuItem;
                }
                return  null;
            }).filter(Objects::nonNull).collect(Collectors.toList());

            this.getItems().addAll(items);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
