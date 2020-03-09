package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.player.DefaultMediaPlayerViewVBox;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadCore.util.ProcessBuilderUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadedPaneContainer extends VBox {
    private JFXListView<DownloadItemPaneEntity> listView;
    private List<Object> indeies = Collections.synchronizedList(new LinkedList<>());
    private AtomicInteger indexGenerator = new AtomicInteger();

    public int next() {
        return indexGenerator.getAndIncrement();
    }

    public List<Object> getIndeies() {
        return indeies;
    }

    public JFXListView<DownloadItemPaneEntity> getListView() {
        return listView;
    }

    private final Semaphore semaphore = new Semaphore(2);

    public DownloadedPaneContainer(DownloadPane downloadPane) {
        super();

       /* FailListExecutorService.commonExecutorService().execute(()->{
            while (true) {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(()->{
                    indexMap.forEach((i,com)->{
                        AtomicLong size = com.getEntity().getAbstractVideoBean().getSize();
                        DownloadItemPane downloadItemPane = com.getDownloadItemPane();

                        downloadItemPane.getDownloadProgressBar().setProgress(size.get());
                    });
                });

            }
        });
        */
        listView = new JFXListView<>();

        listView.setCellFactory(list -> {

            ListCell<DownloadItemPaneEntity> listCell = new ListCell<DownloadItemPaneEntity>() {
                @Override
                protected void updateItem(DownloadItemPaneEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
                       /* int index = getIndex();
                        Node pane = ((AbstractVideoBean) indeies.get(index)).getPane();*/
                        AbstractDataBean abstractDataBean = item.getAbstractDataBean();

                        DownloadedItemPane downloadedItemPane = new DownloadedItemPane();

                        JFXButton playButton = downloadedItemPane.getPlayButton();
                        JFXButton deleteFileButton = downloadedItemPane.getDeleteFileButton();
                        JFXButton openFileButton = downloadedItemPane.getOpenFileButton();
                        downloadedItemPane.getFilePathLabel().setText(abstractDataBean.getName());

                        playButton.setOnMouseClicked(e -> {
                            try {
                                DefaultMediaPlayerViewVBox defaultMediaPlayerViewVBox = new DefaultMediaPlayerViewVBox(Paths.get(abstractDataBean.getDirFile().toString(),
                                        abstractDataBean.getName() + ".mp4"
                                ).toUri().toURL().toString());

                                Stage stage = new Stage();

                                stage.setOnCloseRequest(e1 -> {
                                    defaultMediaPlayerViewVBox.getMediaView().getMediaPlayer().stop();
                                });

                                stage.setScene(new Scene(defaultMediaPlayerViewVBox, 700, 400));
                                stage.setOnCloseRequest(e1->{
                                    defaultMediaPlayerViewVBox.close();
                                });
                                stage.show();
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                        });
                        deleteFileButton.setOnAction(e -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "您确定要删除吗?",ButtonType.YES,ButtonType.NO);

                            alert.resultProperty().addListener((a,o,n)->{

                                if ( n == ButtonType.YES) {
                                    Path filePath = abstractDataBean.getFilePath();
                                    try {
                                        Files.delete(filePath);
                                        listView.getItems().remove(item);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }else {
                                    alert.close();
                                }
                            });
                            alert.show();

                        });
                        openFileButton.setOnAction(e -> {
                            Path filePath = abstractDataBean.getFilePath();
                            if (Files.exists(filePath)) {
                                try {
                                    ProcessBuilderUtils.executeAndDiscardOuput("explorer", "/e,/select,", "\"" + filePath.toString() + "\"");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }else {

                            }
                        });
                        setGraphic(downloadedItemPane);
                    } else if (empty) {
                        setGraphic(null);
                    }
                }
            };

            BindUtils.bind(listCell.prefHeightProperty(), listView.heightProperty().multiply(0.2));
            BindUtils.bind(listCell.prefWidthProperty(), listView.widthProperty());
            return listCell;
        });
        BindUtils.bind(listView.prefHeightProperty(), this.heightProperty());
        BindUtils.bind(listView.prefWidthProperty(), this.widthProperty());
        this.getChildren().addAll(listView);

    }

}
