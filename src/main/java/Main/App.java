package Main;


import dad.javafx.datechooser.DateChooser;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	DateChooser dateChooser = new DateChooser();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Button inicialize = new Button("Inicializar");
		Button consult = new Button("Consultar");
		
		HBox buttons = new HBox(inicialize, consult);
		buttons.setAlignment(Pos.CENTER);
		
		inicialize.setOnAction(evt -> Save());
		consult.setOnAction(evt -> Consult());
		VBox root = new VBox(dateChooser, buttons);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Probando JDate");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void Consult() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("La fecha seleccionada es: "
		+dateChooser.getDateProperty().getDayOfMonth()+
		"/"+dateChooser.getDateProperty().getMonthValue()+
		"/"+dateChooser.getDateProperty().getYear());
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);

	}
	
	private void Save() {

		dateChooser.SaveDate();

	}

}
