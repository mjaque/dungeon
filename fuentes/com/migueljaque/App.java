package com.migueljaque;

import javafx.application.Application;
import javafx.stage.Stage;
import com.migueljaque.controladores.*;
import java.io.IOException;

public class App extends Application{
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		new ControladorPrincipal(stage);
	}
}
