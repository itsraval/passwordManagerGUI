

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Settings {

    private static void saveSettings(File settingsFile, CheckBox timeAccess, TextField minText, CheckBox emailSender, TextField email, CheckBox inactive){
        String text = "SETTINGS - PASSWORD MANAGER\n\n";

        if(minText.getText().equals("")) return;

        for (int i=0; i<minText.getText().length(); i++){
            if (minText.getText().charAt(i) < 48 || minText.getText().charAt(i) > 57){
                minText.setId("wrong-password");
                minText.setText("");
                return;
            } 
        }
        if (Integer.parseInt(minText.getText()) < 1)  return;
        if (Integer.parseInt(minText.getText()) > 7200){
            minText.setId("wrong-password");
            minText.setText("7200");
            return;
        }  
        minText.setId("correct-password");
        
        if (timeAccess.isSelected()){
            text = text + "Access Time: ON " + minText.getText() + "\n";
            mainPage.accessTime = Integer.parseInt(minText.getText());
            mainPage.maxTime = LocalDateTime.now().plusMinutes(mainPage.accessTime);
        }else{
            text = text + "Access Time: OFF " + minText.getText() + "\n";
            mainPage.accessTime = -1;
        }

        if(emailSender.isSelected()){
            text = text + "Email Sender: " + "ON " + email.getText() + "\n";
        }else{
            text = text + "Email Sender: " + "OFF " + email.getText() + "\n";
        }
        mainPage.sendEmail = emailSender.isSelected();
        mainPage.email = email.getText();

        if(inactive.isSelected()){
            text = text + "Inactive: " + "ON\n";
        }else{
            text = text + "Inactive: " + "OFF\n";
        }
        mainPage.inactive = inactive.isSelected();
        mainPage.inactiveObj.setI(inactive.isSelected());

        try {
            FileWriter fWriter = new FileWriter(settingsFile);
            fWriter.write(text);
            fWriter.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }   
        mainPage.updateInactive();     
    }

    private static void enableDisable(CheckBox box, Label l, TextField t){
        if (box.isSelected()){
            if (l != null) l.setDisable(true);
            t.setDisable(true);   
        }else{
            if (l !=null) l.setDisable(false);
            t.setDisable(false);
        }
        mainPage.updateInactive();
    }
    
    private static void setRow(GridPane layout){
        // Set row grid
        RowConstraints row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
        for (int i = 0; i < 5; i++) {
            row = new RowConstraints(40);
            layout.getRowConstraints().add(row);
        }

        row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
    }

    private static void setColumn(GridPane layout){
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(50);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(50);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(File settingsFile, Stage window, GridPane layout, int accessTime, boolean sendEmail, boolean inact){
        //title
        Label title = new Label("Settings");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 3, 0, 3, 1);

        //time access
        Label timeAccessText = new Label("Access Time:");
        Tooltip ttat = new Tooltip("The application will ask\nto typethe password\nto access anything.\nAfter Minutes: ....");
        timeAccessText.setTooltip(ttat);
        CheckBox timeAccess = new CheckBox();
        if (accessTime == -1) timeAccess.setSelected(false);
        else timeAccess.setSelected(true);
        GridPane.setHalignment(timeAccess, HPos.CENTER);
        layout.add(timeAccessText, 2, 1, 4, 1);
        layout.add(timeAccess, 6, 1, 1, 1);

        //time access SUB
        Label timeAccessTextMinutes = new Label("Minutes:");
        TextField minText = new TextField("" + accessTime);
        if (accessTime == -1) {
            timeAccessTextMinutes.setDisable(true);
            minText.setText("" + 5);
            minText.setDisable(true);
            
        }
        minText.setMinWidth(50);
        minText.setMaxWidth(50);
        minText.setAlignment(Pos.CENTER);
        GridPane.setHalignment(minText, HPos.CENTER);
        Tooltip tmin = new Tooltip("Min value: 1\nMax value: 7200");
        minText.setTooltip(tmin);
        layout.add(timeAccessTextMinutes, 3, 2, 2, 1);
        layout.add(minText, 5, 2, 1, 1);

        timeAccess.setOnMousePressed(e -> enableDisable(timeAccess, timeAccessTextMinutes, minText));
                
        //email sender
        Label emailSenderText = new Label("Send email if login fails (5 times).");
        Tooltip test = new Tooltip("If the Login fails over 5 times,\nsend an email to\nthe Login User email.");
        emailSenderText.setTooltip(test);
        CheckBox emailSender = new CheckBox();
        emailSender.setSelected(sendEmail);
        GridPane.setHalignment(emailSender, HPos.CENTER);
        layout.add(emailSenderText, 2, 3, 4, 1);
        layout.add(emailSender, 6, 3, 1, 1);

        //email sender SUB
        TextField email = new TextField(mainPage.email);
        if (!emailSender.isSelected()) {
            email.setDisable(true);
        }
        email.setAlignment(Pos.CENTER);
        GridPane.setHalignment(email, HPos.CENTER);
        layout.add(email, 3, 4, 3, 1);

        emailSender.setOnMousePressed(e -> enableDisable(emailSender, null, email));

        //inactive
        Label inactiveText = new Label("Automatically close the application if inactive.");
        Tooltip tit = new Tooltip("If you are inactive on this application\nfor at least 15 minutes,\nit will close automatically.");
        inactiveText.setTooltip(tit);
        CheckBox inactive = new CheckBox();
        inactive.setSelected(inact);
        GridPane.setHalignment(inactive, HPos.CENTER);
        layout.add(inactiveText, 2, 5, 4, 1);
        layout.add(inactive, 6, 5, 1, 1);

        //button generate
        Button apply = new Button("Apply");
        apply.getStyleClass().add("button-default");
        apply.setOnAction(e -> saveSettings(settingsFile, timeAccess, minText, emailSender, email, inactive));
        layout.add(apply, 1, 6, 3, 1);

        //button copy
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-default");
        cancel.setOnAction(e -> window.close());
        layout.add(cancel, 5, 6, 3, 1);
    }

    public static void display(File settingsFile, int accessTime, boolean sendEmail, boolean inact){
        // Display this page
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(Settings.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 420, 380);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Settings", false);
       
        //grid setup
        GridPane layout = new GridPane();
        centralPage(settingsFile, window, layout, accessTime, sendEmail, inact);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
               
        scene.getStylesheets().add(Settings.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
