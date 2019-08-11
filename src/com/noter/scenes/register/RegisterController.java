package com.noter.scenes.register;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.noter.Noter;
import com.noter.models.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputControl;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXPasswordField passwordFieldSecure;

    @FXML
    private JFXCheckBox checkboxPassword;

    @FXML
    private JFXTextField confirmPasswordField;

    @FXML
    private JFXPasswordField confirmPasswordFieldSecure;

    @FXML
    private JFXCheckBox checkboxConfirmPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Prevent SQL Injection
        RegexValidator regexSQLValidator = new RegexValidator("Não use espaços e aspas");
        regexSQLValidator.setRegexPattern("[\\\"\\'\\ \\\\]");

        //Check valid username
        RegexValidator regexUserValidator = new RegexValidator("Usuário inválido");
        regexUserValidator.setRegexPattern("^(?=.{4,36}$)(?![_.-])(?!.*[_.]{2})[a-zA-Z0-9._-]+(?<![_.])$");

        //Check valid email
        RegexValidator regexEmailValidator = new RegexValidator("Email inválido");
        regexEmailValidator .setRegexPattern("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");

        /* Username field */
        ValidatorBase[] usernameValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira o usuário!"), regexUserValidator};

        usernameField.setValidators(usernameValidators);
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameField.resetValidation();
            }
        });

        /* Email field */
        ValidatorBase[] emailValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira o email!"), regexEmailValidator};

        emailField.setValidators(emailValidators);
        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                emailField.resetValidation();
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

        /* Confirm password field's */
        ValidatorBase[] confirmPasswordValidators = new ValidatorBase[]{new RequiredFieldValidator("Insira uma senha!"),
                new ValidatorBase("As senhas não coincidem!") {
                    protected void eval() {
                        this.evalTextInputField();
                    }

                    private void evalTextInputField() {
                        TextInputControl textField = (TextInputControl) this.srcControl.get();
                        String text = textField.getText();

                        this.hasErrors.set(text.equals(passwordField.getText()));
                    }
                }
        };

        confirmPasswordFieldSecure.textProperty().bindBidirectional(confirmPasswordField.textProperty());
        confirmPasswordFieldSecure.visibleProperty().bind(checkboxConfirmPassword.selectedProperty().not());
        confirmPasswordField.visibleProperty().bind(checkboxConfirmPassword.selectedProperty());

        confirmPasswordField.setValidators(confirmPasswordValidators);
        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                confirmPasswordField.resetValidation();
            }
        });

        confirmPasswordFieldSecure.setValidators(confirmPasswordValidators);
        confirmPasswordFieldSecure.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                confirmPasswordFieldSecure.resetValidation();
            }
        });
    }

    @FXML
    public void handleGoBack() {
        Noter.getNoter().getSceneController().goBack();
    }

    @FXML
    public void handleCreateAccount() {
        if (usernameField.validate() && emailField.validate() && (passwordField.isVisible() ? passwordField.validate() : passwordFieldSecure.validate())) {
            String username = usernameField.getText();
            String email = emailField.getText().toLowerCase();
            String password = confirmPasswordField.isVisible() ? confirmPasswordField.getText() : confirmPasswordFieldSecure.getText();

            if (Noter.getNoter().getAccountManager().getAccountByUsername(username).isPresent()) {
                usernameField.getActiveValidator().setMessage("Nome de usuário em uso");
                return;
            }

            if (Noter.getNoter().getAccountManager().getAccountByEmail(email).isPresent()) {
                emailField.getActiveValidator().setMessage("Email em uso");
                return;
            }

            Account account = Noter.getNoter().getAuthManager().register(
                    username,
                    email,
                    password
            );
            Noter.getNoter().getSessionManager().startSessionWithNewAccount(account);
        }
    }
}
