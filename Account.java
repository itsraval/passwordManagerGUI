
import javafx.scene.image.Image;

public class Account implements Comparable<Account> {
    private Image image;
    private String site;
    private String url;
    private String user;
    private String email;
    private String password;
    private String pin;

    private static String toUpper(String s){
        // set the first letter uppercase
        String text = "";
        char c = s.charAt(0);
        if((int)c >= 97 && (int)c <= 122){
            text = text + (char)((int)c-32);
            for (int i=1; i<s.length();i++){
                text = text + s.charAt(i);
            }
            return text;
        }else return s;
    }

    Account (String site, String image, String url, String user, String email, String password, String pin){
        this.site = toUpper(site);
        Image icon = new Image(image);
        if(icon.isError()){
            this.image = new Image(Account.class.getResource("images/image-default.png").toString());
        }else this.image = icon;
        this.url = url;
        this.user = user;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    public String getSite() {
        return site;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }

    public String getPasswordNotCrypted(){
        return AES.decrypt(password);
    }

    public String getPinNotCrypted(){
        return AES.decrypt(pin);
    }

    @Override
    public int compareTo(Account a) {
        return this.site.compareTo(a.site);
        // sorth twith Collections.sort(array);
    }

    public void edit(String site, String url, String user, String email, String password, String pin){
        // edit account
        this.site = toUpper(site);
        this.url = url;
        this.user = user;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public void setPasswordPin(String password, String pin){
        // edit the password and the pin
        this.password = password;
        this.pin = pin;
    }

    public String printTextFormat(){
        // return the account in string format for the account file
        return site + " " + image.getUrl() + " " + url + " " + user + " " + email + " " + password + " " + pin + "\n\n";
    }

    public String printExport(){
        // return the account in string format to be exported
        String text = Utility.accountString(site) + "\n";
        text = text + "url: " + Utility.accountString(url) + "\n";
        text = text + "user: " + Utility.accountString(user) + "\n";
        text = text + "email: " + Utility.accountString(email) + "\n";
        text = text + "password: " + Utility.accountPasswordString(AES.decrypt(password)) + "\n";
        text = text + "pin: " + Utility.accountPasswordString(AES.decrypt(pin)) + "\n\n";
        return text;
    }

    public String getFirstLetter(){
        // return the first letter of the account site in string format
        if (Utility.accountString(this.site).equals("")) return "";
        else return "" + site.charAt(0);
    }
}
