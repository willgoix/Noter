package com.noter.scenes.forgotPassword;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.noter.Noter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private JFXTextField usernameOrEmailField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Prevent SQL Injection
        RegexValidator regexSQLValidator = new RegexValidator("Não pode usar espaços e aspas");
        regexSQLValidator.setRegexPattern("[\\\"\\'\\ \\\\]");

        /* Username or email field */
        ValidatorBase[] usernameOrEmailValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira o usuário!")};

        usernameOrEmailField.setValidators(usernameOrEmailValidators);
        usernameOrEmailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameOrEmailField.resetValidation();
            }
        });
    }

    @FXML
    public void handleSendEmail() {

    }

    @FXML
    public void handleGoBack() {
        Noter.getNoter().getSceneController().goBack();
    }

    @FXML
    public void handleUnfocus() {
        usernameOrEmailField.setFocusTraversable(false);
    }
}
