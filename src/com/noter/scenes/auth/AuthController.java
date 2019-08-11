package com.noter.scenes.auth;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.noter.Main;
import com.noter.Noter;
import com.noter.models.Account;
import com.noter.scenes.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Optional;
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
        //Prevent SQL Injection
        RegexValidator regexSQLValidator = new RegexValidator("Não use espaços e aspas");
        regexSQLValidator.setRegexPattern("[\\\"\\'\\ \\\\]");

        //Check valid username
        RegexValidator regexUserValidator = new RegexValidator("Usuário inválido");
        regexUserValidator.setRegexPattern("^(?=.{4,36}$)(?![_.-])(?!.*[_.]{2})[a-zA-Z0-9._-]+(?<![_.])$");

        /* Username field */
        ValidatorBase[] usernameValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira o usuário!"), regexUserValidator};

        usernameField.setValidators(usernameValidators);
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameField.resetValidation();
            }
        });

        /* Password field's */
        ValidatorBase[] passwordValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira uma senha!")};

        passwordFieldSecure.textProperty().bindBidirectional(passwordField.textProperty());
        passwordFieldSecure.visibleProperty().bind(checkboxPassword.selectedProperty().not());
        passwordField.visibleProperty().bind(checkboxPassword.selectedProperty());

        passwordField.setValidators(passwordValidators);
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.resetValidation();
            }
        });

        passwordFieldSecure.setValidators(passwordValidators);
        passwordFieldSecure.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordFieldSecure.resetValidation();
            }
        });
    }

    @FXML
    public void handleClose() {
        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAuthenticate() {
        if (usernameField.validate() && (passwordField.isVisible() ? passwordField.validate() : passwordFieldSecure.validate())) {
            String username = usernameField.getText();
            String password = (passwordField.isVisible() ? passwordField.getText() : passwordFieldSecure.getText());

            Optional<Account> accountOptional = Noter.getNoter().getAuthManager().auth(username, password);

            if (accountOptional.isPresent()) {
                Noter.getNoter().getSessionManager().startSessionWithNewAccount(accountOptional.get());
            } else {
                System.out.println("errado");
            }
        }
    }

    @FXML
    public void handleForgotPassword() {
        Noter.getNoter().getSceneController().changeScene(Scenes.FORGOT_PASSWORD);
    }

    @FXML
    public void handleCreateAccount() {
        Noter.getNoter().getSceneController().changeScene(Scenes.REGISTER);
    }
}
