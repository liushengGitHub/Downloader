package liusheng.downloadCore.pane;

import cn.hutool.core.util.StrUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import liusheng.downloadCore.config.SystemCodeConfig;
import liusheng.downloadCore.executor.ListExecutorService;

import java.io.IOException;
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
                ListExecutorService.commonExecutorServicehelp().execute(() -> {
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
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String background = SystemCodeConfig.defaultMainBackground();
            boolean blank = StrUtil.isBlank(background);
            List<MenuItem> menuItems = Files.list(path).map(file -> {
                String s = file.getFileName().toString();
                MenuItem menuItem = new MenuItem(s);
                menuItem.setOnAction(new BackgroundAction(file));
                if (!blank && background.equals(s)) {
                    menuItem.fire();
                }

                return menuItem;
            }).collect(Collectors.toList());

            this.getItems().addAll(menuItems);


            if (blank && menuItems.size() > 0) {
                menuItems.get(0).fire();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path backgroundPath = Paths.get("background/colors");
            if (!Files.exists(backgroundPath)){
                return;
            }
            List<MenuItem> items = Files.lines(backgroundPath).map(str -> {
                String[] pair = str.split(" ");
                if (pair.length > 1) {
                    MenuItem menuItem = new MenuItem(pair[1]);
                    menuItem.setOnAction(new ColorBackground(pair[0]));
                    return menuItem;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());

            this.getItems().addAll(items);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
