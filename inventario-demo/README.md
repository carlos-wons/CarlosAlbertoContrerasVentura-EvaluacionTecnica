# Inventario Demo - Módulo de Inicio de Sesión (Maven)

Proyecto de ejemplo para la evaluación técnica: módulo de inicio de sesión con bcrypt.

## Estructura
- `pom.xml` - configuración Maven con dependencia `jbcrypt` y `mysql-connector-java`.
- `src/main/java/com/miproyecto/auth/` - código Java del módulo de autenticación y UI (Swing).

## Pasos para usar
1. Asegúrate tener JDK 11+ y Maven instalado (o usa el wrapper si lo añades).
2. Actualiza las credenciales y URL de la base de datos en `DBConnection.java` (host, usuario, contraseña, nombre de BD).
3. Construir: `mvn clean package`
4. Ejecutar: `mvn exec:java` (ejecuta `com.miproyecto.auth.LoginFrame` por defecto)
5. Para crear un usuario de prueba, puedes ejecutar la clase `CrearUsuarioTest` desde tu IDE o usar otra clase `main` cambiando el `mainClass` en el plugin de `pom.xml`.

> Nota: Las contraseñas se guardan como hash bcrypt. Instala las dependencias desde Maven antes de ejecutar.

