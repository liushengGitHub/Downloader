package liusheng.main;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import liusheng.downloadCore.DownloadList;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.pane.DownloadPane;
import liusheng.downloadCore.pane.page.PageAction;
import liusheng.downloadCore.pane.page.PagePane;
import liusheng.downloadCore.search.SearchAction;
import liusheng.downloadCore.search.SearchPane;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadCore.util.FontUtils;
import liusheng.downloadCore.util.PluginUtils;
import liusheng.downloadInterface.PagePlugin;
import liusheng.downloadInterface.PagePluginHolder;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox main = new VBox();
        BackgroundImage backgroundImage = new BackgroundImage(new Image(
                Main.class.getClassLoader().getResourceAsStream("backgroud.jpg")
        ),
                BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        main.setBackground(background);

        Map<Object, Node> controllerMap = new HashMap<>();

        HBox select = new HBox();
        select.setOpacity(0.8);
        select.setAlignment(Pos.CENTER);
        DownloadPane downloadPane = new DownloadPane();
        PagePane pagePane = new PagePane();
        JFXButton searchButton = new JFXButton("搜索");
        FontUtils.setFontAndColor(searchButton);
        JFXButton pageButton = new JFXButton("页面");
        FontUtils.setFontAndColor(pageButton);
        JFXButton downloadButton = new JFXButton("下载");
        FontUtils.setFontAndColor(downloadButton);
        SearchPane searchPane = new SearchPane();

        searchPane.setOpacity(0.8);
        downloadPane.setOpacity(0.8);
        controllerMap.put(searchButton, searchPane);
        controllerMap.put(pageButton, pagePane);
        controllerMap.put(downloadButton, downloadPane);


        controllerMap.put(pageButton, downloadPane);
        pageButton.setOnAction(e -> {
            ObservableList<Node> children = main.getChildren();
            int size = children.size();
            // 这个事件源对象就是button

            if (size > 1) {
                children.remove(1, size);
            }
            children.addAll(pagePane);
        });

        searchButton.setOnAction(e -> {
            ObservableList<Node> children = main.getChildren();
            int size = children.size();
            // 这个事件源对象就是button

            if (size > 1) {
                children.remove(1, size);
            }
            children.addAll(searchPane);

        });
        BindUtils.bind(downloadPane.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(pagePane.prefWidthProperty(), main.widthProperty());

        BindUtils.bind(pagePane.prefHeightProperty(), main.heightProperty().subtract(50));

        BindUtils.bind(downloadPane.prefHeightProperty(), main.heightProperty().subtract(50));


        downloadButton.setOnAction(e -> {
            ObservableList<Node> children = main.getChildren();
            int size = children.size();
            // 这个事件源对象就是button

            if (size > 1) {
                Node node = children.get(size - 1);
                children.remove(1, size);
            }
            children.addAll(downloadPane);
        });
        // 绑定.实现动态变化
        //  BindUtils.bind(select.prefHeightProperty(), main.heightProperty().multiply(2 / 15.0));
        select.setPrefHeight(50);
        BindUtils.bind(select.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(searchButton.prefWidthProperty(), select.widthProperty().multiply(0.3));
        BindUtils.bind(pageButton.prefWidthProperty(), select.widthProperty().multiply(0.3));
        BindUtils.bind(downloadButton.prefWidthProperty(), select.widthProperty().multiply(0.3));

        BindUtils.bind(searchButton.prefHeightProperty(), select.heightProperty());
        BindUtils.bind(pageButton.prefHeightProperty(), select.heightProperty());
        BindUtils.bind(downloadButton.prefHeightProperty(), select.heightProperty());
        BindUtils.bind(searchPane.prefHeightProperty(), main.heightProperty().subtract(50));

        select.getChildren().addAll(searchButton, pageButton, downloadButton);

        main.getChildren().addAll(select, searchPane);

        Scene scene = new Scene(main, 600, 480);

        List<SearchPluginHolder> searchPluginHolders = PluginUtils.getSearchPlugins()
                .stream().map(SearchPlugin::get).collect(Collectors.toList());

        List<PagePluginHolder> pagePluginHolders = PluginUtils.getPagePlugins()
                .stream().map(PagePlugin::get).collect(Collectors.toList());
        if (!searchPluginHolders.isEmpty()) {
            searchPane.getController().getSearchText().setOnAction(new SearchAction(searchPane.getController().getComboBox(),
                    searchPane.getController().getSearchText(), searchPluginHolders
                    , searchPane, downloadPane));
            // 触发Action事件的
            scene.addMnemonic(new Mnemonic(searchPane.getController().getSearchText(),
                    new KeyCodeCombination(KeyCode.ENTER)));

            searchPane.getController().getSearchButton().setOnAction(new SearchAction(searchPane.getController().getComboBox(),
                    searchPane.getController().getSearchText(), searchPluginHolders
                    , searchPane, downloadPane));
        }

        if (!pagePluginHolders.isEmpty()) {
            pagePane.getController().getPageText().setOnAction(new PageAction(pagePane.getController().getComboBox(),
                    pagePane.getController().getPageText(), pagePluginHolders
                    , pagePane, downloadPane));
            // 触发Action事件的
            scene.addMnemonic(new Mnemonic(pagePane.getController().getPageText(),
                    new KeyCodeCombination(KeyCode.ENTER)));

            pagePane.getController().getPageButton().setOnAction(new PageAction(pagePane.getController().getComboBox(),
                    pagePane.getController().getPageText(), pagePluginHolders
                    , pagePane, downloadPane));
        }


        primaryStage.setOnCloseRequest(e -> {
            FailListExecutorService.commonExecutorServicehelp().execute(() -> {
                Path path1 = Paths.get("download/unDownloadUrls");
                Path path2 = Paths.get("download/DownloadedUrls");
                try {
                    synchronized (DownloadList.downloadList()) {
                        Files.write(path1, DownloadList.downloadList().getUnDownloadList());
                        Files.write(path2, DownloadList.downloadList().getUnDownloadList());
                    }
                    //关闭线程池
                    FailListExecutorService.commonExecutorService().shutdownNow();
                    FailListExecutorService.commonExecutorServicehelp().shutdownNow();

                } catch (IOException ex) {

                }
                System.exit(1);
            });
        });


        primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResource("icon/icon.jpg").toString()));

        primaryStage.setScene(scene);
        primaryStage.show();

        // 要放在最后面
        searchPane.getController().getSearchText().requestFocus();
    }


    /*private ObservableList<Node> removeNonFirst(VBox main) {

    }*/
}
