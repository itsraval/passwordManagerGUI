

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class mainPage {

    public static LocalDateTime maxTime;
    private static LocalDateTime inactiveTime;
    private static int inactiveTimeIncrement = 15;
    public static boolean pass = false;

    public static boolean inactive;
    public static boolean sendEmail;
    public static int accessTime;
    public static String email;

    private static ArrayList<Account> cText;
    private static ScrollPane scrollPane;
    private static GridPane layout;
    private static Label noAccounts = new Label("No saved accounts.");
    private static int rowClicked;
    private static Label letter;
    private static TextField search;

    public static Inactive inactiveObj;

    public static void updateInactive(){
        inactiveTime = LocalDateTime.now().plusMinutes(inactiveTimeIncrement);
        inactiveObj.setInactiveTime(inactiveTime);
    }

    public static boolean inTime() {
        // return true if its the current time is lower than the maxTime
        LocalDateTime nowTime = LocalDateTime.now();
        if (accessTime == -1) return true;
        if (nowTime.compareTo(maxTime) < 0) {
            maxTime = nowTime.plusMinutes(accessTime);
            return true;
        } else
            return false;
    }

    private static void readSettings(File settingsFile){
        try{
            Scanner scan = new Scanner(settingsFile);
            scan.nextLine();
            scan.nextLine();
            String line = scan.nextLine();

            String[] s = line.split(" ");
            if (s[2].equals("OFF")) accessTime = -1;
            else accessTime = Integer.parseInt(s[s.length-1]);

            line = scan.nextLine();
            s = line.split(" ");
            if (s[2].equals("OFF")) sendEmail = false;
            email = s[3];

            line = scan.nextLine();
            if (line.contains("OFF")) inactive = false;
            else inactive = true;
            inactiveObj.setI(inactive);

            scan.close();
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    private static void copyPassword(Login log, String p) {
        // copy CALL
        if (inTime()) {
            if (p.equals("!"))
                p = "";
            Utility.copyToClipboard(p);
        } else {
            passwordAsk.display(log);
            if (inTime()) {
                if (p.equals("!"))
                    p = "";
                Utility.copyToClipboard(p);
            }
        }
        pass = false;
    }

    public static void modifyA(BorderPane page, File accountFile, Login log, Account a) {
        // modify CALL
        if (inTime()) {
            page.setRight(modifyAccount.display(page, layout, accountFile, cText, log, a));
        } else {
            passwordAsk.display(log);
            if (inTime()) {
                page.setRight(modifyAccount.display(page, layout, accountFile, cText, log, a));
            }
        }
        pass = false;
    }

    private static void delete(File accountFile, BorderPane page, Login log) {
        // delete function
        if (pass) {
            cText.remove(rowClicked);
            log.accountFileUpdate(accountFile, cText);

            layout = new GridPane();
            setColumn(layout);
            setRow(layout, cText);
            scrollPane.setContent(layout);

            layout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    updateInactive();
                    
                    clickGrid(event, layout);
                    if (inTime()) {
                        if (cText.size() > 0)
                        page.setRight(
                                ViewAccount.display(page, layout, accountFile, cText, log, cText.get(rowClicked)));
                    } else {
                        passwordAsk.display(log);
                        if (inTime()) {
                            if (cText.size() > 0)
                            page.setRight(
                                    ViewAccount.display(page, layout, accountFile, cText, log, cText.get(rowClicked)));
                        }
                    }
                    pass = false;
                }
            });

            centralPage(accountFile, log, page, layout);
            page.setRight(RightPageNoAccount.display());
            if (cText.size() == 0) letter.setText("?");
            else letter.setText(getAccountFromVBar().getFirstLetter());
        }
    }

    private static void deleteAccount(File accountFile, BorderPane page, Login log) {
        // delete account CALL
        ControlIfSure.display(2);
        if (inTime()) {
            page.setRight(RightPageNoAccount.display());
            delete(accountFile, page, log);
            
        } else {
            passwordAsk.display(log);
            if (inTime()) {
                page.setRight(RightPageNoAccount.display());
                delete(accountFile, page, log);
            }
        }
        pass = false;
    }

    public static void clickGrid(MouseEvent event, GridPane grid) {
        // click on grid
        Node clickedNode = event.getPickResult().getIntersectedNode();
        try {
            if (clickedNode != grid) {
                // click on descendant node
                Node parent = clickedNode.getParent();
                while (parent != grid) {
                    clickedNode = parent;
                    parent = clickedNode.getParent();
                }
                Integer rowIndex = GridPane.getRowIndex(clickedNode) / 2;
                rowClicked = rowIndex.intValue();// return the row cliccked
            }
        } catch (NullPointerException e) {
        }
    }

    private static void addRow(GridPane layout) {
        // add new row
        RowConstraints row = new RowConstraints(100);
        layout.getRowConstraints().add(row);
        row = new RowConstraints(20);
        layout.getRowConstraints().add(row);
    }

    public static void updateMainPageAdd(File accountFile, BorderPane page, GridPane layout, Login log, Account a) {
        // update main page when add new account
        if (noAccounts.isVisible()) {// if there are no accounts
            noAccounts.setVisible(false);
            layout.getRowConstraints().get(0).setMaxHeight(100);
            layout.getRowConstraints().get(0).setMinHeight(100);
            RowConstraints row = new RowConstraints(20);
            layout.getRowConstraints().add(row);

        } else
            addRow(layout);
        centralPage(accountFile, log, page, layout);
        page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, a));
        letter.setText(getAccountFromVBar().getFirstLetter());
    }

    public static void updateMainPageEdit(File accountFile, BorderPane page, GridPane layout, Login log, Account a) {
        // update main page when edit an account
        centralPage(accountFile, log, page, layout);
        page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, a));
        letter.setText(getAccountFromVBar().getFirstLetter());
    }

    private static int firstRow(int len, double v) {
        // formula to get the position in the array viewed in first row
        double d = 160 + 120 * (len - 7);
        double x = ((d * v - 65) / 120) + 1;
        return (int) x;
    }

    private static Account getAccountFromVBar() {
        // get the account from the vertical scroll bar
        int i = firstRow(cText.size(), scrollPane.getVvalue());
        return cText.get(i);
    }

    private static void setRow(GridPane layout, ArrayList<Account> accounts) {
        // Set row grid
        cText = accounts;
        int k = cText.size();
        if (k == 0) {
            RowConstraints onlyRow = new RowConstraints(645);
            onlyRow.setValignment(VPos.CENTER);
            layout.getRowConstraints().add(onlyRow);

            noAccounts.setVisible(true);
            noAccounts.setStyle("-fx-font-size: 20px;");
            layout.add(noAccounts, 1, 0, 1, 1);
            layout.getColumnConstraints().get(1).setHalignment(HPos.CENTER);
        }

        for (int i = 0; i < k; i++) {
            RowConstraints row = new RowConstraints(100);
            layout.getRowConstraints().add(row);
            row = new RowConstraints(20);
            layout.getRowConstraints().add(row);
        }
    }

    private static void setColumn(GridPane layout) {
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(7);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(670);
        layout.getColumnConstraints().add(column);

        maxTime = LocalDateTime.now().plusMinutes(accessTime);
        updateInactive();
    }

    private static void setRowAccount(GridPane layout) {
        // Set row grid of an account
        RowConstraints row = new RowConstraints(15);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(20);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(15);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(15);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(20);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(15);
        layout.getRowConstraints().add(row);
    }

    private static void setColumnAccount(GridPane layout) {
        // Set column grid of an account
        ColumnConstraints column = new ColumnConstraints(10);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(70);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(20);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(220);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(220);
        layout.getColumnConstraints().add(column);

        for (int i = 0; i < 3; i++) {
            column = new ColumnConstraints(40);
            layout.getColumnConstraints().add(column);
        }

        column = new ColumnConstraints(10);
        layout.getColumnConstraints().add(column);
    }

    private static void displayAccount(File accountFile, Login log, BorderPane page, GridPane layout, int i, Account a) {
        GridPane accountRow = new GridPane();
        accountRow.getStyleClass().add("account-row");
        // accountRow.setGridLinesVisible(true);
        setRowAccount(accountRow);
        setColumnAccount(accountRow);
        layout.add(accountRow, 1, i, 1, 1);

        Image image = a.getImage();// account logo image
        ImageView iw = new ImageView(image);
        iw.setOnMouseClicked(e -> Utility.urlImage(a.getUrl()));
        iw.getStyleClass().add("showHand");
        iw.setFitHeight(70);
        iw.setFitWidth(70);
        accountRow.add(iw, 1, 1, 1, 4);

        Label title = new Label(Utility.accountString(a.getSite()));// account site
        title.getStyleClass().add("account-title");
        accountRow.add(title, 3, 1, 1, 2);

        Label url = new Label(Utility.accountString(a.getUrl()));// account url
        url.getStyleClass().add("showHand");
        url.setOnMouseClicked(e -> Utility.openURL(Utility.accountString(a.getUrl())));
        url.getStyleClass().add("account-url");
        accountRow.add(url, 3, 4, 1, 1);

        if (!Utility.accountString(a.getUser()).equals("")) {
            Label user = new Label("User:\t" + Utility.accountString(a.getUser()));// account user
            accountRow.add(user, 4, 2, 1, 1);
        }

        if (!Utility.accountString(a.getEmail()).equals("")) {
            Label email = new Label("Email:\t" + Utility.accountString(a.getEmail()));// account email
            accountRow.add(email, 4, 4, 1, 1);
        }

        Image copy = new Image(mainPage.class.getResource("images/copy-icon.png").toString());
        ImageView cp = new ImageView(copy);
        cp.setOnMouseClicked(e -> copyPassword(log, a.getPasswordNotCrypted()));
        cp.getStyleClass().add("showHand");
        cp.setFitHeight(30);
        cp.setFitWidth(30);
        GridPane.setHalignment(cp, HPos.CENTER);
        accountRow.add(cp, 5, 2, 1, 2);

        Image modify = new Image(mainPage.class.getResource("images/modify-icon.png").toString());
        ImageView md = new ImageView(modify);
        md.setOnMouseClicked(e -> modifyA(page, accountFile, log, a));
        md.getStyleClass().add("showHand");
        md.setFitHeight(30);
        md.setFitWidth(30);
        GridPane.setHalignment(md, HPos.CENTER);
        accountRow.add(md, 6, 2, 1, 2);

        Image delete = new Image(mainPage.class.getResource("images/delete-icon.png").toString());
        ImageView del = new ImageView(delete);
        del.setOnMouseClicked(e -> deleteAccount(accountFile, page, log));
        del.getStyleClass().add("showHand");
        del.setFitHeight(30);
        del.setFitWidth(30);
        GridPane.setHalignment(del, HPos.CENTER);
        accountRow.add(del, 7, 2, 1, 2);
    }

    private static void centralPage(File accountFile, Login log, BorderPane page, GridPane layout) {
        // central page creation
        int k = cText.size();
        int j = 0;
        for (int i = 0; i < k * 2; i++) {
            displayAccount(accountFile, log, page, layout, i, cText.get(j));
            j++;
            i++;
        }
    }

    private static HBox topApp(ScrollPane searchScrollPane, BorderPane page, double vBar, File accountFile, Login log) {
        // application bar
        HBox box = new HBox();
        box.setMaxHeight(40);
        box.setMinHeight(40);
        box.setAlignment(Pos.CENTER);

        letter = new Label("?");
        letter.setMinWidth(50);
        letter.setMaxWidth(50);
        if (cText.size() > 0) {
            letter.setText(cText.get(0).getFirstLetter());
        }
        letter.getStyleClass().add("letter");

        Region spacer = new Region();
        spacer.setPrefWidth(230);

        Image add = new Image(mainPage.class.getResource("images/add-account-icon.png").toString());
        ImageView addA = new ImageView(add);
        addA.setOnMouseClicked(e -> page.setRight(addAccount.display(page, layout, accountFile, cText, log)));
        addA.getStyleClass().add("showHand");
        addA.setFitHeight(35);
        addA.setFitWidth(35);

        Region spacer0 = new Region();
        spacer0.setPrefWidth(10);

        Image pG = new Image(mainPage.class.getResource("images/pswG-icon.png").toString());
        ImageView pswGen = new ImageView(pG);
        pswGen.setOnMouseClicked(e -> passwordGenerator.display());
        pswGen.getStyleClass().add("showHand");
        pswGen.setFitHeight(35);
        pswGen.setFitWidth(35);

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.SOMETIMES);

        search = new TextField();
        search.setPromptText("Search");
        search.setFocusTraversable(false);

        search.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue) {
                    Search.display(searchScrollPane, scrollPane, page, layout, accountFile, log, cText, search.getText());
                }
                else
                {
                    searchScrollPane.setVisible(false);
                }
            }
        });

        search.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                String text = search.getText() + ke.getText();
                Search.display(searchScrollPane, scrollPane, page, layout, accountFile, log, cText, text);

                updateInactive();
            }
            
        });

        Region spacer2 = new Region();
        spacer2.setPrefWidth(25);

        box.getChildren().addAll(letter, spacer, addA, spacer0, pswGen, spacer1, search, spacer2);
        return box;
    }

    public static void display(File accountFile, File settingsFile, Login log, ArrayList<Account> accounts) {
        // display mane page
        Stage window = new Stage();
        window.getIcons().add(new Image(mainPage.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);

        inactiveObj = new Inactive(window);

        ScrollPane searchScrollPane = new ScrollPane();    
        searchScrollPane.setMinWidth(163);  
        searchScrollPane.setMaxWidth(163);
        searchScrollPane.setMinHeight(170);
        searchScrollPane.setMaxHeight(170);
        searchScrollPane.setTranslateX(44);
        searchScrollPane.setTranslateY(-229);
        searchScrollPane.getStyleClass().add("showHand");
        searchScrollPane.setVisible(false);
        searchScrollPane.setStyle("-fx-background-color: rgba(54, 54, 54, 1);");

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
        StackPane sp = new StackPane(page, searchScrollPane);

        StackPane root = new StackPane(sp);
        root.setPadding(new Insets(10, 10, 10, 10));
        
        Scene scene = new Scene(root, 1120, 770);
        scene.setFill(Color.TRANSPARENT);

        noAccounts.setVisible(false);

        scrollPane = new ScrollPane();

        // grid setup
        layout = new GridPane();
        readSettings(settingsFile);
        setColumn(layout);
        setRow(layout, accounts);
        centralPage(accountFile, log, page, layout);
        // layout.setGridLinesVisible(true);

        scrollPane.setContent(layout);

        titleBar.topBarMenu(window, scene, page, accounts, log, accountFile, settingsFile, false);

        VBox vCenter = new VBox();
        vCenter.getChildren().addAll(topApp(searchScrollPane, page, scrollPane.getVvalue(), accountFile, log), scrollPane);

        page.setCenter(vCenter);
        page.setRight(RightPageNoAccount.display());

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(inactiveObj, 1, 1, TimeUnit.MINUTES);

        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                letter.setText(getAccountFromVBar().getFirstLetter());
            }
          
        });
        
        layout.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                updateInactive();
                clickGrid(event, layout);
                if(inTime()){
                    if (cText.size() > 0) 
                    page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, cText.get(rowClicked)));
                }else{
                    passwordAsk.display(log);
                    if(inTime()){
                        if (cText.size() > 0) 
                        page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, cText.get(rowClicked)));
                    }
                }
                pass = false;
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouse) {
                Node clickedNode = mouse.getPickResult().getIntersectedNode();
                if (!clickedNode.equals(search)){
                    letter.requestFocus();
                }
                updateInactive();
            }
            
        });

       
        scene.getStylesheets().add(mainPage.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
