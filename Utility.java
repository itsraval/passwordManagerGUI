

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utility {
    // utility functions
    
    public static void changeImageWithText(ImageView iw, Image i1, Image i2, PasswordField password, TextField passwordText){
        // Change see and not see password
        if (iw.getImage().equals(i1)){
            iw.setImage(i2);
            passwordText.setText(password.getText());
            password.setVisible(false);
            passwordText.setVisible(true);   
        }else{
            iw.setImage(i1);
            password.setText(passwordText.getText());
            password.setVisible(true);
            passwordText.setVisible(false);
        }
    }

    public static void changeImage(ImageView iw, Image i1, Image i2){
        // Change see and not see password
        if (iw.getImage().equals(i1)){
            iw.setImage(i2); 
        }else{
            iw.setImage(i1);
        }
    }

    public static void copyToClipboard(String text) {
        // copy the input string into clipboard
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void openURL(String url) {
        // Open url  of the given string
        String urlFromatted;
        if (!url.contains("https") && !url.contains("http")){
            if (!url.contains("www.")) urlFromatted = "www." + url;
            else urlFromatted = url;
        }else urlFromatted = url;
        try{
            try{
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(urlFromatted));
                }
            }catch (URISyntaxException f) {
                f.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void urlImage(String url){
        // Open url on default browser
        url = Utility.accountString(url);
        if (!url.equals("")){
            openURL(url);
        }
    }

    public static void createReadme() {
        // create the readme FILE
        final String readmePath = "C:\\passwordManagerGUI\\README.txt";
        String text = "WARNING!!!\nDO NOT delete files in this folder.\nIf you do, you will reset the 'Password Manager'\n(so you will lose all your saved accounts and your login credentials).\n\n\nThis program is made by Alessandro Ravizzoti.\nCopyright © 2020.";
        try {
            File readmeFile = new File(readmePath); 
            if (!readmeFile.exists()){
                readmeFile.createNewFile();
                FileWriter fWriter = new FileWriter(readmeFile);
                fWriter.write(text);
                fWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSettingsFile() {
        // create the settings FILE
        final String readmePath = "C:\\passwordManagerGUI\\Settings.txt";
        String text = "SETTINGS - PASSWORD MANAGER\n\n";
        text = text + "Access Time: ON " + 5 + "\n";
        text = text + "Email Sender: " + "OFF email@example.com\n";
        text = text + "Inactive: " + "ON\n";

        try {
            File readmeFile = new File(readmePath); 
            if (!readmeFile.exists()){
                readmeFile.createNewFile();
                FileWriter fWriter = new FileWriter(readmeFile);
                fWriter.write(text);
                fWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String accountString(String t){
        // validate string
        if (t.equals("!")) return "";
        return t.replace('~', ' ');
    }

    public static String accountPasswordString(String t){
        // validate password or pin
        if (t.equals("!")) return "";
        else return t;
    }

    public static String chooseInput(PasswordField p, TextField t){
        // selected the last modify text input
        if(p.isVisible()){
            return p.getText();
        }else return t.getText();
    }
}
