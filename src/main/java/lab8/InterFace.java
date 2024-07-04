package lab8;

import Dominio.Consultor;
import Dominio.Departamento;
import Dominio.Empleado;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;

public class InterFace {

    private VBox contenedorPrincipal;
    private HBox contenedorAccion;
    private ComboBox<String> comboBoxAccion;
    
    public InterFace(int espacio) {
        //Inicialización de parámetros de los contenedores padres
        contenedorPrincipal = new VBox(espacio);
        contenedorAccion = new HBox(espacio);
        contenedorAccion.setAlignment(Pos.CENTER);
        //-------------------------------------Para poder escoger acciones --------------------------------------------
        comboBoxAccion = new ComboBox<>();
        comboBoxAccion.getItems().addAll("Insertar", "Modificar", "Consultar");
        comboBoxAccion.setOnAction(event -> escogerAccion(comboBoxAccion));
        //-----------------------------------------------------------------------------------------------------------
        contenedorAccion.getChildren().addAll(new Label("Acción:"), comboBoxAccion);
        contenedorPrincipal.getChildren().add(contenedorAccion);
    }
    public Scene getScene() {
        Scene scene = new Scene(contenedorPrincipal, 500, 550);
        return scene;
    }
    public void escogerAccion(ComboBox<String> accionComboBox) {
        // Limpiar el VBox antes de agregar nuevo contenido
        contenedorPrincipal.getChildren().clear();
        contenedorPrincipal.getChildren().add(contenedorAccion); //Mantiene la selección de acción en la parte superior
        String accion = accionComboBox.getValue();
        if (accion != null) {
            switch (accion) {
                case "Insertar" -> funcionInsertarRegistro();
                case "Modificar" -> funcionModificarRegistro();
                case "Consultar" -> funcionConsultarRegistro();
            }
        }
    }
    public void funcionInsertarRegistro(){
        GridPane gridPane = crearGridPane();
        agregarCamposBasicos(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        contenedorPrincipal.getChildren().add(gridPane);
    }
    public void funcionModificarRegistro(){
        GridPane gridPane = crearGridPane();
        agregarCamposBasicos(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        contenedorPrincipal.getChildren().add(gridPane);
    }
    public void funcionConsultarRegistro(){

        TextArea textAreaDepartamento = crearTextArea(400,300,20);
        TextArea textAreaGeneral = crearTextArea(400,300,20);
        ComboBox<String> departamentoComboBox = crearComboBoxDepartamento("Ventas", "Marketing", "IT", "Finanzas");


        contenedorAccion.getChildren().add(departamentoComboBox);
        contenedorPrincipal.getChildren().addAll(textAreaDepartamento,textAreaGeneral);

        departamentoComboBox.setOnAction(actionEvent -> {
            String departamentoSeleccionado = departamentoComboBox.getValue();
            String consulta = obtenerConsultaPorDepartamento(departamentoSeleccionado);
            textAreaDepartamento.setText(consulta);
        });

        textAreaGeneral.setText(obtenerConsulta());


    }
    public void realizarInsercion(ComboBox<String> comboBoxTipoTrabajador, TextField nombreField, TextField direccionField, TextField telefonoField, TextField cedulaField, ComboBox<String> departamentoComboBox, TextField salarioField, TextField deduccionesField,TextField horasTrabajadasField,TextField tarifaHorariaField,TextField textMensaje ){

        if (camposLlenos(comboBoxTipoTrabajador,nombreField,direccionField,telefonoField,cedulaField,departamentoComboBox,salarioField,deduccionesField,horasTrabajadasField,tarifaHorariaField)) {
            if (comboBoxTipoTrabajador.getValue().equals("Empleado")){
                Empleado empleado = new Empleado(nombreField.getText(), direccionField.getText(), telefonoField.getText(), cedulaField.getText(), new Departamento(departamentoComboBox.getValue()), Double.parseDouble(salarioField.getText()), Double.parseDouble(deduccionesField.getText()));
                empleado.guardarTrabajadoresEnArchivo(empleado.toString());
                resetearCamposTexto(nombreField,direccionField,telefonoField,cedulaField,deduccionesField,salarioField,horasTrabajadasField,tarifaHorariaField,comboBoxTipoTrabajador);

            } else {
                Consultor consultor = new Consultor(nombreField.getText(), direccionField.getText(), telefonoField.getText(), cedulaField.getText(), new Departamento(departamentoComboBox.getValue()), Integer.parseInt(horasTrabajadasField.getText()), Double.parseDouble(tarifaHorariaField.getText()));
                consultor.guardarTrabajadoresEnArchivo(consultor.toString());
                resetearCamposTexto(nombreField,direccionField,telefonoField,cedulaField,deduccionesField,salarioField,horasTrabajadasField,tarifaHorariaField,comboBoxTipoTrabajador);
            }

        }else{
            textMensaje.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(5),
                    ae -> textMensaje.setVisible(false)
            ));
            timeline.play(); // Iniciar el Timeline

        }




    }
    public GridPane crearGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }
    public void agregarFila(String lable, Object object, GridPane gridPane, int fila){
        gridPane.addRow(fila,new Label(lable), (Node) object);
    }
    public TextField crearTextFile(boolean esVisible,boolean editable,String etiqueta){
        TextField textField = new TextField(etiqueta);
        textField.setEditable(editable);
        textField.setVisible(esVisible);
        textField.setFont(Font.font("Arial"));
        return textField;
    }
    public void agregarCamposBasicos(GridPane gridPane){

        ComboBox<String> comboBoxTipoTrabajador = crearComboBoxTipoTrabajo("Empleado","Consultor");
        agregarFila("Tipo de Trabajador: ",comboBoxTipoTrabajador,gridPane,1);

        TextField nombreField = crearTextFile(true,true,"");
        agregarFila("Nombre :", nombreField, gridPane,2);

        TextField direccionField = crearTextFile(true,true,"");
        agregarFila("Dirección:", direccionField, gridPane,3);

        TextField telefonoField = crearTextFile(true,true,"");
        agregarFila("Teléfono:", telefonoField, gridPane,4);

        TextField cedulaField = crearTextFile(true,true,"");
        agregarFila("Cédula:", cedulaField, gridPane,5);

        ComboBox<String> departamentoComboBox = crearComboBoxDepartamento("Ventas", "Marketing", "IT", "Finanzas");
        agregarFila("Departamento:", departamentoComboBox, gridPane,6);

        TextField salarioField = crearTextFile(false,true,"");
        agregarFila("Salario:", salarioField, gridPane,7);

        TextField deduccionesField = crearTextFile(false,true,"");
        agregarFila("Deducciones:", deduccionesField, gridPane,8);

        TextField horasTrabajadasField = crearTextFile(false,true,"");
        agregarFila("Horas Trabajadas:", horasTrabajadasField, gridPane,9);

        TextField tarifaHorariaField = crearTextFile(false,true,"");
        agregarFila("Tarifa Horaria:", tarifaHorariaField, gridPane,10);





        TextField textMensaje = crearTextFile(false,false,"Faltan Datos");
        gridPane.add(textMensaje,13,2);

        comboBoxTipoTrabajador.setOnAction(actionEvent -> seleccionarTipoEmpleado(comboBoxTipoTrabajador,salarioField,deduccionesField,horasTrabajadasField,tarifaHorariaField));

        if (comboBoxAccion.getValue().equals("Insertar")){
            Button insertarButton = new Button("Insertar");
            agregarFila("",insertarButton,gridPane,11);
            insertarButton.setOnAction(actionEvent -> realizarInsercion(comboBoxTipoTrabajador,nombreField,direccionField,telefonoField,cedulaField,departamentoComboBox,salarioField,deduccionesField,horasTrabajadasField,tarifaHorariaField,textMensaje));
        }
        if (comboBoxAccion.getValue().equals("Modificar")){
            Button modificarButton = new Button("Modificar");
            agregarFila("",modificarButton,gridPane,12);
            ComboBox<String> idTrabajador = crearComboBoxIdTrabajador();
            agregarFila("Id Trabajadores",idTrabajador,gridPane,0);


            modificarButton.setOnAction(actionEvent -> manejarEventoModificar(comboBoxTipoTrabajador,nombreField,direccionField,telefonoField,cedulaField,departamentoComboBox,salarioField,deduccionesField,horasTrabajadasField,tarifaHorariaField,textMensaje));


        }
    }
    public void seleccionarTipoEmpleado(ComboBox<String> tipoTrabajadorComboBox, TextField salarioField, TextField deduccionesField, TextField horasTrabajadasField, TextField tarifaHorariaField){
        if (tipoTrabajadorComboBox.getValue().equals("Empleado")){

            salarioField.setVisible(true);
            deduccionesField.setVisible(true);
            horasTrabajadasField.setVisible(false);
            tarifaHorariaField.setVisible(false);


        } else if (tipoTrabajadorComboBox.getValue().equals("Consultor")) {
            horasTrabajadasField.setVisible(true);
            tarifaHorariaField.setVisible(true);
            salarioField.setVisible(false);
            deduccionesField.setVisible(false);
        }


    }
    public ComboBox<String> crearComboBoxTipoTrabajo(String uno,String dos){
        ComboBox<String> tipoTrabajadorComboBox = new ComboBox<>();
        tipoTrabajadorComboBox.getItems().addAll(uno,dos);

        return tipoTrabajadorComboBox;
    }
    public ComboBox<String> crearComboBoxDepartamento(String uno,String dos,String tres,String cuatro){
        ComboBox<String> tipoTrabajadorComboBox = new ComboBox<>();
        tipoTrabajadorComboBox.getItems().addAll(uno,dos,tres,cuatro);

        return tipoTrabajadorComboBox;
    }
    public ComboBox<String> crearComboBoxIdTrabajador(){
        ComboBox<String> trabajadorComboBox = new ComboBox<>();
        String [] id = leerIds();
        for (int i = 0; i< id.length; i++){
            trabajadorComboBox.getItems().add(id[i]);
        }
        return trabajadorComboBox;
    }

    public String obtenerConsulta(){
        String consulta = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/empleado.txt"))) {
            String line;
            consulta = "";
            while ((line = reader.readLine()) != null) {
                consulta += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consulta;

    }
    public String obtenerConsultaPorDepartamento(String departamentoSeleccionado){
        StringBuilder consulta = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/empleado.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en partes usando el delimitador ";"
                String[] partes = line.split(";");
                if (partes.length > 5 && partes[5].equals(departamentoSeleccionado)) {
                    consulta.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consulta.toString();
    }
    public String[] leerIds()  {
        ArrayList<String> consecutivos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/empleado.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf(';'); // Buscar el primer punto y coma en la línea
                if (index != -1) { // Si se encuentra el punto y coma
                    String dataBeforeSemicolon = line.substring(0, index); // Obtener la parte antes del punto y coma
                    consecutivos.add(dataBeforeSemicolon);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consecutivos.toArray(new String[0]);

    }
    public void resetearCamposTexto(TextField nombreField, TextField direccionField, TextField telefonoField, TextField cedulaField, TextField deduccionesField, TextField salarioField, TextField horasTrabajadasField, TextField tarifaHorariaField, ComboBox<String> tipoTrabajadorComboBox){

        nombreField.setText("");
        direccionField.setText("");
        telefonoField.setText("");
        cedulaField.setText("");

        if (tipoTrabajadorComboBox.getValue().equals("Empleado")){
            deduccionesField.setText("");
            salarioField.setText("");
        }
        else {
            horasTrabajadasField.setText("");
            tarifaHorariaField.setText("");

        }

    }

    public TextArea crearTextArea(int x, int y, int padding ){
        TextArea textArea = new TextArea();
        textArea.setPrefSize(x, y);
        textArea.setPadding(new Insets(padding));
        textArea.setFont(Font.font("Arial",11.5));
        textArea.setEditable(false);
        return textArea;
    }

    public boolean camposLlenos(ComboBox<String> comboBoxTipoTrabajador, TextField nombreField, TextField direccionField, TextField telefonoField, TextField cedulaField, ComboBox<String> departamentoComboBox, TextField salarioField, TextField deduccionesField,TextField horasTrabajadasField,TextField tarifaHorariaField ){
        boolean todosLosCamposLLenos = false;

        if (!(comboBoxTipoTrabajador.getValue() == null) && !nombreField.getText().isEmpty()
                && !direccionField.getText().isEmpty() && !telefonoField.getText().isEmpty() &&
                !cedulaField.getText().isEmpty() && !(departamentoComboBox.getValue() == null))
        {
            if (comboBoxTipoTrabajador.getValue().equals("Empleado") && !deduccionesField.getText().isEmpty() && !salarioField.getText().isEmpty()) {
                todosLosCamposLLenos = true;


            } else if (!horasTrabajadasField.getText().isEmpty() && !tarifaHorariaField.getText().isEmpty()){
                todosLosCamposLLenos = true;
            }


        }
        return todosLosCamposLLenos;

    }
}

