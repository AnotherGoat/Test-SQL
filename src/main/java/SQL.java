//Paso 1. Importar los paquetes requeridos
import java.sql.*;
import java.util.Scanner;

public class SQL {
    // url del driver JDBC y la base de datos
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Comida";
    // usuario y contraseña de la base de datos
    static final String USER = "root";
    static final String PASS = "1234";

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

            menu();

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

    public static ResultSet consultar(String sql) throws SQLException {
        //Paso 4: Ejecutar una consulta
        System.out.println("Creando declaracion...");
        stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
    /*
    // Caso 1
    public static ResultSet select() throws SQLException {
        //Paso 4: Ejecutar una consulta
        System.out.println("Creando declaracion...");
        stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }*/

    public static void mostrarResultados(ResultSet rs) {

    }

}