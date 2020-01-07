package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXListView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.player.DefaultMediaPlayerViewVBox;
import liusheng.downloadCore.util.BindUtils;

import java.net.MalformedURLException;
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
                        Label label = new Label(abstractDataBean.getRefererUrl());

                        label.setOnMouseClicked(e->{
                            try {
                                DefaultMediaPlayerViewVBox defaultMediaPlayerViewVBox = new DefaultMediaPlayerViewVBox(Paths.get(abstractDataBean.getDirFile().toString(),
                                        abstractDataBean.getName() + ".mp4"
                                ).toUri().toURL().toString());

                                Stage stage = new Stage();

                                stage.setOnCloseRequest(e1->{
                                    defaultMediaPlayerViewVBox.getMediaView().getMediaPlayer().stop();
                                });

                                stage.setScene(new Scene(defaultMediaPlayerViewVBox,700,400));
                                stage.show();
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                        });

                        setGraphic(label);
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
