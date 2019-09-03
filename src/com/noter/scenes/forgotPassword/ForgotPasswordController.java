package com.noter.scenes.forgotPassword;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.noter.Noter;
import com.noter.scenes.Scenes;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
    @FXML
    private JFXButton sendEmailButton;

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
        Service<Boolean> splashService = new Service<Boolean>() {
            @Override
            public void start() {
                super.start();
                sendEmailButton.setDisable(false);
            }

            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() {
                        //TODO: Generate token and send email
                        return true;
                    }
                };
            }

            @Override
            protected void succeeded() {

            }
        };

        splashService.start();
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
