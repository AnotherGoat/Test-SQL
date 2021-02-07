
// Importar los paquetes requeridos
import java.sql.*;
import java.util.Scanner;

public class SQL {

    /* Atributos */
    // URL del driver JDBC y de la base de datos
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/Comida";
    // Usuario y contraseña de la base de datos
    private final String USER = "root";
    private final String PASS = "1234";
    // Scanner
    private Scanner teclado;

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String sql = null;

    /* Constructores */
    public SQL() {
        this.teclado = new Scanner(System.in);

        try {
            conectar();
            menu();
            desconectar();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e);
        } catch (ClassNotFoundException f) {
            System.out.println("Clase no encontrada: " + f);
        }
    }

    /* Métodos */
    private void conectar() throws SQLException, ClassNotFoundException {
        // Carga el driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Abre una conexión
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    private void desconectar() throws SQLException {
        // Liberar memoria
        rs.close();
        stmt.close();
        conn.close();
    }

    private void menu() throws SQLException {

        int eleccion = -1;
        System.out.println("Bienvenido a la base de datos Comida");

        do {
            System.out.println("\nOpciones:");
            System.out.println("1 - Ver pizzas");
            System.out.println("2 - Insertar una pizza");
            System.out.println("3 - Editar una pizza");
            System.out.println("0 - Salir");

            System.out.print("\nEscoja una opción: ");

            eleccion = teclado.nextInt();

            switch (eleccion) {
                case 1:
                    select();
                    break;
                case 2:
                    insert();
                    break;
                case 3:
                    update();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (eleccion != 0);
    }

    private void select() throws SQLException {
        System.out.println("\nCreando declaracion...");
        stmt = conn.createStatement();
        String sql = "SELECT * FROM Pizza";
        rs = stmt.executeQuery(sql);

        System.out.println("\nLas pizzas disponibles son: ");

        // Extraer datos del resultado
        while (rs.next()) {
            // Recuperar por el nombre de la columna
            int codigoPizza = rs.getInt("codigoPizza");
            int valorPizza = rs.getInt("valorPizza");
            String nombrePizza = rs.getString("nombrePizza");

            // Mostar resultados
            System.out.print("Código: " + codigoPizza);
            System.out.print(", Valor: " + valorPizza);
            System.out.println(", Nombre: " + nombrePizza);

        }
    }

    private void insert() {

    }

    private void update() {

    }

/*
    public static void main(String[] args) {

        try{
            //Paso 2: Cargar driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            //Paso 3: Abrir una conexion
            System.out.println("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            mostrarResultados();
            actualizarPìzza();
            mostrarResultados();

            //Paso 6: Limpiar
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Errores de jdbc
            se.printStackTrace();
        }catch(Exception e){
            //Errores de Class.forName
            e.printStackTrace();
        }finally{
            //bloque usado para cerrar recursos
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nada que hacer
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Adios!");
    }



    public static void actualizarPìzza() throws SQLException{

        int codigoPizza;


        System.out.println("Ingrese el código de la pizza que desea modificar: ");
        codigoPizza = teclado.nextInt();
        teclado.next();

        System.out.print("Ingrese el nombre de la Pizza: ");
        String nombrePizza = teclado.nextLine();
        teclado.next();

        System.out.print("\nIngrese el precio de la Pizza: ");
        int precioPizza = teclado.nextInt();

        String sql = "update Pizza set nombrePizza=?, valorPizza=? where codigoPizza=?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString (1, nombrePizza); // es el dato para el atributo1 si es entero
        preparedStmt.setInt (2, precioPizza); //es el dato para el atributo2 si es string
        preparedStmt.setInt (3, codigoPizza);
        preparedStmt.executeUpdate();

        System.out.println("Se ha actualizado la pizza");

    }





    /*
    // Caso 1
    public static ResultSet select() throws SQLException {
        //Paso 4: Ejecutar una consulta
        System.out.println("Creando declaracion...");
        stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }*/
/*
    public static void mostrarResultados() throws SQLException{

        ResultSet rs = consultar("SELECT * FROM Pizza");



}*/

}