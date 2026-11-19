package com.example;

import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(12));
        pane.setHgap(5);
        pane.setVgap(5);
        Scene scene = new Scene(pane, 400, 300);
        stage.setTitle("Cake Order Form");

        Label lblName = new Label("Enter your name:");
        pane.add(lblName, 0, 0);
        TextField txtName = new TextField();
        pane.add(txtName, 1, 0);

        Label lblPhone = new Label("Enter your phone number:");
        pane.add(lblPhone, 0, 1);  
        TextField txtPhone = new TextField();
        pane.add(txtPhone, 1, 1);

        Label lblCakeType = new Label("Select cake type:");
        pane.add(lblCakeType, 0, 2);   
        ComboBox<String> cmbCakeType = new ComboBox<>();
        cmbCakeType.getItems().addAll(
            "Apple",
            "Chocolate",
            "Carrot", 
            "Cheesecake",
            "Opera",
            "Tiramisu",
            "Coffee"
        );
        pane.add(cmbCakeType, 1, 2);

        Label lblCakeSize = new Label("Select cake size:");
        pane.add(lblCakeSize, 0, 3);   
        RadioButton rbSmall = new RadioButton("Small");
        RadioButton rbMedium = new RadioButton("Medium");  
        RadioButton rbLarge = new RadioButton("Large");
        ToggleGroup sizeGroup = new ToggleGroup();
        rbSmall.setToggleGroup(sizeGroup);
        rbMedium.setToggleGroup(sizeGroup);
        rbLarge.setToggleGroup(sizeGroup);
        pane.add(rbSmall, 1, 3);
        pane.add(rbMedium, 1, 4);
        pane.add(rbLarge, 1, 5);

        CheckBox chkFreeDelivery = new CheckBox("Inside Free Delivery Area");
        pane.add(chkFreeDelivery, 1, 6);

        Button btnSave = new Button("Save Order");
        pane.add(btnSave, 1, 7); 

        Button btnQuit = new Button("Quit");
        pane.add(btnQuit, 2, 7);

        btnQuit.setOnAction(e -> {
            Platform.exit();
        });

        btnSave.setOnAction(e -> {
            String customerName = txtName.getText();
            String phoneNumber = txtPhone.getText();
            String cakeType = cmbCakeType.getValue();
            String cakeSize = null;
            if (rbSmall.isSelected()) {
                cakeSize = "Small";
            } else if (rbMedium.isSelected()) {
                cakeSize = "Medium";
            } else if (rbLarge.isSelected()) {
                cakeSize = "Large";
            }
            boolean freeDelivery = chkFreeDelivery.isSelected();

            try {
                Order order = new Order(customerName, phoneNumber, cakeType, cakeSize, freeDelivery);
                double price = order.calculatePrice();
                try (FileWriter writer = new FileWriter("Orders.txt", true)) {
                    writer.write(String.format(
                    "Name: %s%n" + 
                    "Phone: %s%n" + 
                    "Cake Type: %s%n" + 
                    "Cake Size: %s%n" + 
                    "Price: $%.2f%n" + 
                    "Free Delivery: %b%n%n", 
                    order.getCustomerName(),
                    order.getPhoneNumber(),
                    order.getCakeType(),
                    order.getCakeSize(), 
                    price,
                    order.isFreeDelivery()
                    ));
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Saved");
                alert.setHeaderText("Order Saved Successfully");
                alert.setContentText("Your order has been saved.");
                alert.showAndWait();
                System.out.println("Order saved successfully!"); 
                clearForm(txtName, txtPhone, cmbCakeType, sizeGroup, chkFreeDelivery);
                
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
            catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File Writing Error");
                alert.setContentText("Error writing to file: " + ex.getMessage());
                alert.showAndWait();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    private void clearForm(TextField txtName, TextField txtPhone, ComboBox<String> cmbCakeType, ToggleGroup sizeGroup, CheckBox chkFreeDelivery) {
        txtName.clear();
        txtPhone.clear();
        cmbCakeType.getSelectionModel().clearSelection();
        sizeGroup.selectToggle(null);
        chkFreeDelivery.setSelected(false);
    }
    public static void main(String[] args) {
        launch();
    }

}