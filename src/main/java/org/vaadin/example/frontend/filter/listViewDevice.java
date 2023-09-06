package org.vaadin.example.frontend.filter;

import org.springframework.stereotype.Component;
import org.vaadin.example.backend.entity.Device;
import org.vaadin.example.backend.service.DeviceService;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "filterdevice")
@UIScope
@Component
@PageTitle("Devices | Vaadin CRM")
public class listViewDevice extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	Grid<Device> grid;
	DeviceService ps;
	Device device;

	TextField filter;

	TextField serialNumber, deviceName, assosId;
	DatePicker time;
	Integer id = 0;

	public listViewDevice(DeviceService ps) {
		this.ps = ps;

		Tabs tabs = getTabs();
		tabs.getStyle().set("width", "100%");
		add(tabs);

		filter = new TextField();
		filter.setPlaceholder("Filter...");
		filter.setClearButtonVisible(true);
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> updateList());

		Button addDevice = new Button("Add Device", new Icon(VaadinIcon.PLUS_CIRCLE_O));
		addDevice.addClickListener(e -> {
			Dialog addDialogDevice = new Dialog();
			addDialogDevice.setMaxWidth("400px");
			setSizeFull();

			serialNumber = new TextField("Serial Number");
			serialNumber.setClearButtonVisible(true);
			serialNumber.getStyle().set("width", "100%");

			deviceName = new TextField("device Name");
			deviceName.setClearButtonVisible(true);
			deviceName.getStyle().set("width", "100%");

			assosId = new TextField("assos Id");
			assosId.setClearButtonVisible(true);
			assosId.getStyle().set("width", "100%");

			time = new DatePicker();
			time.setPlaceholder("Date");
			time.getStyle().set("width", "100%");

			Button save = new Button("Save", et -> {
				validateAndSaveDevice();
				Notification.show("Device saved", 4300, Position.BOTTOM_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				addDialogDevice.close();
			});
			save.getStyle().set("width", "100%");
			save.addClickShortcut(Key.ENTER);

			if (serialNumber.getValue() != "" && deviceName.getValue() != "" && assosId.getValue() != "") {
				save.setEnabled(true);
			}

			Button cancel = new Button("Discard");
			cancel.getStyle().set("width", "100%");
			cancel.getStyle().set("color", "black");
			cancel.addClickShortcut(Key.END);
			cancel.addClickListener(click -> {
				serialNumber.clear();
				deviceName.clear();
				assosId.clear();
				time.clear();
				addDialogDevice.close();
			});
			addDialogDevice.add(serialNumber, deviceName, assosId, time, save, cancel);
			addDialogDevice.open();
		});
		HorizontalLayout h = new HorizontalLayout(filter, addDevice);

		add(h);

		grid = new Grid<>(Device.class, false); // false mnchene 3adam ltekrar
		setSizeFull();
		grid.addColumn(Device::getSerialNumber).setHeader("Serial Number").setSortable(true);
		grid.addColumn(Device::getDeviceName).setHeader("device Name").setSortable(true);
		grid.addColumn(Device::getAssosId).setHeader("Assos Id").setSortable(true);
		grid.addColumn(Device::getDate).setHeader("Date").setSortable(true);
//		grid.addColumn(new LocalDateRenderer<>(listViewDevice::generateRandomDueDate, "yyyy-MM-dd")).setHeader("Date");

		grid.addComponentColumn(item -> {
			Button edit = new Button(new IronIcon("lumo","edit"));
			edit.addClickListener(event -> {
				editDevice(item);
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
		tabs.setSelectedTab(t2);
		return tabs;
	}

	private Tab createTab(Button button) {
		RouterLink link = new RouterLink();
		button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		link.add(new Span(button));
		link.setTabIndex(-1);
		return new Tab(link);
	}

	// to dialog addDialogDevice
	private void validateAndSaveDevice() {
		device = new Device(idGenerator(), serialNumber.getValue(), deviceName.getValue(), assosId.getValue(), time.getValue());
		ps.save(device);
		updateList();
	}

	private Integer idGenerator() {
		return id;
	}
	// finish code

	private void editDevice(Device devp) {
		// TODO : 1 - create dialog inside of it the different fields , these fields
		// should be filled
		Dialog editDialogDevice = new Dialog();
		editDialogDevice.setMaxWidth("400px");
		setSizeFull();

		serialNumber = new TextField("Serial Number");
		serialNumber.setClearButtonVisible(true);
		serialNumber.getStyle().set("width", "100%");
		serialNumber.setValue(devp.getSerialNumber());

		deviceName = new TextField("device Name");
		deviceName.setValue(devp.getDeviceName());
		deviceName.setClearButtonVisible(true);
		deviceName.getStyle().set("width", "100%");

		assosId = new TextField("assosId");
		assosId.setValue(devp.getAssosId());
		assosId.setClearButtonVisible(true);
		assosId.getStyle().set("width", "100%");

		time = new DatePicker();
		time.getStyle().set("width", "100%");
		time.setValue(devp.getDate());
		Button save = new Button("Save");
		save.getStyle().set("width", "100%");

		Button delete = new Button("Delete", e -> {
			deleteDeviceById(devp.getId());
			editDialogDevice.close();
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
			updateDevice(devp.getId());
			editDialogDevice.close();
			Notification.show("Row has been updated", 4500, Position.BOTTOM_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		});

		cancel.addClickListener(click -> {
			serialNumber.clear();
			deviceName.clear();
			assosId.clear();
			time.clear();
			editDialogDevice.close();
		});

		editDialogDevice.add(serialNumber, deviceName, assosId, time, save, delete, cancel);
		editDialogDevice.open();
	}

	// to Dialog editDialog
	private void deleteDeviceById(Integer i) {
		device = new Device(i, serialNumber.getValue(), deviceName.getValue(), assosId.getValue(), time.getValue());
		ps.deleteById(i);
		updateList();
	}

	public void updateDevice(Integer i) {
		Device devpati = ps.findById(i);
		devpati.setSerialNumber(serialNumber.getValue());
		devpati.setDeviceName(deviceName.getValue());
		devpati.setAssosId(assosId.getValue());
		devpati.setDate(time.getValue());
		ps.save(devpati);
		updateList();
	}
	// finish code

	public void updateList() {
		grid.setItems(ps.findAll(filter.getValue()));
	}
}