package org.vaadin.example.frontend.filter;

import org.springframework.stereotype.Component;
import org.vaadin.example.backend.entity.Patient;
import org.vaadin.example.backend.service.PatientService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "filterpatient")
@UIScope
@Component
@PageTitle("Patients | Vaadin CRM")
public class listViewPatient extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	Grid<Patient> grid;
	PatientService ps;
	Patient p;

	TextField filter;

//	List<Column<Patient>> ls;
//	Binder<Patient> binder;

	TextField firstName, lastName;
	IntegerField phone;
	EmailField email;
	DatePicker time;
	Dialog dialog, d;
	Integer id = 0;

	public listViewPatient(PatientService ps) {
		this.ps = ps;

		Tabs tabs = getTabs();
		tabs.getStyle().set("width", "100%");
		add(tabs);

		filter = new TextField();
		filter.setPlaceholder("Filter...");
		filter.setClearButtonVisible(true);
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> updateList());

		Button addPatient = new Button("Add Patient", new Icon(VaadinIcon.PLUS_CIRCLE_O));
		addPatient.addClickListener(e -> {
			Dialog addDialogPatient = new Dialog();
			addDialogPatient.setMaxWidth("400px");
			setSizeFull();

			firstName = new TextField("First Name");
			firstName.setClearButtonVisible(true);
			firstName.getStyle().set("width", "100%");

			lastName = new TextField("Last Name");
			lastName.setClearButtonVisible(true);
			lastName.getStyle().set("width", "100%");

			phone = new IntegerField("Phone Number");
			phone.setClearButtonVisible(true);
			phone.getStyle().set("width", "100%");

			email = new EmailField("Email");
			email.setClearButtonVisible(true);
			email.getStyle().set("width", "100%");

			time = new DatePicker();
			time.setPlaceholder("Date");
			time.getStyle().set("width", "50%");

			Button save = new Button("Save", et -> {
				validateAndSavePatient();
				Notification.show("Patient saved", 4300, Position.BOTTOM_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				addDialogPatient.close();
			});
			save.getStyle().set("width", "100%");
			save.addClickShortcut(Key.ENTER);

			if (firstName.getValue() != "" && lastName.getValue() != "" && phone.getValue() != 0
					&& email.getValue() != "") {
				save.setEnabled(true);
			}

			Button cancel = new Button("Discard");
			cancel.getStyle().set("width", "100%");
			cancel.getStyle().set("color", "black");
			cancel.addClickShortcut(Key.END);
			cancel.addClickListener(click -> {
				firstName.clear();
				lastName.clear();
				phone.clear();
				email.clear();
				time.clear();
				addDialogPatient.close();
			});
			addDialogPatient.add(firstName, lastName, phone, email, time, save, cancel);
			addDialogPatient.open();
		});
		HorizontalLayout h = new HorizontalLayout(filter, addPatient);

		add(h);

		grid = new Grid<>(Patient.class, false); // false mnchene 3adam ltekrar
		setSizeFull();
		grid.addColumn(Patient::getFirstName).setHeader("First Name").setSortable(true);
		grid.addColumn(Patient::getLastName).setHeader("Last Name").setSortable(true);
		grid.addColumn(Patient::getPhoneNumber).setHeader("Phone Number").setSortable(true);
		grid.addColumn(Patient::getEmail).setHeader("Email").setSortable(true);
		grid.addColumn(Patient::getDate).setHeader("Date").setSortable(true);
		
		grid.addComponentColumn(item -> {
			Button edit = new Button(VaadinIcon.EDIT.create());
			edit.addClickListener(event -> {
				editPatient(item);
			});
			return edit;
		}).setHeader("update");

		grid.getColumns().forEach(e -> e.setAutoWidth(true));

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addAttachListener(e -> updateList());
		grid.recalculateColumnWidths();

		add(grid);
	}

	private Tabs getTabs() {
		Tabs tabs = new Tabs();
		Tab t = new Tab();
		Tab t1 = new Tab();
		Tab t2 = new Tab();
		Tab t3 = new Tab();
		t.add(createTab(new Button("Monitoring Plattform", VaadinIcon.WORKPLACE.create(),
						e -> getUI().get().navigate("filterpatient"))));
		t1.add(createTab(new Button("Patients", VaadinIcon.USERS.create(),
				e -> getUI().get().navigate("filterpatient"))));
		t2.add(createTab(new Button("Devices", VaadinIcon.BARCODE.create(),
				e -> getUI().get().navigate("filterdevice"))));
		t3.add(createTab(new Button("Log out", VaadinIcon.SIGN_OUT.create(), e -> getUI().get().navigate("login"))));
		t1.getStyle().set("left", "50%");
		t2.getStyle().set("left", "50%");
		t3.getStyle().set("left", "50%");
		tabs.add(t,t1,t2,t3);
		tabs.setSelectedTab(t1);
		return tabs;
	}

	private Tab createTab(Button button) {
		RouterLink link = new RouterLink();
		button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		
		link.add(new Span(button));
		link.setTabIndex(-1);
		return new Tab(link);
	}

	// to dialog addDialogPatient
	private void validateAndSavePatient() {
		p = new Patient(idGenerator(), firstName.getValue(), lastName.getValue(), phone.getValue(), email.getValue(), time.getValue());
		ps.save(p);
		updateList();
	}

	private Integer idGenerator() {
		return id;
	}
	// finish code
	
	private void editPatient(Patient p) {
		// TODO : 1 - create dialog inside of it the different fields , these fields
		// should be filled
		Dialog editDialogPatient = new Dialog();
		editDialogPatient.setMaxWidth("400px");
		setSizeFull();

		firstName = new TextField("First Name");
		firstName.setClearButtonVisible(true);
		firstName.getStyle().set("width", "100%");
		firstName.setValue(p.getFirstName());

		lastName = new TextField("lastName");
		lastName.setValue(p.getLastName());
		lastName.setClearButtonVisible(true);
		lastName.getStyle().set("width", "100%");

		phone = new IntegerField("phone");
		phone.setValue(p.getPhoneNumber());
		phone.setClearButtonVisible(true);
		phone.getStyle().set("width", "100%");

		email = new EmailField("email");
		email.setClearButtonVisible(true);
		email.setValue(p.getEmail());
		email.getStyle().set("width", "100%");

		time = new DatePicker();
		time.getStyle().set("width", "50%");
		time.setValue(p.getDate());
		

		Button save = new Button("Save");
		save.getStyle().set("width", "100%");

		Button delete = new Button("Delete", e -> {
			deletePatientById(p.getId());
			editDialogPatient.close();
			Notification.show("Row has been deleted", 6000, Position.BOTTOM_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
			;
		});
		delete.getStyle().set("width", "100%");
		delete.getStyle().set("color", "red");

		Button cancel = new Button("Cancel");
		cancel.getStyle().set("width", "100%");
		cancel.getStyle().set("color", "black");

		save.addClickListener(click -> {
			updatePatient(p.getId());
			editDialogPatient.close();
			Notification.show("Row has been updated", 4500, Position.BOTTOM_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		});

		cancel.addClickListener(click -> {
			firstName.clear();
			lastName.clear();
			phone.clear();
			email.clear();
			time.clear();
			editDialogPatient.close();
		});

		editDialogPatient.add(firstName, lastName, phone, email, time, save, delete, cancel);
		editDialogPatient.open();
	}

	// to Dialog editDialog
	private void deletePatientById(Integer i) {
		p = new Patient(i, firstName.getValue(), lastName.getValue(), phone.getValue(), email.getValue(), time.getValue());
		ps.deleteById(i);
		updateList();
	}

	public void updatePatient(Integer i) {
		Patient pati = ps.findById(i);
		pati.setFirstName(firstName.getValue());
		pati.setLastName(lastName.getValue());
		pati.setPhoneNumber(phone.getValue());
		pati.setEmail0(email.getValue());
		pati.setDate(time.getValue());
		ps.save(pati);
		updateList();
	}
	// finish code

	public void updateList() {
		grid.setItems(ps.findAll(filter.getValue()));
	}
}