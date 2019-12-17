package dad.javafx.datechooser;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox implements Initializable {
	
	//FXML
	
	@FXML
    private ComboBox<Integer> day;

    @FXML
    private ComboBox<String> month;

    @FXML
    private ComboBox<String> year;
    
    //model
    
    ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    boolean bisiesto = false;
    
    ArrayList<String> yearsList = new ArrayList<>();
    
    ListProperty<Integer> days = new SimpleListProperty<>(FXCollections.observableArrayList());
    ListProperty<String> months = new SimpleListProperty<>(FXCollections.observableArrayList());
    ListProperty<String> years = new SimpleListProperty<>(FXCollections.observableArrayList());
    
    public DateChooser() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this); 
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Creamos ArrayList para que sea más cómoda la construcción
		//del contenido de los combobox
		
		
		//Rellenamos las ArrayList
		for(int i = LocalDate.now().getYear(); i >= 1900; i--) {
			yearsList.add(Integer.toString(i));
		}
		
		//Añadimos los años y los meses, además de los posibles días
		years.addAll(yearsList);
		months.addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre");
		
		
		//Establecemos los valores fijos de los combobox
		year.setItems(years);
		year.getSelectionModel().selectFirst();
		month.setItems(months);
		month.getSelectionModel().selectFirst();
		day.getSelectionModel().selectFirst();
		MonthChange();
		
		month.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				MonthChange();
				
			}
			
		});
		year.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				YearChange(oldValue, newValue);
				
			}
			
		});

	}

	private void YearChange(String old, String newV) {
		try {
			Integer.parseInt(newV);
		}catch(NumberFormatException e) {
			year.setValue(old);
		}
		if(Year.of(Integer.parseInt(year.getValue())).isLeap()) {
			bisiesto = true;
		}else {
			bisiesto = false;
		}
		MonthChange();
	}

	private void MonthChange() {
		
		DaysSet(Month.of(month.getSelectionModel().getSelectedIndex()+1).length(bisiesto));
	}
	
	public void SaveDate() {
		try {
			dateProperty.set(LocalDate.of(Integer.parseInt(year.getValue()), 
					Month.of(month.getSelectionModel().getSelectedIndex()+1),
					day.getSelectionModel().getSelectedItem()));
		} catch (Exception e) {
			
		}
	}
	
	private void DaysSet(int max) {
		
		day.getItems().clear();
		days.clear();
		
		for(int i = max; i > 0; i--) {
			days.add(i);
		}
		
		Collections.sort(days);
		
		day.setItems(days);
		day.getSelectionModel().selectFirst();

	}

	public final ObjectProperty<LocalDate> datePropertyProperty() {
		return this.dateProperty;
	}
	

	public final LocalDate getDateProperty() {
		return this.datePropertyProperty().get();
	}
	

	public final void setDateProperty(final LocalDate dateProperty) {
		this.datePropertyProperty().set(dateProperty);
	}	

}
