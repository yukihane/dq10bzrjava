package yukihane.dq10bzrjava.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.scene.control.ComboBox;
import yukihane.dq10bzrjava.entity.LargeCategory;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private Label characterLabel;

    @FXML
    private ComboBox<LargeCategory> cbLargeCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterLabel.textProperty().bind(viewModel.characterNameProperty());
        cbLargeCategory.itemsProperty().bind(viewModel.largeCategoriesProperty());
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
    }

    @FXML
    public void loginAction() {
        viewModel.getLoginCommand().execute();
    }
}
