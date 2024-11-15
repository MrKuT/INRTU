package sample.gui;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.models.Aircraft;
import sample.models.Train;
import sample.models.Transport;
import sample.models.TransportModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TransportFormController implements Initializable {
    public ChoiceBox cmbType;
    public TextField txtName;
    public TextField txtSpeed;
    public TextField txtDist;

    public HBox trainPane;
    public TextField txtWag;

    public HBox airPane;
    public TextField txtRange;
    public TextField txtLoad;


    public TransportModel transportModel;//модель



    final String T_TRAIN = "Поезд";
    final String T_AIRCRAFT = "Самолет";

    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbType.setItems(FXCollections.observableArrayList(
                T_TRAIN,
                T_AIRCRAFT
        ));
        cmbType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });
        updatePanes("");
    }


    public Transport getTransport() {
        Transport result = null;
        int speed = Integer.parseInt(this.txtSpeed.getText());
        int distance = Integer.parseInt(this.txtDist.getText());
        String name = this.txtName.getText();
        switch ((String)this.cmbType.getValue()) {
            case T_TRAIN:
                result = new Train(name, speed,distance, Integer.parseInt(this.txtWag.getText()));
                break;
            case T_AIRCRAFT:
                result = new Aircraft(name, speed,distance,Integer.parseInt(this.txtRange.getText()),Integer.parseInt(this.txtLoad.getText()));
                break;
        }
        return result;
    }
    public void setTransport(Transport transport) {
        // делаем так что если объект редактируется, то нельзя переключать тип
        this.id = transport != null ? transport.id : null;

        this.cmbType.setDisable(transport != null);
        if (transport != null) {
            // ну а тут стандартное заполнение полей в соответствии с переданной едой
            this.txtName.setText(String.valueOf(transport.getName()));
            this.txtSpeed.setText(String.valueOf(transport.getSpeed()));
            this.txtDist.setText(String.valueOf(transport.getDistance()));

            if (transport instanceof Train) { // если фрукт
                this.cmbType.setValue(T_TRAIN);
                this.txtWag.setText(String.valueOf(((Train) transport).getWagons()));
            } else if (transport instanceof Aircraft) { // если булочка
                this.cmbType.setValue(T_AIRCRAFT);
                this.txtRange.setText(String.valueOf(((Aircraft) transport).getRange()));
                this.txtLoad.setText(String.valueOf(((Aircraft) transport).getLoad()));
            }
        }
    }

    public void updatePanes(String value) {
        this.trainPane.setVisible(value.equals(T_TRAIN));
        this.trainPane.setManaged(value.equals(T_TRAIN)); // добавили
        this.airPane.setVisible(value.equals(T_AIRCRAFT));
        this.airPane.setManaged(value.equals(T_AIRCRAFT)); // добавили
    }
    // обработчик нажатия на кнопку Сохранить
    public void onSaveClick(ActionEvent actionEvent) {
        if (this.id != null) {
            // если передавали значит у нас редактирование
            Transport transport = getTransport();
            // подвязываем переданный идентификатор
            transport.id = this.id;
            // отправляем в модель на изменение
            this.transportModel.edit(transport);
        } else {
            // ну а если у нас добавление, просто добавляем объект
            this.transportModel.add(getTransport());
        }
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onCancelClick(ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }
}
