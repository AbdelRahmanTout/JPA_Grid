package org.vaadin.example.frontend.login;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;

@Route(value = "login"/*,layout = MainView.class*/)
@RouteAlias(value = ""/*, layout = MainView.class*/)
@PWA(name = "Vaadin Application", shortName = "Vaadin App", description = "This is an example Vaadin application.", enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class Login extends VerticalLayout{

	TextField username;
	PasswordField password;
	Button buttonLogin;
	
	public Login() {
		H1 nurs = new H1("Nurses");
		H2 logH2 = new H2("Log in");
		setAlignItems(Alignment.CENTER);
		
		add(nurs, logH2);

		username = new TextField();
		username.setLabel("Username");
		username.setValue("AbdelRahman");
		username.setRequiredIndicatorVisible(true);
		username.getStyle().set("width", "100%");

		password = new PasswordField();
		password.setLabel("Password");
		password.setValue("Tout");
		password.setRequiredIndicatorVisible(true);
		password.getStyle().set("width", "100%");

		add(username, password);
		
		buttonLogin = new Button("Login", e -> {
			if (username.getValue().equals("AbdelRahman") && password.getValue().equals("Tout")) {
				getUI().get().navigate("filterpatient");
			} else {
				Notification.show("Wrong Credentials",3200,Position.BOTTOM_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			}
		});

		buttonLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLogin.getStyle().set("width", "100%");
		buttonLogin.addClickShortcut(Key.ENTER);

		add(buttonLogin);

		addClassName("centered-content");
	}
}
