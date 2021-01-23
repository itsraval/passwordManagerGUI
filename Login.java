

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Login{
    private String user;
    private String password;

    public Login(String user, String password) {
        this.user = user;
        this.password = SHA256.hash(password);
        AES.setKey(password);
    }

    public static void createCredentials(File accountFile, String user, String password){
        // Checks if the 2 passwords are equal
        try {
            FileWriter fWriter = new FileWriter(accountFile);
            fWriter.write(SHA256.hash(user) + "\n" + SHA256.hash(password) + "\n");
            fWriter.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public boolean checkUser(String user){
        // Check input user with login user
        return this.user.equals(user);
    }

    public boolean checkPassword(String password){
        // Check input password with login password
        return this.password.equals(SHA256.hash(password));
    }

    private void newEncryptionPassword(ArrayList<Account> cText, String newPassword){
        // change cipher password for all accounts
        ArrayList<String> passwords = new ArrayList<String>();
        ArrayList<String> pins = new ArrayList<String>();

        for (int i=0; i<cText.size(); i++) {
            passwords.add(cText.get(i).getPasswordNotCrypted());
            pins.add(cText.get(i).getPinNotCrypted());
        }
        AES.setKey(newPassword);

        for (int i=0; i<cText.size(); i++) {
            cText.get(i).setPasswordPin(AES.encrypt(passwords.get(i)), AES.encrypt(pins.get(i)));
        }        
    }

    public void changeLoginUserPassword(File accountFile, ArrayList<Account> cText, String newUser, String newPassword){
        newEncryptionPassword(cText, newPassword);

        this.user = newUser;
        this.password = SHA256.hash(newPassword);

        String text = SHA256.hash(this.user) + "\n";
        text = text + this.password + "\n\n";

        for (Account a : cText) {
            text = text + a.printTextFormat();
        }
        // write to file
        try {
            FileWriter fWriter = new FileWriter(accountFile);
            fWriter.write(text);
            fWriter.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void accountFileUpdate (File accountFile, ArrayList<Account> cText) {
        String text = SHA256.hash(this.user) + "\n";
        text = text + this.password + "\n\n";
        Collections.sort(cText);
        for (Account account : cText) {
            text = text + account.printTextFormat();
        }
        // write to file
        try {
            FileWriter fWriter = new FileWriter(accountFile);
            fWriter.write(text);
            fWriter.close();
        } catch (IOException e) {
            //e.printStackTrace();
        } 
    }
}
