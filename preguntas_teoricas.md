# 📘 Evaluación Técnica – [Grupo Castores]
**Postulante:** [Carlos Alberto Contreras Ventura]  
**Fecha:** [22/10/2025]  
**Puesto al que aplicas:** [Desarrollador]

---

## 🧠 1. CONOCIMIENTOS SQL

**Introducción:**  
SQL (Structured Query Language) es el lenguaje estándar utilizado para la gestión y manipulación de bases de datos relacionales.  
Permite realizar operaciones de consulta, inserción, actualización y eliminación de datos, así como definir estructuras de tablas, relaciones y restricciones.

El conocimiento de SQL incluye no solo la capacidad de escribir consultas básicas, sino también de manejar conceptos avanzados como **joins**, **triggers** y **stored procedures**, que permiten combinar información de múltiples tablas, automatizar tareas y mantener la integridad y consistencia de los datos en entornos complejos.  

En esta sección se abordarán los conceptos fundamentales y prácticos de SQL, proporcionando un entendimiento sólido de su funcionamiento y aplicaciones en la gestión de bases de datos.

### 1.1) Describe el funcionamiento general de la sentencia JOIN

**Respuesta:**  
La sentencia `JOIN` se utiliza en SQL para combinar registros de dos o más tablas basándose en una relación lógica entre ellas, generalmente definida a través de una clave primaria y una clave foránea.  
Su propósito principal es consultar y unificar información que se encuentra distribuida en distintas tablas, generando un único conjunto de resultados coherente.  

El funcionamiento se basa en comparar los valores de las columnas relacionadas entre las tablas, devolviendo únicamente las combinaciones de filas que cumplan con la condición especificada.  
Existen diferentes tipos de `JOIN` —como `INNER JOIN`, `LEFT JOIN`, `RIGHT JOIN` y `FULL JOIN`— que determinan qué registros se incluyen en el resultado según la correspondencia entre las tablas.

> En resumen, `JOIN` permite trabajar con datos de manera relacional, evitando la duplicación de información y facilitando la consulta de estructuras de base de datos normalizadas.

### 1.2) ¿Cuáles son los tipos de JOIN y cuál es el funcionamiento de los mismos?

**Respuesta:**  
Los tipos principales de `JOIN` en SQL son **INNER JOIN**, **LEFT JOIN**, **RIGHT JOIN** y **FULL JOIN**.  
Cada uno determina cómo se combinan los registros de las tablas involucradas y qué filas se incluyen en el conjunto de resultados final.

---

#### 🔹 Tipos de JOIN y su funcionamiento

- **INNER JOIN**  
  Devuelve únicamente las filas que tienen valores coincidentes en ambas tablas.  
  Se utiliza cuando se desea obtener solo los registros que están relacionados.

- **LEFT JOIN (LEFT OUTER JOIN)**  
  Devuelve todos los registros de la tabla izquierda y las coincidencias de la tabla derecha.  
  Si no hay coincidencia, los campos de la tabla derecha aparecen como `NULL`.

- **RIGHT JOIN (RIGHT OUTER JOIN)**  
  Devuelve todos los registros de la tabla derecha y las coincidencias de la tabla izquierda.  
  Los registros sin coincidencia en la izquierda muestran valores `NULL`.

- **FULL JOIN (FULL OUTER JOIN)**  
  Devuelve todos los registros de ambas tablas, coincidan o no.  
  Cuando no existe correspondencia, los valores faltantes se completan con `NULL`.

---

#### 🔸 Esquema comparativo

| Tipo de JOIN | Registros de tabla izquierda | Registros de tabla derecha | Incluye coincidencias | Incluye no coincidencias |
|---------------|------------------------------|-----------------------------|------------------------|---------------------------|
| **INNER JOIN** | ✔️ Solo los que coinciden | ✔️ Solo los que coinciden | ✅ | ❌ |
| **LEFT JOIN**  | ✔️ Todos | ✔️ Solo coincidencias | ✅ | Parcial (izquierda) |
| **RIGHT JOIN** | ✔️ Solo coincidencias | ✔️ Todos | ✅ | Parcial (derecha) |
| **FULL JOIN**  | ✔️ Todos | ✔️ Todos | ✅ | ✅ |

