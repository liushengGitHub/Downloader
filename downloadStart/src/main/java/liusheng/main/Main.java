package liusheng.main;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import liusheng.downloadCore.DownloadList;
import liusheng.downloadCore.Option;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.option.DefaultOptionsLoader;
import liusheng.downloadCore.option.OptionsLoader;
import liusheng.downloadCore.pane.BackgroundMenu;
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
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private final OptionsLoader loader = new DefaultOptionsLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox main = new VBox();
        HBox select = new HBox();
        MenuBar menuBar = new MenuBar();



        menuBar.setStyle("-fx-background-color: transparent");
        Menu fileMenu = new Menu("_File");

        fileMenu.setStyle("-fx-background-color: transparent");

        MenuItem exitMenuItem = new MenuItem("Exit");

        exitMenuItem.setStyle("-fx-background-color:transparent");
        exitMenuItem.setOnAction(event -> {
            System.exit(0);
        });
        fileMenu.getItems().addAll(exitMenuItem);

        menuBar.getMenus().addAll(fileMenu);
        menuBar.getMenus().addAll(new BackgroundMenu("BackGround", main));


        int componentSize = 2;
        int menuBarSize = 30;
        int selectSize = 50;
        int pagintionSize = menuBarSize + selectSize;


        select.setPrefHeight(selectSize);
        menuBar.setPrefHeight(menuBarSize);
        select.setOpacity(0.8);
        select.setAlignment(Pos.CENTER);

        List<Option> options = this.loader.loader();

        options.stream().forEach(option -> {
            JFXButton button = option.button();

            Pane pane = option.pane();

            BindUtils.bind(pane.prefWidthProperty(), main.widthProperty());
            BindUtils.bind(pane.prefHeightProperty(), main.heightProperty().subtract(pagintionSize));

            BindUtils.bind(button.prefWidthProperty(), select.widthProperty().multiply(1.0 / options.size()));
            BindUtils.bind(button.prefHeightProperty(), select.heightProperty());

            button.setOnAction(e -> {
                ObservableList<Node> children = main.getChildren();
                int size = children.size();
                // 这个事件源对象就是button

                if (size > componentSize) {
                    children.remove(componentSize, size);
                }
                children.addAll(pane);
            });

            FontUtils.setFontAndColor(button);

            button.setOpacity(0.8);
            pane.setOpacity(0.8);

            select.getChildren().add(button);
        });


        SearchPane searchPane =
                (SearchPane) options.stream().filter(option -> option.pane() instanceof SearchPane).
                        map(Option::pane).findFirst().orElse(null);
        PagePane pagePane =
                (PagePane) options.stream().filter(option -> option.pane() instanceof PagePane).
                        map(Option::pane).findFirst().orElse(null);
        DownloadPane downloadPane =
                (DownloadPane) options.stream().filter(option -> option.pane() instanceof DownloadPane).
                        map(Option::pane).findFirst().orElse(null);


        // 绑定.实现动态变化
        //  BindUtils.bind(select.prefHeightProperty(), main.heightProperty().multiply(2 / 15.0));




        Scene scene = new Scene(main, 600, 480);


        //插件
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


        onCloseListener(primaryStage);

        main.getChildren().addAll(menuBar, select, searchPane);
        primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResource("icon/icon.jpg").toString()));

        primaryStage.setScene(scene);
        primaryStage.show();

        // 要放在最后面
        searchPane.getController().getSearchText().requestFocus();
    }

    private void onCloseListener(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            FailListExecutorService.commonExecutorServicehelp().execute(() -> {
                Path path1 = Paths.get("download/unDownloadUrls");
                Path path2 = Paths.get("download/DownloadedUrls");
                try {
                    synchronized (DownloadList.downloadList()) {
                        Files.write(path1, DownloadList.downloadList().getUnDownloadList());
                        Files.write(path2, DownloadList.downloadList().getDownloadedList());
                    }
                    //关闭线程池
                    FailListExecutorService.commonExecutorService().shutdownNow();
                    FailListExecutorService.commonExecutorServicehelp().shutdownNow();

                } catch (IOException ex) {

                }
                System.exit(1);
            });
        });
    }


    /*private ObservableList<Node> removeNonFirst(VBox main) {

    }*/
}
