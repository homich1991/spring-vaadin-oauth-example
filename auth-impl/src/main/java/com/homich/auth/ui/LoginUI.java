package com.homich.auth.ui;

import com.homich.auth.entities.User;
import com.homich.auth.repositories.UserRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

@SpringUI(path = "/login")
@Title("Auth app")
@Theme("valo")
public class LoginUI extends UI {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final Environment environment;

    private Map<String, String> params = null;

    @Autowired
    public LoginUI(AuthenticationManager authenticationManager, UserRepository userRepository, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.environment = environment;
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setSizeFull();

        VerticalLayout uiLayout = new VerticalLayout();
        setContent(uiLayout);

        uiLayout.setSizeFull();
        Component loginForm = buildLoginForm();
        uiLayout.addComponent(loginForm);
        uiLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        if (vaadinRequest.getParameter("redirect_uri") != null) {
            params = new HashMap<>();
            params.put("redirect_uri", vaadinRequest.getParameter("redirect_uri"));
            params.put("client_id", vaadinRequest.getParameter("client_id"));
            params.put("response_type", vaadinRequest.getParameter("response_type"));
            params.put("state", vaadinRequest.getParameter("state"));
        }

    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());

        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout signInFields = new HorizontalLayout();
        signInFields.setSpacing(true);
        signInFields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.setRequired(true);
        username.setRequiredError("Username is required");
        username.setValidationVisible(false);
        username.addValueChangeListener(event -> username.setValidationVisible(true));

        final PasswordField password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setRequired(true);
        password.setRequiredError("Password is required");
        password.setValidationVisible(false);
        password.addValueChangeListener(event -> password.setValidationVisible(true));

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signin.focus();

        signInFields.addComponents(username, password, signin);
        signInFields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final Label errorLabel = new Label();
        errorLabel.setStyleName(ValoTheme.LABEL_FAILURE);
        errorLabel.setVisible(false);

        VerticalLayout fields = new VerticalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");
        fields.addComponents(signInFields, errorLabel);

        signin.addClickListener((Button.ClickListener) event -> {
            username.setValidationVisible(true);
            password.setValidationVisible(true);
            if (!(username.isValid() && password.isValid())) {
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.getValue(), password.getValue());
            try {
                Authentication token = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(token);

                VaadinSession.getCurrent().setAttribute(User.class, userRepository.findByName(username.getValue(), User.class));
                VaadinSession.getCurrent().getSession().setMaxInactiveInterval(Integer.parseInt(environment.getProperty("http.session.max-inactive-interval-minutes", "20")) * 60);

                if (params != null) {
                    getPage().setLocation(
                            String.format("/oauth/authorize?client_id=%s"
                                            + "&redirect_uri=%s"
                                            + "&response_type=%s"
                                            + "&state=%s",
                                    params.get("client_id"),
                                    params.get("redirect_uri"),
                                    params.get("response_type"),
                                    params.get("state")
                            ));
                } else {
                    String fragment = getPage().getLocation().getFragment();
                    getPage().setLocation("/" + ((fragment == null) ? "" : "#" + fragment));
                }
            } catch (BadCredentialsException e) {
                errorLabel.setValue("Incorrect username or password");
                errorLabel.setVisible(true);
            } catch (Exception e) {
                errorLabel.setValue("Some shit happens ¯\\_(ツ)_/¯");
                errorLabel.setVisible(true);
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome to");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Auth app");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }
}
