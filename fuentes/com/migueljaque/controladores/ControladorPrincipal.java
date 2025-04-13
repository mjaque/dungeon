package com.migueljaque.controladores;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.fxml.FXML;

import com.migueljaque.modelos.Modelo;

public class ControladorPrincipal extends Controlador{

	private Stage ventana;
	private Scene vista1;
	private Scene vista2;

	@FXML
	private GridPane grid;

	private Integer filas = 10;
	private Integer cols = 10;
	private StackPane[][] stackPanes;
	private Image imgEscenario;
	private ImageView ivPersonaje;

	private Modelo modelo;

	private static final Integer LADO = 128;

	public ControladorPrincipal(Stage ventana){
		super();
		
		stackPanes = new StackPane[filas][cols];
		imgEscenario = new Image(this.getClass().getResourceAsStream("/2dpixx_dungeon.png"));
		this.modelo = new Modelo();

		this.ventana = ventana;

		//Cargamos las vistas del Controlador
		vista1 = cargarVista(this, "vista1");
		vista2 = cargarVista(this, "vista2");

		//Componemos la vista
		HBox raizVista1 = (HBox) vista1.getRoot();
		VBox raizVista2 = (VBox) vista2.getRoot();
		raizVista1.getChildren().add(raizVista2);

		crearGrid(filas, cols);

		//Creamos los viewports del escenario
		Rectangle2D vpSuelo = new Rectangle2D(5*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpEsquinaSuperiorIzquierda = new Rectangle2D(0*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpEsquinaSuperiorDerecha = new Rectangle2D(2*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpEsquinaInferiorIzquierda = new Rectangle2D(0*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpEsquinaInferiorDerecha = new Rectangle2D(2*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpBordeSuperior = new Rectangle2D(1*LADO, 5*LADO, LADO, LADO);
		Rectangle2D vpBordeInferior = new Rectangle2D(1*LADO, 3*LADO, LADO, LADO);
		Rectangle2D vpBordeLateralIzquierdo = new Rectangle2D(2*LADO, 4*LADO, LADO, LADO);
		Rectangle2D vpBordeLateralDerecho = new Rectangle2D(0*LADO, 4*LADO, LADO, LADO);
		Rectangle2D vpPared = new Rectangle2D(4*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpColumna = new Rectangle2D(2*LADO, 2*LADO, LADO, LADO);
		Rectangle2D vpPuerta = new Rectangle2D(1*LADO, 0*LADO, LADO, LADO);
	
		//Creación del Personaje
		Image imgPersonaje = new Image(this.getClass().getResourceAsStream("/2dpixx_-_free_assets_-_warrior_character_size_128x160_isometric_-_idle.png"));
		ivPersonaje = new ImageView(imgPersonaje);
		ivPersonaje.setFitHeight(60);
		ivPersonaje.setPreserveRatio(true);
		Rectangle2D vpPersonaje = new Rectangle2D(3*128, 1*160, 128, 160);
		ivPersonaje.setViewport(vpPersonaje);

		//Ponemos el suelo
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < cols; j++)
				asignarCelda(vpSuelo, i, j);

		//Ponemos las esquinas:
		asignarCelda(vpEsquinaSuperiorIzquierda, 0, 0);
		asignarCelda(vpEsquinaSuperiorDerecha, 0, cols - 1);
		asignarCelda(vpEsquinaInferiorIzquierda, filas - 1, 0);
		asignarCelda(vpEsquinaInferiorDerecha, filas - 1, cols - 1);

		//Ponemos los bordes superior e inferior
		for (int i = 0; i < cols - 2; i++){
			asignarCelda(vpBordeSuperior, 0, i + 1);
			asignarCelda(vpBordeInferior, filas - 1, i + 1);
		}

		//Ponemos los bordes laterales
		for (int i = 0; i < filas - 2; i++){
			asignarCelda(vpBordeLateralIzquierdo, i + 1, 0);
			asignarCelda(vpBordeLateralDerecho, i + 1, cols - 1);
		}

		//Ponemos la pared superior
		for (int i = 0; i < cols - 2; i++){
			asignarCelda(vpPared, 1, i + 1);
		}
		
		//Ponemos los elementos
		sobrePoner(vpColumna, 3, 4);

		moverPersonaje(2,1);
		moverPersonaje(5,6);

		//capturarEventos()

		//Iniciamos la vista principal       
        ventana.setScene(vista1); //Cargamos la vista principal
		ventana.setTitle("Mazmorra");
        ventana.show();
	}

	private void moverPersonaje(Integer fila, Integer col){
		stackPanes[fila][col].getChildren().add(ivPersonaje);
	}

	private void asignarCelda(Rectangle2D viewport, Integer fila, Integer col){
		ImageView imageView = (ImageView) stackPanes[fila][col].getChildren().get(0);
		imageView.setViewport(viewport);
	}

	private void sobrePoner(Rectangle2D viewport, Integer fila, Integer col){
		ImageView imageView = new ImageView(imgEscenario);
		imageView.setFitWidth(60);
		imageView.setFitHeight(60);
		imageView.setPreserveRatio(true);
		imageView.setViewport(viewport);
		stackPanes[fila][col].getChildren().add(imageView);
	}

	private void crearGrid(Integer filas, Integer cols){
		for (int i = 0; i < filas; i++)
			grid.getRowConstraints().add(new RowConstraints());
		for (int i = 0; i < cols; i++)
			grid.getColumnConstraints().add(new ColumnConstraints());

		for (int i = 0; i < filas; i++)
			for (int j = 0; j < cols; j++){
				StackPane stackPane = new StackPane();
				//stackPane.setStyle("-fx-border-color: pink; -fx-border-width: 1;");				
				stackPanes[i][j] = stackPane;
				ImageView imageView = new ImageView(imgEscenario);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				imageView.setPreserveRatio(true);
				stackPane.getChildren().add(imageView);
				grid.add(stackPane, j, i);
			}
	}

	private void capturarEventos(){
		/*
		//Utilizamos función lambda para implementar anónimamente un EventHandler
		boton1.setOnAction(evento -> {
				System.out.println("Se ha pulsado el botón 1.");
		});
		*/
	}
}
