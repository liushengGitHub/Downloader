package liusheng.downloadCore;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import liusheng.downloadCore.config.SystemCodeConfig;
import liusheng.downloadCore.executor.ListExecutorService;
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

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 年: 2019  月: 12 日: 31 小时: 08 分钟: 40
 * 用户名: LiuSheng
 */

public class MainApplication extends Application {

    private final OptionsLoader loader = new DefaultOptionsLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox main = new VBox();
        HBox select = new HBox();
        MenuBar menuBar = new MenuBar();


        initMenu(main, menuBar);


        /**
         * 主界面中组件的数量
         */
        int componentSize = SystemCodeConfig.componentSize();

        int menuBarHeight = SystemCodeConfig.menuBarHeight();

        int selectHeight = SystemCodeConfig.selectHeight();

        /**
         * 总高度
         */
        int paginationSize = menuBarHeight + selectHeight;

        configureMenuBarStyle(select, menuBar, menuBarHeight, selectHeight);

        List<Option> options = initPane(main, select, componentSize, paginationSize);

        SearchPane searchPane =
                (SearchPane) options.stream().filter(option -> option.pane() instanceof SearchPane).
                        map(Option::pane).findFirst().orElse(null);

        PagePane pagePane =
                (PagePane) options.stream().filter(option -> option.pane() instanceof PagePane).
                        map(Option::pane).findFirst().orElse(null);

        DownloadPane downloadPane =
                (DownloadPane) options.stream().filter(option -> option.pane() instanceof DownloadPane).
                        map(Option::pane).findFirst().orElse(null);


        Scene scene = new Scene(main, 600, 480);

        main.getChildren().addAll(menuBar, select);

        if (Objects.nonNull(downloadPane)) {
            if (Objects.nonNull(searchPane)) {
                // 加载Search 面板的插件
                initSearchPagePlugin(searchPane, downloadPane, scene);
                main.getChildren().add(searchPane);
            }
            if (Objects.nonNull(pagePane)) {
                // 加载Page 面板的插件
                initPagePagePlugin(pagePane, downloadPane, scene);
            }
        }


        onCloseListener(primaryStage);

        /**
         *  配置main Stage
         */

        configureManStage(primaryStage, searchPane, scene);
    }

    private void configureManStage(Stage primaryStage, SearchPane searchPane, Scene scene) {
        primaryStage.getIcons().add(new Image(MainApplication.class.getClassLoader().getResource("icon/icon.jpg").toString()));
        primaryStage.setTitle(SystemCodeConfig.title());
        primaryStage.setScene(scene);
        primaryStage.show();

        // 要放在最后面
        if (Objects.nonNull(searchPane)) {
            searchPane.getController().getSearchText().requestFocus();
        }
    }

    private void configureMenuBarStyle(HBox select, MenuBar menuBar, int menuBarHeight, int selectHeight) {
        select.setPrefHeight(selectHeight);
        menuBar.setPrefHeight(menuBarHeight);
        select.setOpacity(0.8);
        select.setAlignment(Pos.CENTER);
    }

    private void initPagePagePlugin(PagePane pagePane, DownloadPane downloadPane, Scene scene) {
        List<PagePluginHolder> pagePluginHolders = PluginUtils.getPagePlugins()
                .stream().map(PagePlugin::get).collect(Collectors.toList());
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
    }

    private void initSearchPagePlugin(SearchPane searchPane, DownloadPane downloadPane, Scene scene) {
        //插件
        List<SearchPluginHolder> searchPluginHolders = PluginUtils.getSearchPlugins()
                .stream().map(SearchPlugin::get).collect(Collectors.toList());

        if (!searchPluginHolders.isEmpty()) {
            searchPane.getController().getSearchText().setOnAction(new SearchAction(searchPane.getController().getComboBox(),
                    searchPane.getController().getSearchText(), searchPluginHolders
                    , searchPane, downloadPane));
            // 触发Action事件的
            scene.addMnemonic(new Mnemonic(searchPane.getController().getSearchText(),
                    new KeyCodeCombination(KeyCode.ENTER)));
            // 以上出发Action 事件

            searchPane.getController().getSearchButton().setOnAction(new SearchAction(searchPane.getController().getComboBox(),
                    searchPane.getController().getSearchText(), searchPluginHolders
                    , searchPane, downloadPane));
        }
    }

    private List<Option> initPane(VBox main, HBox select, int componentSize, int pagintionSize) {
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
        return options;
    }

    private void initMenu(VBox main, MenuBar menuBar) {
        menuBar.setStyle("-fx-background-color: transparent");
        Menu fileMenu = new Menu("_File");

        fileMenu.setStyle("-fx-background-color: transparent");

        MenuItem exitMenuItem = new MenuItem("Exit");

        exitMenuItem.setStyle("-fx-background-color:transparent");
        exitMenuItem.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        fileMenu.getItems().addAll(exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        menuBar.getMenus().addAll(new BackgroundMenu("_BackGround", main));
    }

    private void onCloseListener(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            Platform.setImplicitExit(false);
            SystemTray systemTray = SystemTray.getSystemTray();
            try {
                systemTray.add(getTrayIcon(primaryStage,systemTray));
            } catch (AWTException ex) {
                Platform.exit();
            }
        });
    }

    private TrayIcon getTrayIcon(Stage primaryStage, SystemTray systemTray) {

        PopupMenu popup = new PopupMenu();

        java.awt.MenuItem show = new java.awt.MenuItem("show");
        java.awt.MenuItem exit = new java.awt.MenuItem("exit");

        popup.add(show);
        popup.add(exit);
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(
                ClassLoader.getSystemClassLoader().getResource("icon/icon1.jpg")
        ), "蝶蝶", popup);


        show.addActionListener((e)->{
            Platform.runLater(()->{
                primaryStage.show();
            });
            systemTray.remove(trayIcon);
        });
        exit.addActionListener((e)->{
            systemTray.remove(trayIcon);
            Platform.exit();
        });
        return trayIcon;
    }


    /*private ObservableList<Node> removeNonFirst(VBox main) {

    }*/
}
