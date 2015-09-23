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
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
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
import yukihane.dq10bzrjava.entity.ItemCount;
import yukihane.dq10bzrjava.entity.LargeCategory;
import yukihane.dq10bzrjava.entity.Quality;
import yukihane.dq10bzrjava.entity.SmallCategory;
import yukihane.dq10bzrjava.login.LoginView;
import yukihane.dq10remote.communication.HappyService;
import yukihane.dq10remote.communication.HappyServiceFactory;
import yukihane.dq10remote.communication.dto.bazaar.ItemCountDto;
import yukihane.dq10remote.communication.dto.bazaar.ItemCountValueList;
import yukihane.dq10remote.communication.dto.bazaar.LargeCategoryDto;
import yukihane.dq10remote.communication.dto.bazaar.LargeCategoryValueList;
import yukihane.dq10remote.communication.dto.bazaar.SmallCategoryDto;
import yukihane.dq10remote.communication.dto.bazaar.SmallCategoryValueList;
import yukihane.dq10remote.communication.dto.login.CharacterList;
import yukihane.dq10remote.exception.HappyServiceException;

public class MainViewModel implements ViewModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewModel.class);

    private final ReadOnlyStringWrapper characterName = new ReadOnlyStringWrapper("");

    private final Command loginCommand;

    private HappyService service;

    /**
     * 種類の選択肢.
     */
    private final ReadOnlyListWrapper<LargeCategory> largeCategories
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    /**
     * 選択された種類.
     */
    private final SimpleObjectProperty<LargeCategory> selectedLargeCategory
        = new SimpleObjectProperty<>();

    /**
     * 種類2の選択肢.
     */
    private final ReadOnlyListWrapper<SmallCategory> smallCategories
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    /**
     * 選択された種類2.
     */
    private final SimpleObjectProperty<SmallCategory> selectedSmallCategory
        = new SimpleObjectProperty<>();

    /**
     * アイテム名の選択肢.
     */
    private final ReadOnlyListWrapper<ItemCount> itemCounts
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    /**
     * 選択されたアイテム名.
     */
    private final SimpleObjectProperty<ItemCount> selectedItemCount
        = new SimpleObjectProperty<>();

    /**
     * アイテム名が選択不可能か.
     */
    private final ReadOnlyBooleanWrapper disabledItemCounts
        = new ReadOnlyBooleanWrapper(true);

    /**
     * できのよさの選択肢.
     */
    private final ReadOnlyListWrapper<Quality> qualities
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    /**
     * 選択されたできのよさ.
     */
    private final SimpleObjectProperty<Quality> selectedQuality
        = new SimpleObjectProperty<>();

    /**
     * できのよさが選択不可能か.
     */
    private final ReadOnlyBooleanWrapper disabledQualities
        = new ReadOnlyBooleanWrapper(true);

    public MainViewModel() {
        ObservableList<CharacterList> charalist = FXCollections.observableArrayList();
        loginCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                openLoginWindow();
            }
        });

        selectedLargeCategory.addListener(new SelectedLargeCategoryChangeListener());
        selectedSmallCategory.addListener(new SelectedSmallCategoryChangeListener());
        selectedItemCount.addListener(new SelectedItemCountChangeListener());

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

    public ReadOnlyListProperty<ItemCount> itemCountsProperty() {
        return itemCounts.getReadOnlyProperty();
    }

    public SimpleObjectProperty<ItemCount> selectedItemCountProperty() {
        return selectedItemCount;
    }

    public ReadOnlyBooleanProperty disabledItemCountsProperty() {
        return disabledItemCounts.getReadOnlyProperty();
    }

    public ReadOnlyListProperty<Quality> qualitiesProperty() {
        return qualities.getReadOnlyProperty();
    }

    public SimpleObjectProperty<Quality> selectedQualityProperty() {
        return selectedQuality;
    }

    public ReadOnlyBooleanProperty disabledQualitiesProperty() {
        return disabledQualities.getReadOnlyProperty();
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
        LargeCategory lc = selectedLargeCategory.get();
        if (lc == null) {
            LOGGER.debug("large category is null");
            return;
        }

        Observable<List<SmallCategory>> observable
            = Observable.create((Subscriber<? super List<SmallCategory>> subscriber) -> {
                try {
                    SmallCategoryDto dto = service.getSmallCategory(lc.getLargeCategoryId());
                    List<SmallCategoryValueList> list = dto.getSmallCategoryValueList();
                    List<SmallCategory> categories = list.stream()
                    .map((SmallCategoryValueList t) -> SmallCategory.from(t))
                    .collect(Collectors.toCollection(() -> new ArrayList<>(list.size())));
                    subscriber.onNext(categories);
                    subscriber.onCompleted();
                } catch (HappyServiceException ex) {
                    subscriber.onError(ex);
                }
            });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(Schedulers.newThread()).subscribe((List<SmallCategory> data) -> {
            Platform.runLater(() -> {
                smallCategories.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void queryItemCount() {
        LargeCategory lc = selectedLargeCategory.get();
        if (lc == null) {
            LOGGER.debug("large category is null");
            return;
        }
        SmallCategory sc = selectedSmallCategory.get();
        if (lc.isSmallCategory() && sc == null) {
            LOGGER.debug("small category is null");
            return;
        }

        int lcid = lc.getLargeCategoryId();
        int scid = (lc.isSmallCategory()) ? sc.getSmallCategoryId() : lc.getSmallCategoryId();

        Observable<List<ItemCount>> observable
            = Observable.create((Subscriber<? super List<ItemCount>> subscriber) -> {
                try {
                    ItemCountDto dto = service.getItemCount(lcid, scid);
                    List<ItemCountValueList> list = dto.getItemCountValueList();
                    List<ItemCount> categories = list.stream()
                    .map((ItemCountValueList t) -> ItemCount.from(t))
                    .collect(Collectors.toCollection(() -> new ArrayList<>(list.size())));
                    subscriber.onNext(categories);
                    subscriber.onCompleted();
                } catch (HappyServiceException ex) {
                    subscriber.onError(ex);
                }
            });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(Schedulers.newThread()).subscribe((List<ItemCount> data) -> {
            Platform.runLater(() -> {
                itemCounts.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void unsetDisabled() {
        clearOptions();
        setDisabledDefault();

        LargeCategory lc = selectedLargeCategory.get();
        SmallCategory sc = selectedSmallCategory.get();
        if (lc == null || (lc.isSmallCategory() && sc == null)) {
            return;
        }
        int lcid = lc.getLargeCategoryId();
        int scid = lc.isSmallCategory() ? sc.getSmallCategoryId() : lc.getSmallCategoryId();

        if (lcid == 1 || lcid == 2 || lcid == 3) {
            // 武器, 盾, 防具の場合
            disabledItemCounts.set(false);
            disabledQualities.set(false);
            qualities.addAll(Quality.values());
        } else if (lcid == 5 || lcid == 11 || scid == 606) {
            // 職人どうぐ, 釣りどうぐ, 消費アイテム>料理 の場合
            disabledItemCounts.set(false);
            disabledQualities.set(false);
            qualities.addAll(Quality.values());
        } else if (scid == 605) {
            // 消費アイテム>依頼書 の場合

        } else if (lcid == 6 || lcid == 7 || lcid == 8 || lcid == 12 || lcid == 9 || lcid == 10) {
            // (料理と依頼書以外の)消費アイテム, 素材, 家具, 庭具, レシピ帳, スカウトの書

        }
    }

    private void clearOptions() {
        qualities.clear();
    }

    private void setDisabledDefault() {
        disabledItemCounts.set(true);
        disabledQualities.set(true);
    }

    private class SelectedLargeCategoryChangeListener implements ChangeListener<LargeCategory> {

        @Override
        public void changed(ObservableValue<? extends LargeCategory> observable, LargeCategory oldValue, LargeCategory newValue) {
            if (oldValue == newValue) {
                return;
            }
            smallCategories.clear();
            itemCounts.clear();
            if (!newValue.isSmallCategory()) {
                queryItemCount();
                return;
            }
            querySmallCategory();
        }
    }

    private class SelectedSmallCategoryChangeListener implements ChangeListener<SmallCategory> {

        @Override
        public void changed(ObservableValue<? extends SmallCategory> observable, SmallCategory oldValue, SmallCategory newValue) {
            if (oldValue == newValue) {
                return;
            }
            unsetDisabled();
            itemCounts.clear();
            queryItemCount();
        }
    }

    private class SelectedItemCountChangeListener implements ChangeListener<ItemCount> {

        @Override
        public void changed(ObservableValue<? extends ItemCount> observable, ItemCount oldValue, ItemCount newValue) {
            System.out.println("item count changed");
        }
    }
}
