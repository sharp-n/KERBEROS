package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Controller {
    private String IDa = "35lk69G899345ujv";
    private String IDb = "joi845klsjdL0E3D";
    private String key;
    private String keyA;
    private String keyB;
    private int trentCns = 0;
    private int aliceCns = 0;
    private int bobCns = 0;
    private int buf = 0;
    private int bufA = 0;
    private int bufA1 = 0;
    private Long time1;
    private Long time2;

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyA(String keyA) {
        this.keyA = keyA;
    }

    public void setKeyB(String keyB) {
        this.keyB = keyB;
    }

    @FXML
    private Button sendIDMesABut, sendMesABut, sendMesBBut, verMesBBut, verMesABut;

    @FXML
    private Button encryptMesABut, decryptMesABut, encryptMesBBut, encrMesTrentBut, decryptMesBBut;

    @FXML
    private Button genNumABut, genMesTrentBut, genNumBBut;

    @FXML
    private TextField IDaA, IDbA, IDaFromA, IDbFromA, IDaT, IDbT, IDaB, IDbB;

    @FXML
    private TextField timestampAAlice, timestampBAlice;

    @FXML
    private TextField valABob, valBBob, valATrent, valBTrent, valBAlice, valAAlice;

    @FXML
    private TextField keyABob, keyBBob, keyBAlice, keyAAlice, keyATrent, keyBTrent;

    @FXML
    private TextField randEncrABob, randEncrBBob, randEncrNumBAlice, randEncrAAlice;

    @FXML
    private TextField randABob, randBBob, randAAlice, randNumBAlice;

    @FXML
    private Button sendMesTrentBut;

    @FXML
    private TextField encrKeyAlice, encrKeyBob;
    @FXML
    private TextField timestampATrent, timestampBTrent, timestampABob, timestampBBob;

    @FXML
    void SendIDMesA(ActionEvent event) {
        IDaFromA.setText(IDa);
        IDbFromA.setText(IDb);
        AES.genKey();
        setKeyA(AES.getSecretKey());
        encrKeyAlice.setText(keyA);
        AES.genKey();
        setKeyB(AES.getSecretKey());
        encrKeyBob.setText(keyB);
        sendIDMesABut.setDisable(true);
        bufA1++;
        setDisableTBut(false);
        IDaFromA.setEditable(false);
        IDbFromA.setEditable(false);
    }

    @FXML
    void genMesTrent(ActionEvent event) {
        IDaT.setText(IDbFromA.getText());
        IDbT.setText(IDaFromA.getText());
        String time = getTimeStr();
        time1= getTimeLong();
        timestampATrent.setText(time);
        timestampBTrent.setText(time);
        valATrent.setText("20s");
        valBTrent.setText("20s");
        AES.genKey();
        setKey(AES.getSecretKey());
        keyATrent.setText(key);
        keyBTrent.setText(key);
        trentCns++;
    }

    @FXML
    void encrMesTrent(ActionEvent event) {
        if (trentCns==1) {
            AES.setSecretKey(keyA);
            IDaT.setText(AES.encrypt(IDaT.getText()));
            timestampATrent.setText(AES.encrypt(timestampATrent.getText()));
            valATrent.setText(AES.encrypt(valATrent.getText()));
            keyATrent.setText(AES.encrypt(keyATrent.getText()));
            AES.setSecretKey(keyB);
            IDbT.setText(AES.encrypt(IDbT.getText()));
            timestampBTrent.setText(AES.encrypt(timestampBTrent.getText()));
            valBTrent.setText(AES.encrypt(valBTrent.getText()));
            keyBTrent.setText(AES.encrypt(keyBTrent.getText()));
            trentCns++;
        } else if(trentCns<1) AES.errorPrint("You`re trying to encrypt\nan empty message!");
        else AES.errorPrint("You`re trying to encrypt\nalready encrypted message!");
    }

    @FXML
    void sendMesTrent(ActionEvent event) {
        if (trentCns==2) {
            IDaA.setText(IDaT.getText());
            IDbA.setText(IDbT.getText());
            timestampAAlice.setText(timestampATrent.getText());
            timestampBAlice.setText(timestampBTrent.getText());
            valAAlice.setText(valATrent.getText());
            valBAlice.setText(valBTrent.getText());
            keyAAlice.setText(keyATrent.getText());
            keyBAlice.setText(keyBTrent.getText());
            IDaT.setText("");IDbT.setText("");
            timestampATrent.setText("");timestampBTrent.setText("");
            valATrent.setText("");valBTrent.setText("");
            keyATrent.setText("");keyBTrent.setText("");
            setDisableABut(false);
            setDisableTBut(true);
        } else AES.errorPrint("You`re trying to send an empty or\nunencrypted message! " +
                "\nPlease, generate the message and encrypt it.");
    }

    @FXML
    void decryptMesA(ActionEvent event) {
        if (aliceCns==0||aliceCns==5) {
            aliceCns++;
            if (bufA==0) {
                AES.setSecretKey(encrKeyAlice.getText());
                IDaA.setText(AES.decrypt(IDaA.getText()));
                timestampAAlice.setText(AES.decrypt(timestampAAlice.getText()));
                valAAlice.setText(AES.decrypt(valAAlice.getText()));
                keyAAlice.setText(AES.decrypt(keyAAlice.getText()));
                bufA++;
            } else {
                AES.setSecretKey(encrKeyAlice.getText());
                String bufffff = AES.decrypt(randEncrNumBAlice.getText());
                randEncrNumBAlice.setText(bufffff);
            }
        } else AES.errorPrint("You`ve already decrypt the message!\n" +
                "Please, move to the next steps!");
    }

    @FXML
    void verMesA(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (aliceCns==1) {
            aliceCns++;
            time2 = getTimeLong();
            Long dt = time2 - time1;
            if (dt < 20000 && IDaA.getText().equals(IDb)) {
                alert.setTitle("");
                alert.setContentText("The timestamp is correct.\nIDs matched.");
                alert.showAndWait();
                IDaA.setText(IDa);
            } else if (dt>20000){AES.errorPrint("Timestamp is not valid!"); newScene();}
            else {
                AES.errorPrint("IDs are not matched!");
                newScene();
            }
        } else if(aliceCns==6) {
            if (randEncrNumBAlice.getText().equals(randNumBAlice.getText()) && encrKeyBob.getText().equals(encrKeyAlice.getText())) {
                alert.setTitle("");
                alert.setContentText("The secret key is distributed between Alice and Bob!\n");
                alert.showAndWait();
                newScene();
            } else if (!randEncrNumBAlice.getText().equals(randNumBAlice.getText())) {
                AES.errorPrint("Random numbers are not matched!");
                newScene();
            }
        }
        else if (aliceCns<1) AES.errorPrint("Decrypt the message!");
        else if (aliceCns>1&&aliceCns<6) AES.errorPrint("You`ve already verified the message!\n" +
                "Please, move to the next steps.");
    }

    @FXML
    void genNumA(ActionEvent event) {
        if (aliceCns==2) {
            aliceCns++;
            randAAlice.setText(getRandNum());
            timestampAAlice.setText(getTimeStr());
            time2 = getTimeLong();
        } else if (aliceCns==0) AES.errorPrint("Please, decrypt message first!");
        else if (aliceCns==1) AES.errorPrint("Please, at first, verify your message!");
        else if (aliceCns>2) AES.errorPrint("The number is generated.");
    }

    @FXML
    void encryptMesA(ActionEvent event) {
        if (aliceCns==3) {
            aliceCns++;
            AES.setSecretKey(encrKeyAlice.getText());
            IDaA.setText(AES.encrypt(IDaA.getText()));
            timestampAAlice.setText(AES.encrypt(timestampAAlice.getText()));
            randEncrAAlice.setText(AES.encrypt(randAAlice.getText()));
        } else if (aliceCns<3) AES.errorPrint("Please, make sure you follow the previous steps.");
        else AES.errorPrint("The message is encrypted.\nPlease, move to the next steps.");
    }

    @FXML
    void sendMesA(ActionEvent event) {
        if (aliceCns==4) {
            aliceCns++;
            IDaB.setText(IDaA.getText());
            IDbB.setText(IDbA.getText());
            timestampABob.setText(timestampAAlice.getText());
            timestampBBob.setText(timestampBAlice.getText());
            randABob.setText(randAAlice.getText());
            randEncrABob.setText(randEncrAAlice.getText());
            valBBob.setText(valBAlice.getText());
            keyBBob.setText(keyBAlice.getText());
            timestampAAlice.setText("");timestampBAlice.setText("");
            IDaA.setText("");IDbA.setText("");
            valAAlice.setText("");valBAlice.setText("");
            setDisableBBut(false);
            setDisableABut(true);
        } else if (aliceCns<4) AES.errorPrint("Please, make sure you follow the previous steps.");
        else if (aliceCns>4) AES.errorPrint("Please, move to the next steps.");
    }

    @FXML
    void decryptMesB(ActionEvent event) {
        if (bobCns<=1) {
            bobCns++;
            if (buf == 0) {
                AES.setSecretKey(encrKeyBob.getText());
                IDbB.setText(AES.decrypt(IDbB.getText()));
                timestampBBob.setText(AES.decrypt(timestampBBob.getText()));
                valBBob.setText(AES.decrypt(valBBob.getText()));
                keyBBob.setText(AES.decrypt(keyBBob.getText()));
                buf++;
            } else {
                AES.setSecretKey(encrKeyBob.getText());
                IDaB.setText(AES.decrypt(IDaB.getText()));
                timestampABob.setText(AES.decrypt(timestampABob.getText()));
                randEncrABob.setText(AES.decrypt(randEncrABob.getText()));
            }
        }
    }

    @FXML
    void verMesB(ActionEvent event) {
        if (bobCns==2) {
            bobCns++;
            Long dt = time2 - time1;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (dt < 20000 && IDaB.getText().equals(IDbB.getText()) && IDbB.getText().equals(IDa) && randEncrABob.getText().equals(randABob.getText())) {
                time1 = time2;
                time2 = getTimeLong();
                dt = time2 - time1;
                if (dt < 20000) {
                    alert.setTitle("");
                    alert.setContentText("The timestamp is correct.\nIDs and random numbers matched.");
                    alert.showAndWait();
                }
            } else if (!IDaB.getText().equals(IDbB.getText()) || !IDbB.getText().equals(IDa)) {
                AES.errorPrint("IDs are not matched!");
                newScene();
            } else if (dt > 20000) {
                AES.errorPrint("Timestamp is out of valid!");
                newScene();
            } else if (!randEncrABob.getText().equals(randABob.getText())) {
                AES.errorPrint("Random numbers are not matched!");
                newScene();
            }
        } else if (bobCns<2) AES.errorPrint("Please, decrypt all the messages!");
        else  AES.errorPrint("Please, move to the next steps.");
    }

    @FXML
    void genNumB(ActionEvent event) {
        if (bobCns==3) {
            bobCns++;
            randBBob.setText(getRandNum());
        } else if (bobCns<2) AES.errorPrint("Please, make sure you decrypt all the messages!");
        else if (bobCns==2) AES.errorPrint("Please, check that the IDs match\nand timestamps are correct.");
        else AES.errorPrint("The number is generated.");
    }

    @FXML
    void encryptMesB(ActionEvent event) {
        if (bobCns==4) {
            bobCns++;
            AES.setSecretKey(encrKeyBob.getText());
            randEncrBBob.setText(AES.encrypt(randBBob.getText()));
        } else if (bobCns<2) AES.errorPrint("Please, make sure you decrypt all the messages!");
        else if (bobCns==2) AES.errorPrint("Please, check that the IDs match\nand timestamps are correct.");
        else if (bobCns==3) AES.errorPrint("Please, generate the number!");
        else AES.errorPrint("The message is encrypted.");
    }

    @FXML
    void sendMesB(ActionEvent event) {
        if (bobCns==5) {
            randEncrNumBAlice.setText(randEncrBBob.getText());
            randNumBAlice.setText(randBBob.getText());
            setDisableABut(false);
            setDisableBBut(true);
        } else AES.errorPrint("Please, make sure you follow the previous steps.");
    }

    public static Scene getScene() {
        Parent root = loadFXML();
        assert root != null;
        return new Scene(root);
    }

    private static Parent loadFXML() {
        try {
            return FXMLLoader.load(MainController.class.getResource("secondWin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return new Pane();
        }

    }

    public String getTimeStr(){
        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("hh-mm-ss");
        String time = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return time;
    }
    public Long getTimeLong(){
        Long timeStamp = System.currentTimeMillis();
        return timeStamp;
    }

    public String getRandNum(){
        String randNum = "";
        Random r = new Random();
        for (int i = 0; i < 16; i++) {
            int rand = 1 + r.nextInt(9);
            randNum += rand;
        }
        return randNum;
    }

    public static void newScene(){
        Scene previousScene = Main.getMainStage().getScene();
        Scene scene = Controller.getScene();
        Main.getMainStage().setScene(scene);
        scene.setOnKeyPressed(event -> { if(event.getCode().equals(KeyCode.ESCAPE)) Main.getMainStage().setScene(previousScene); });
    }
    public void setDisableABut(boolean bool){
        genNumABut.setDisable(bool);
        encryptMesABut.setDisable(bool);
        decryptMesABut.setDisable(bool);
        verMesABut.setDisable(bool);
        sendMesABut.setDisable(bool);
    }
    public void setDisableBBut(boolean bool){
        genNumBBut.setDisable(bool);
        encryptMesBBut.setDisable(bool);
        decryptMesBBut.setDisable(bool);
        verMesBBut.setDisable(bool);
        sendMesBBut.setDisable(bool);
    }
    public void setDisableTBut(boolean bool){
        genMesTrentBut.setDisable(bool);
        encrMesTrentBut.setDisable(bool);
        sendMesTrentBut.setDisable(bool);
    }
}
