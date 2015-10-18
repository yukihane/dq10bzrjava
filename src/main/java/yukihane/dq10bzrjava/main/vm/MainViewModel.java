package yukihane.dq10bzrjava.main.vm;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.schedulers.Schedulers;
import yukihane.dq10bzrjava.Constants;
import yukihane.dq10bzrjava.Session;
import yukihane.dq10bzrjava.entity.ItemCount;
import yukihane.dq10bzrjava.entity.LargeCategory;
import yukihane.dq10bzrjava.entity.NumOfRenkin;
import yukihane.dq10bzrjava.entity.Quality;
import yukihane.dq10bzrjava.entity.SmallCategory;
import yukihane.dq10bzrjava.login.LoginView;
import yukihane.dq10bzrjava.resource.NumOfRenkinLoader;
import yukihane.dq10bzrjava.resource.QualityLoader;
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

    private HappyService service;

    private final Command loginCommand;

    public Command getLoginCommand() {
        return loginCommand;
    }

    @Getter
    private final CharacterNameProperties characterName = new CharacterNameProperties();

    @Getter
    private final LargeCategoryProperties largeCategory = new LargeCategoryProperties();

    @Getter
    private final SmallCategoryProperties smallCategory = new SmallCategoryProperties();

    @Getter
    private final ItemCountProperties itemCount = new ItemCountProperties();

    @Getter
    private final QualityProperties quality = new QualityProperties();

    private final List<Quality> qualities = new QualityLoader().load();

    @Getter
    private final NumOfRenkinProperties numOfRenkin = new NumOfRenkinProperties();

    private final List<NumOfRenkin> numOfRenkins = new NumOfRenkinLoader().load();

    public MainViewModel() {

        ObservableList<CharacterList> charalist = FXCollections.observableArrayList();
        loginCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                openLoginWindow();
            }
        });

        largeCategory.selected.addListener(new SelectedLargeCategoryChangeListener());
        smallCategory.selected.addListener(new SelectedSmallCategoryChangeListener());
        itemCount.selected.addListener(new SelectedItemCountChangeListener());

        Session sess = Session.getInstance();
        String sessionId = sess.getSessionId();
        if (sessionId != null && !sessionId.isEmpty()) {
            service = HappyServiceFactory.getService(sess.getSessionId());
            characterName.value.set(sess.getDisplayName());
            loadInitialData();
        }
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
                characterName.value.set(displayName);
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
                largeCategory.values.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void querySmallCategory() {
        LargeCategory lc = largeCategory.getSelected();
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
                smallCategory.values.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void queryItemCount() {
        LargeCategory lc = largeCategory.getSelected();
        if (lc == null) {
            LOGGER.debug("large category is null");
            return;
        }
        SmallCategory sc = smallCategory.getSelected();
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
                itemCount.values.addAll(data);
            });
        }, (Throwable t) -> {
            LOGGER.error("large category get error", t);
        });
    }

    private void unsetDisabled() {
        clearOptions();
        setDisabledDefault();

        LargeCategory lc = largeCategory.getSelected();
        SmallCategory sc = smallCategory.getSelected();
        if (lc == null || (lc.isSmallCategory() && sc == null)) {
            return;
        }
        int lcid = lc.getLargeCategoryId();
        int scid = lc.isSmallCategory() ? sc.getSmallCategoryId() : lc.getSmallCategoryId();

        if (lcid == 1 || lcid == 2 || lcid == 3) {
            // 武器, 盾, 防具の場合
            itemCount.disabled.set(false);
            quality.disabled.set(false);
            numOfRenkin.disabled.set(false);

            quality.values.addAll(qualities);
            numOfRenkin.values.addAll(numOfRenkins);
        } else if (lcid == 5 || lcid == 11 || scid == 606) {
            // 職人どうぐ, 釣りどうぐ, 消費アイテム>料理 の場合
            itemCount.disabled.set(false);
            quality.disabled.set(false);
            quality.values.addAll(qualities);
        } else if (scid == 605) {
            // 消費アイテム>依頼書 の場合

        } else if (lcid == 6 || lcid == 7 || lcid == 8 || lcid == 12 || lcid == 9 || lcid == 10) {
            // (料理と依頼書以外の)消費アイテム, 素材, 家具, 庭具, レシピ帳, スカウトの書

        }
    }

    private void clearOptions() {
        quality.values.clear();
        numOfRenkin.values.clear();
    }

    private void setDisabledDefault() {
        itemCount.disabled.set(true);
        quality.disabled.set(true);
        numOfRenkin.disabled.set(true);
    }

    private class SelectedLargeCategoryChangeListener implements ChangeListener<LargeCategory> {

        @Override
        public void changed(ObservableValue<? extends LargeCategory> observable, LargeCategory oldValue, LargeCategory newValue) {
            if (oldValue == newValue) {
                return;
            }
            smallCategory.values.clear();
            if (newValue.isSmallCategory()) {
                querySmallCategory();
            } else {
                itemCount.values.clear();
                queryItemCount();
            }
        }
    }

    private class SelectedSmallCategoryChangeListener implements ChangeListener<SmallCategory> {

        @Override
        public void changed(ObservableValue<? extends SmallCategory> observable, SmallCategory oldValue, SmallCategory newValue) {
            if (oldValue == newValue) {
                return;
            }
            unsetDisabled();
            itemCount.values.clear();
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
