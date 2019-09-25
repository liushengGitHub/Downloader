package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXListView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.util.BindUtils;

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
                        setGraphic(new Label(item.getAbstractVideoBean().getUrl()));
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