---

> En resumen, los tipos de `JOIN` determinan el alcance de la combinación entre tablas, permitiendo obtener distintos conjuntos de datos según las relaciones existentes o ausentes entre ellas.

### 1.3) ¿Cuál es el funcionamiento general de los TRIGGER y qué propósito tienen?

**Respuesta:**  
Un **TRIGGER** es un procedimiento almacenado en una base de datos que se ejecuta automáticamente cuando ocurre un evento específico sobre una tabla o vista, como una **inserción (`INSERT`)**, **actualización (`UPDATE`)** o **eliminación (`DELETE`)** de datos.

#### 🔹 Funcionamiento general
Cuando se define un `TRIGGER`, se asocia a una tabla y a un tipo de evento.  
Al producirse dicho evento, la base de datos ejecuta automáticamente el bloque de código SQL que contiene el `TRIGGER`, sin necesidad de intervención del usuario o la aplicación.  
Este código puede realizar acciones adicionales, validar información o registrar cambios según la lógica establecida.

#### 🔹 Propósito principal
Los `TRIGGER` tienen como objetivos fundamentales:
- **Mantener la integridad de los datos:** aseguran que las reglas y restricciones se cumplan automáticamente.  
- **Aplicar reglas de negocio:** permiten ejecutar acciones automáticas cuando se realizan operaciones críticas.  
- **Auditar o registrar cambios:** se pueden usar para crear bitácoras de modificaciones en las tablas.  
- **Automatizar tareas repetitivas:** eliminan la necesidad de intervención manual para tareas predecibles.

---

#### 🔸 Esquema del ciclo de un TRIGGER

| Etapa | Descripción |
|--------|--------------|
| **Definición** | Se crea el `TRIGGER` asociado a una tabla y evento específico (`INSERT`, `UPDATE`, `DELETE`). |
| **Activación** | Ocurre automáticamente cuando se ejecuta la operación definida. |
| **Ejecución** | Se ejecuta el bloque de código SQL interno del `TRIGGER`. |
| **Resultado** | Se aplican las acciones automáticas (validaciones, inserciones, auditorías, etc.). |

---

> En resumen, los `TRIGGER` permiten automatizar respuestas ante eventos en la base de datos, garantizando coherencia, trazabilidad y cumplimiento de las reglas de negocio sin requerir intervención directa del usuario.

### 1.4) ¿Qué es y para qué sirve un STORED PROCEDURE?

**Respuesta:**  
Un **Stored Procedure** (procedimiento almacenado) es un conjunto de instrucciones SQL precompiladas que se almacenan directamente en el servidor de base de datos.  
Puede ser ejecutado múltiples veces mediante una simple llamada, lo que permite centralizar la lógica de negocio y reducir la necesidad de enviar consultas repetitivas desde la aplicación cliente.

#### 🔹 Propósito y ventajas principales
- **Eficiencia y rendimiento:** al estar precompilados, se ejecutan más rápido y reducen el tráfico entre la aplicación y la base de datos.  
- **Reutilización y mantenimiento:** permiten agrupar consultas SQL complejas en un solo bloque reutilizable.  
- **Seguridad:** facilitan el control de acceso a los datos al otorgar permisos sobre el procedimiento en lugar de sobre las tablas directamente.  
- **Consistencia:** ayudan a mantener reglas de negocio uniformes y centralizadas dentro del sistema.

---

#### 🔸 Ejemplo de un Stored Procedure en SQL

```sql
-- Creación de un procedimiento almacenado que devuelve los productos activos
CREATE PROCEDURE ObtenerProductosActivos()
BEGIN
    SELECT id_producto, nombre, precio
    FROM productos
    WHERE estado = 'ACTIVO';
END;

-- Ejecución del procedimiento
CALL ObtenerProductosActivos();

> En resumen, los Stored Procedures permiten mejorar el rendimiento, la seguridad y la organización del código SQL, facilitando la gestión eficiente y consistente de la lógica de negocio dentro de la base de datos.
