
// Importar los paquetes requeridos
import java.sql.*;
import java.util.Scanner;

public class SQL {

    /* Constantes */
    // URL del driver JDBC y de la base de datos
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/Comida";

    // Usuario y contraseña de la base de datos
    private final String USER = "root";
    private final String PASS = "1234";

    /* Atributos */
    // Objetos para hacer la conexión
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private String sql;

    // Datos de la pizza
    private int codigoPizza;
    private String nombrePizza;
    private int valorPizza;

    // Scanner
    private Scanner teclado;

    /* Constructores */
    public SQL() {
        this.teclado = new Scanner(System.in);

        try {
            conectar();
            menu();
            limpiar();
        } catch (SQLException e) {
            // Error de SQL
            System.out.println("Error SQL:");
            e.printStackTrace();
        } catch (ClassNotFoundException f) {
            // Error de Class.forName
            System.out.println("Clase no encontrada:");
            f.printStackTrace();
        }
    }

    /* Métodos */
    private void conectar() throws SQLException, ClassNotFoundException {
        // Carga el driver JDBC
        Class.forName(JDBC_DRIVER);
        // Abre una conexión
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void limpiar() throws SQLException {
        // Liberar memoria
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    private void menu() throws SQLException {
        int eleccion;
        System.out.println("Bienvenido a la base de datos Comida");

        do {
            System.out.println("\nOpciones:");
            System.out.println("1 - Ver pizzas");
            System.out.println("2 - Insertar una pizza");
            System.out.println("3 - Actualizar una pizza");
            System.out.println("0 - Salir");

            System.out.print("\nEscoja una opción: ");

            eleccion = teclado.nextInt();
            teclado.nextLine();

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
                    System.out.println("¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (eleccion != 0);
    }

    private void select() throws SQLException {
        System.out.println("\nCreando declaración...");
        stmt = conn.createStatement();
        String sql = "SELECT * FROM pizza";
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

    private void insert() throws SQLException {
        System.out.print("Ingrese el código de la nueva pizza: ");
        codigoPizza = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Ingrese el nombre de la pizza: ");
        nombrePizza = teclado.nextLine();

        System.out.print("Ingrese el precio de la pizza: ");
        valorPizza = teclado.nextInt();
        teclado.nextLine();

        System.out.println("\nCreando declaración...");
        sql = "INSERT INTO Pizza VALUES (?, ?, ?)";

        // Crea el prepared statement
        pstm = conn.prepareStatement(sql);
        pstm.setInt (1, codigoPizza);
        pstm.setInt (2, valorPizza);
        pstm.setString (3, nombrePizza);
        pstm.executeUpdate();

        System.out.println("Se ha ingresado la pizza");
    }

    private void update() throws SQLException {
        System.out.print("Ingrese el código de la pizza que desea modificar: ");
        codigoPizza = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Ingrese el nombre de la pizza: ");
        nombrePizza = teclado.nextLine();

        System.out.print("Ingrese el precio de la pizza: ");
        valorPizza = teclado.nextInt();
        teclado.nextLine();

        System.out.println("\nCreando declaración...");
        sql = "UPDATE Pizza SET nombrePizza=?, valorPizza=? WHERE codigoPizza=?";

        // Crea el prepared statement
        pstm = conn.prepareStatement(sql);
        pstm.setString (1, nombrePizza);
        pstm.setInt (2, valorPizza);
        pstm.setInt (3, codigoPizza);
        pstm.executeUpdate();

        System.out.println("Se ha actualizado la pizza");
    }
}