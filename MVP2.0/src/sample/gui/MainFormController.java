package sample.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.Aircraft;
import sample.models.Train;
import sample.models.Transport;
import sample.models.TransportModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView mainTable;
    public ComboBox cmbFilter;
    public Label z1;
    TransportModel transportModel = new TransportModel();

    ObservableList<Class<? extends Transport>> transportTypes = FXCollections.observableArrayList(
            Transport.class,
            Train.class,
            Aircraft.class
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transportModel.addDataChangedListener(transports -> {
            mainTable.setItems(FXCollections.observableArrayList(transports));
        });
        cmbFilter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.transportModel.setTransportFilter((Class<? extends Transport>) newValue);
        });
        //запрос 1 подсчет времени в пути
        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Transport transport = (Transport) observable.getValue();
            z1.setText(String.format("%s пробудет в пути %s часа", transport.getName(), transport.getTime(transport.distance, transport.speed)));
        });

        cmbFilter.setItems(transportTypes);
        // выбрали первый элемент в списке
        cmbFilter.getSelectionModel().select(0);

        // переопределил метод преобразования имени класса в текст
        cmbFilter.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                // просто перебираем тут все возможные варианты
                if (Transport.class.equals(object)) {
                    return "Весь транспорт";
                } else if (Train.class.equals(object)) {
                    return "Поезда";
                } else if (Aircraft.class.equals(object)) {
                    return "Самолеты";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });

        TableColumn<Transport, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Transport, String> sumColumn = new TableColumn<>("Скорость в км/ч");
        sumColumn.setPrefWidth(125);
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        TableColumn<Transport, String> distColumn = new TableColumn<>("Расстояние до пункта прибытия в км");
        distColumn.setPrefWidth(240);
        distColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        TableColumn<Transport, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setPrefWidth(300);
        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });
        descriptionColumn.setCellFactory(x -> {
            TableCell<Transport, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(descriptionColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        // подцепляем столбцы к таблице
        mainTable.getColumns().addAll(nameColumn, sumColumn, distColumn, descriptionColumn);
    }

    public void addTransport(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportForm.fxml"));
        Parent root = loader.load();

        // ну а тут создаем новое окно
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        TransportFormController controller = loader.getController();
        // передаем модель
        controller.transportModel = transportModel;
        // открываем окно и ждем пока его закроют
        stage.showAndWait();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(this.mainTable.getScene().getWindow());

        TransportFormController controller = loader.getController();
        controller.setTransport((Transport) this.mainTable.getSelectionModel().getSelectedItem());
        controller.transportModel = transportModel; // передаем модель в контроллер

        stage.showAndWait();
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        Transport transport = (Transport) this.mainTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Точно удалить %s?", transport.getName()));

        // если пользователь нажал OK
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            transportModel.delete(transport.id);
        }
    }

    public void onSaveToFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());
        if (file != null) {
            transportModel.saveToFile(file.getPath());
        }
    }

    public void onLoadFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());
        if (file != null) {
            transportModel.loadFromFile(file.getPath());
        }
    }

    public void onCalcTime(ActionEvent actionEvent) {
        Transport transport = (Transport) this.mainTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Время в пути");
        alert.setHeaderText("Запрос 1: нахождение времени пути транспорта");

        alert.setContentText(String.format("%s пробудет в пути %s", transport.getName(), transport.getTime(transport.distance, transport.speed)));
        alert.showAndWait();
    }

    public void onCommonRoadTo(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Общий путь для...");
        alert.setHeaderText("Запрос 2: нахождение общего пути всего транспорта определенного типа.");
        ButtonType all = new ButtonType("Всего транспорта");
        ButtonType train = new ButtonType("Поездов");
        ButtonType aircraft = new ButtonType("Самолетов");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(all, train, aircraft);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == all) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Общий путь для всего транспорта");
            alert.setHeaderText("Запрос 2: нахождение общего пути всего транспорта определенного типа");
            alert.setContentText(String.format("Общий путь для всего транспорта составляет %s в км", transportModel.calcRoad("all")));
            alert.showAndWait();
        } else if (option.get() == train) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Общий путь для поездов");
            alert.setHeaderText("Запрос 2: нахождение общего пути всего транспорта определенного типа");
            alert.setContentText(String.format("Общий путь для поездов составляет %s в км", transportModel.calcRoad("train")));
            alert.showAndWait();
        } else if (option.get() == aircraft) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Общий путь для самолетов");
            alert.setHeaderText("Запрос 2: нахождение общего пути всего транспорта определенного типа");
            alert.setContentText(String.format("Общий путь для самолетов составляет %s в км", transportModel.calcRoad("aircraft")));
            alert.showAndWait();
        }
    }
}
