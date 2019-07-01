package com.noter.scenes.auth;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXPasswordField passwordFieldSecure;

    @FXML
    private JFXCheckBox checkboxPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Avoid SQL Injection
        RegexValidator regexValidator = new RegexValidator("Campo inválido.");
        regexValidator.setRegexPattern("/[\\t\\r\\n]|(--[^\\r\\n]*)|(\\/\\*[\\w\\W]*?(?=\\*)\\*\\/)/gi");

        /* Username field */
        usernameField.setValidators(new RequiredFieldValidator("Insira o usuário!"), regexValidator);
        usernameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    usernameField.resetValidation();
                }
            }
        });

        /* Password field's */
        passwordFieldSecure.textProperty().bindBidirectional(passwordField.textProperty());
        passwordFieldSecure.visibleProperty().bind(checkboxPassword.selectedProperty().not());
        passwordField.visibleProperty().bind(checkboxPassword.selectedProperty());

        passwordField.setValidators(new RequiredFieldValidator("Insira uma senha!"), regexValidator);
        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    passwordField.resetValidation();
                }
            }
        });

        passwordFieldSecure.setValidators(new RequiredFieldValidator("Insira uma senha!"), regexValidator);
        passwordFieldSecure.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    passwordFieldSecure.resetValidation();
                }
            }
        });
    }

    public void authenticate() {
        usernameField.validate();
        if (passwordField.isDisabled()) {
            passwordFieldSecure.validate();
        } else {
            passwordField.validate();
        }
    }
}
