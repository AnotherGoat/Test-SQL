//Paso 1. Importar los paquetes requeridos
import java.sql.*;
import java.util.Scanner;

public class SQL {
    // url del driver JDBC y la base de datos
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Comida";
    // usuario y contraseña de la base de datos
    static final String USER = "root";
    static final String PASS = "";

    public static Scanner teclado = new Scanner(System.in);

    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) {

        try{
            //Paso 2: Cargar driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            //Paso 3: Abrir una conexion
            System.out.println("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            mostrarResultados();
            insertarNuevaPìzza();

            //Paso 6: Limpiar
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
    /*
    public static void menu() throws SQLException {

        int eleccion = -1;

        do {
            System.out.println("Bienvenido a la base de datos");

            System.out.println("Escoja una opción:");
            System.out.println("1 - Ver pizzas");
            System.out.println("2 - Eliminar una pizza");
            System.out.println("3 - Insertar una pizza");
            System.out.println("4 - Editar una pizza");
            System.out.println("0 - Salir");

            eleccion = teclado.nextInt();

            switch(eleccion) {
                case 1:
                    ResultSet rs = consultar("SELECT * FROM Pizza");
                    System.out.println(rs.toString());
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while(eleccion!=0);

    }
    */

    public static void insertarNuevaPìzza() throws SQLException{
        ResultSet rs = consultar("SELECT COUNT(*) FROM pizza");
        int codigoPizza=0;
        if (rs.next()) codigoPizza = rs.getInt(1) + 1;
        System.out.println("Ingrese una nueva Pizza, codigo de la nueva pizza: "+codigoPizza);
        System.out.print("Ingrese el nombre de la Pizza: ");
        String nombrePizza = teclado.nextLine();
        System.out.print("Ingrese el precio de la Pizza: ");
        int precioPizza = teclado.nextInt();
        String sqlNuevaPizza = "INSERT INTO Pizza VALUES ("+codigoPizza+","+precioPizza+",\'"+nombrePizza+"\');";
        insertar(sqlNuevaPizza);
        System.out.println("Se ha insertado la pizza");

    }
    public static ResultSet consultar(String sql) throws SQLException {
        //Paso 4: Ejecutar una consulta
        System.out.println("Creando declaracion...");
        stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public static void insertar(String sql)throws SQLException{
        stmt.executeUpdate(sql);
    }
    /*
    // Caso 1
    public static ResultSet select() throws SQLException {
        //Paso 4: Ejecutar una consulta
        System.out.println("Creando declaracion...");
        stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }*/

    public static void mostrarResultados() throws SQLException{

        ResultSet rs = consultar("SELECT * FROM Pizza");
        System.out.println("Las pizzas disponibles son: ");
        //Paso 5: Extraer datos del resultado
        while(rs.next()){
            //Recuperar por el nombre de la columna
            int codigoPizza = rs.getInt("codigoPizza");
            int valorPizza = rs.getInt("valorPizza");
            String nombrePizza = rs.getString("nombrePizza");
            //Mostar resultados
            System.out.print("Codigo: " + codigoPizza);
            System.out.print(", Valor: " + valorPizza);
            System.out.println(", Nombre: " + nombrePizza);

        }


}

}