# MySQL Data View

Programa que proporciona una interfaz intuitiva para la gestión de bases de datos MySQL, eliminando la necesidad de utilizar comandos SQL. La aplicación lee los datos de conexión desde un archivo .txt y permite la visualización de datos en tablas, así como operaciones de eliminación (única y múltiple), actualización (directamente en las celdas de la tabla).

**Tecnologías Utilizadas:** Java, JDBC, Swing

## Funcionalidades Principales

* **Conexión a Base de Datos MySQL:**
    * Lee las credenciales de conexión desde un archivo `.txt` para establecer la conexión con una base de datos MySQL.
* **Visualización de Datos en Tablas:**
    * Muestra los datos de la base de datos de forma intuitiva en tablas, seleccionando la tabla cuyos datos se desean visualizar.
    * Permite la interacción con filas y celdas para realizar operaciones de eliminación y actualización.

## Estructura del repositorio

* `src`: Contiene el código fuente del MySQL Data View
* `executable`: Contiene el archivo ejecutable del programa, con `ViewerMySQL.jar` y `credentials.txt` (datos de conexión a la DB MySQL)

## Requisitos previos
1.  **Java:** Descarga e instala el JDK o JRE (versión >= 17) desde [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) .
2.  **MySQL:** Descarga e instala [MySQL](https://www.mysql.com/downloads/).

## Ejecución
1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/uranium092/GUI_MySQL
    ```
2. **Navega a `executable/`:**
   * Abrir `credentials.txt` y adaptar los datos de conexión según sea necesario:
     ```bash
     jdbc:mysql://localhost:3306/nameDB
     root
     admin
     ```
     La línea 1 es la `connection-uri`; la línea 2 el `username`; y la 3 el `password`.
   * Ejecutar `ViewerMySQL.jar` dando doble click o corriendo `java -jar ViewerMySQL.jar`
