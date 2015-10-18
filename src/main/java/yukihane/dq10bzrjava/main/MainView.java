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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
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

        cbLargeCategory.itemsProperty().bind(viewModel.getLargeCategory().valuesProperty());
        cbLargeCategory.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)
            -> viewModel.getLargeCategory().selectedProperty().set(newValue)
        );

        // http://docs.oracle.com/javase/jp/8/javafx/api/javafx/scene/control/ComboBox.html
        final Callback<ListView<LargeCategory>, ListCell<LargeCategory>> lcCellFactory
            = (param) -> new LargeCategoryCell();
        cbLargeCategory.setButtonCell(lcCellFactory.call(null));
        cbLargeCategory.setCellFactory(lcCellFactory);

        cbSmallCategory.itemsProperty().bind(viewModel.getSmallCategory().valuesProperty());
        cbSmallCategory.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)
            -> viewModel.getSmallCategory().selectedProperty().set(newValue)
        );

        cbItemCount.itemsProperty().bind(viewModel.getItemCount().valuesProperty());
        viewModel.getItemCount().selectedProperty().bind(cbItemCount.getSelectionModel().selectedItemProperty());
        cbItemCount.disableProperty().bind(viewModel.getItemCount().disabledProperty());

        cbQuality.itemsProperty().bind(viewModel.getQuality().valuesProperty());
        viewModel.getQuality().selectedProperty().bind(cbQuality.getSelectionModel().selectedItemProperty());
        cbQuality.disableProperty().bind(viewModel.getQuality().disabledProperty());
    }

    @FXML
    public void loginAction() {
        viewModel.getLoginCommand().execute();
    }
}
