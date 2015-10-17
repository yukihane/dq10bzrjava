package yukihane.dq10bzrjava.main;

import yukihane.dq10bzrjava.main.vm.MainViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.scene.control.ComboBox;
import yukihane.dq10bzrjava.entity.ItemCount;
import yukihane.dq10bzrjava.entity.LargeCategory;
import yukihane.dq10bzrjava.entity.Quality;
import yukihane.dq10bzrjava.entity.SmallCategory;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private Label characterLabel;

    @FXML
    private ComboBox<LargeCategory> cbLargeCategory;

    @FXML
    private ComboBox<SmallCategory> cbSmallCategory;

    @FXML
    private ComboBox<ItemCount> cbItemCount;

    @FXML
    private ComboBox<Quality> cbQuality;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterLabel.textProperty().bind(viewModel.getCharacterName().valueProperty());

        cbLargeCategory.itemsProperty().bind(viewModel.largeCategoriesProperty());
        cbLargeCategory.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)
            -> viewModel.selectedLargeCategoryProperty().set(newValue)
        );
//        cbLargeCategory.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
//
//            @Override
//            public ListCell<String> call(ListView<String> param) {
//                return new ListCell<String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                    }
//                };
//            }
//        });

        cbSmallCategory.itemsProperty().bind(viewModel.smallCategoriesProperty());
        cbSmallCategory.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)
            -> viewModel.selectedSmallCategoryProperty().set(newValue)
        );

        cbItemCount.itemsProperty().bind(viewModel.itemCountsProperty());
        viewModel.selectedItemCountProperty().bind(cbItemCount.getSelectionModel().selectedItemProperty());
        cbItemCount.disableProperty().bind(viewModel.disabledItemCountsProperty());

        cbQuality.itemsProperty().bind(viewModel.qualitiesProperty());
        viewModel.selectedQualityProperty().bind(cbQuality.getSelectionModel().selectedItemProperty());
        cbQuality.disableProperty().bind(viewModel.disabledQualitiesProperty());
    }

    @FXML
    public void loginAction() {
        viewModel.getLoginCommand().execute();
    }
}
