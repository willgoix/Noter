package com.noter.scenes.resetPassword;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
public class ResetPasswordController implements Initializable {

    @FXML
    private JFXTextField token;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXButton sendEmailButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Prevent SQL Injection
        RegexValidator regexSQLValidator = new RegexValidator("Não pode usar espaços e aspas");
        regexSQLValidator.setRegexPattern("[\\\"\\'\\ \\\\]");

        /* Token field */
        ValidatorBase[] tokenValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira o token!")};

        token.setValidators(tokenValidators);
        token.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                token.resetValidation();
            }
        });

        /* New Password field */
        ValidatorBase[] newPasswordValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira a nova senha!")};

        newPassword.setValidators(newPasswordValidators);
        newPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                newPassword.resetValidation();
            }
        });
    }

    @FXML
    public void changePassword() {
        String token = Noter.getNoter().getTokenManager().generateToken(Noter.getNoter().getSessionManager().getLoggedAccount());
    }

    @FXML
    public void handleGoBack() {
        Noter.getNoter().getSceneController().goBack();
    }

    @FXML
    public void handleUnfocus() {
        token.setFocusTraversable(false);
    }
}
