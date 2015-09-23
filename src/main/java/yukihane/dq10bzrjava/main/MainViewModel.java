package yukihane.dq10bzrjava.main;

import rx.Observable;
import rx.Subscriber;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.schedulers.Schedulers;
import yukihane.dq10bzrjava.Constants;
import yukihane.dq10bzrjava.Session;
import yukihane.dq10bzrjava.entity.LargeCategory;
import yukihane.dq10bzrjava.entity.SmallCategory;
import yukihane.dq10bzrjava.login.LoginView;
import yukihane.dq10remote.communication.HappyService;
import yukihane.dq10remote.communication.HappyServiceFactory;
import yukihane.dq10remote.communication.dto.bazaar.LargeCategoryDto;
import yukihane.dq10remote.communication.dto.bazaar.LargeCategoryValueList;
import yukihane.dq10remote.communication.dto.login.CharacterList;
import yukihane.dq10remote.exception.HappyServiceException;

public class MainViewModel implements ViewModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewModel.class);

    private final ReadOnlyStringWrapper characterName = new ReadOnlyStringWrapper("");

    private final Command loginCommand;

    private HappyService service;

    private final ReadOnlyListWrapper<LargeCategory> largeCategories
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    private final SimpleObjectProperty<LargeCategory> selectedLargeCategory
        = new SimpleObjectProperty<>();

    private final ReadOnlyListWrapper<SmallCategory> smallCategories
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    private final SimpleObjectProperty<SmallCategory> selectedSmallCategory
        = new SimpleObjectProperty<>();

    public MainViewModel() {
        ObservableList<CharacterList> charalist = FXCollections.observableArrayList();
        loginCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                openLoginWindow();
            }
        });

        selectedLargeCategory.addListener(new SelectedLargeCategoryChangeListener());

        Session sess = Session.getInstance();
        String sessionId = sess.getSessionId();
        if (sessionId != null && !sessionId.isEmpty()) {
            service = HappyServiceFactory.getService(sess.getSessionId());
            characterName.set(sess.getDisplayName());
            loadInitialData();
        }
    }

    public ReadOnlyStringProperty characterNameProperty() {
        return characterName;
    }

    public String getCharacterName() {
        return characterName.get();
    }

    public Command getLoginCommand() {
        return loginCommand;
    }

    public ReadOnlyListProperty<LargeCategory> largeCategoriesProperty() {
        return largeCategories.getReadOnlyProperty();
    }

    public SimpleObjectProperty<LargeCategory> selectedLargeCategoryProperty() {
        return selectedLargeCategory;
    }

    public ReadOnlyListProperty<SmallCategory> smallCategoriesProperty() {
        return smallCategories.getReadOnlyProperty();
    }

    public SimpleObjectProperty<SmallCategory> selectedSmallCategoryProperty() {
        return selectedSmallCategory;
    }

    private void openLoginWindow() {
        ViewTuple viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();

        Parent root = viewTuple.getView();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        NotificationObserver observer = new NotificationObserver() {
            @Override
            public void receivedNotification(String key, Object... payload) {
                service = (HappyService) payload[0];
                String sessionId = (String) payload[1];
                CharacterList character = (CharacterList) payload[2];
                String displayName = character.getCharacterName()
                    + " (" + character.getSmileUniqueNo() + ")";
                characterName.set(displayName);
                Session sess = Session.getInstance();
                sess.setSessionId(sessionId);
                sess.setDisplayName(displayName);
                sess.save();
                // FIXME どこかで購読を解除する必要があるがここで行うと java.util.ConcurrentModificationException 発生
//                MvvmFX.getNotificationCenter().unsubscribe(viewTuple.getViewModel(), this);
                loadInitialData();
            }
        };

        MvvmFX.getNotificationCenter().subscribe(
            viewTuple.getViewModel(),
            Constants.LOGIN_COMPLETED,
            observer);
    }

    private void loadInitialData() {
        queryLargeCategory();
    }

    /**
     * 「種類」カテゴリのロード.
     */
    private void queryLargeCategory() {
        Observable<List<LargeCategory>> observable
            = Observable.create((Subscriber<? super List<LargeCategory>> subscriber) -> {
                try {
                    LargeCategoryDto lc = service.getLargeCategory();
                    List<LargeCategoryValueList> list = lc.getLargeCategoryValueList();
                    List<LargeCategory> categories = list.stream()
                    .map((LargeCategoryValueList t) -> LargeCategory.from(t))
                    .collect(Collectors.toCollection(() -> new ArrayList<>(list.size())));
                    subscriber.onNext(categories);
                    subscriber.onCompleted();
                } catch (HappyServiceException ex) {
                    subscriber.onError(ex);
                }
            });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(Schedulers.newThread()).subscribe((List<LargeCategory> data) -> {
            Platform.runLater(() -> {
                largeCategories.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void querySmallCategory() {
        System.out.println("querySmallCategory called");
    }

    private class SelectedLargeCategoryChangeListener implements ChangeListener<LargeCategory> {

        @Override
        public void changed(ObservableValue<? extends LargeCategory> observable, LargeCategory oldValue, LargeCategory newValue) {
            if (oldValue == newValue) {
                return;
            }
            if (!newValue.isSmallCategory()) {
                // smallCategory set
                return;
            }
            querySmallCategory();
        }
    }
}
