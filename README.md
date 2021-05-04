<img src="https://github.com/itsraval/passwordManagerGUI/blob/main/images/icon.png" width="200" height="200">

# passwordManagerGUI
Local Password Manager. With GUI in JavaFX.

You can save as many accounts as you want (based on your local storage).
An account is made by
* site name
* url
* user
* email
* password
* pin
* icon of webpage

or just by some of them.  
All data will only be saved on local store in "C:\passwordManagerGUI".

# Features
* Integrated password generator
* Index letter
* Automatic icon download
* Edit an icon with an image that you want
* Search bar
* Email alert if 5 login failed attempts (Go to settings to set it)
* Auto exit app after timer
* Ask password after timer
* [Web page](https://www.pswmanager.tk)

## Cryptography
This program uses SHA256 to store user and password to login in the app.
This program uses AES to encrypt every stored password.
* SHA256
* AES

## Imported Libraries
* JavaFX
* java.awt
* java.nio
* java.security
* javax.crypto
* javax.imageio
* javax.mail
* net.sf.image4j.codec.ico.ICODecoder
