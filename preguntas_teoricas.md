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
```
---

> En resumen, los Stored Procedures permiten mejorar el rendimiento, la seguridad y la organización del código SQL, facilitando la gestión eficiente y consistente de la lógica de negocio dentro de la base de datos.

### 1.5) Traer todos los productos que tengan una venta

**Solución SQL:**

```sql
SELECT DISTINCT p.idProducto, p.nombre, p.precio
FROM productos p
INNER JOIN ventas v ON p.idProducto = v.idProducto;
```

## Explicación de la Consulta 1

Esta consulta tiene como objetivo traer todos los productos que han tenido al menos una venta registrada en la base de datos.  
Se utiliza un **INNER JOIN** para combinar las tablas `productos` y `ventas` únicamente en los casos donde exista correspondencia entre el `idProducto`.

### Detalle paso a paso

1. **FROM productos p**  
   Se toma `productos` como tabla principal y se le asigna un alias `p` para simplificar la referencia a sus columnas.

2. **INNER JOIN ventas v ON p.idProducto = v.idProducto**  
   Se realiza un `INNER JOIN` con la tabla `ventas` (`v`) a través de la columna `idProducto`.  
   Esto garantiza que solo se incluyan los productos que aparecen en la tabla de ventas.

3. **SELECT DISTINCT p.idProducto, p.nombre, p.precio**  
   Se seleccionan las columnas relevantes de la tabla `productos`.  
   `DISTINCT` evita que un mismo producto aparezca repetido si tiene varias ventas registradas.

> En resumen, la consulta devuelve la lista de productos que han sido vendidos, mostrando su identificador, nombre y precio, asegurando que cada producto aparezca solo una vez.

### 1.6) Traer todos los productos que tengan ventas y la cantidad total de productos vendidos

**Solución SQL:**

```sql
SELECT p.idProducto, p.nombre, SUM(v.cantidad) AS total_vendido
FROM productos p
INNER JOIN ventas v ON p.idProducto = v.idProducto
GROUP BY p.idProducto, p.nombre;
```

## Explicación de la Consulta 2

Esta consulta tiene como objetivo obtener todos los productos que han sido vendidos junto con la **cantidad total de cada producto** vendida en todas las transacciones registradas.

### Detalle paso a paso

1. **FROM productos p**  
   Se toma la tabla `productos` como base para la consulta y se le asigna un alias `p`.

2. **INNER JOIN ventas v ON p.idProducto = v.idProducto**  
   Se realiza un `INNER JOIN` para incluir solo los productos que aparecen en la tabla de ventas.

3. **SUM(v.cantidad) AS total_vendido**  
   La función `SUM()` acumula la cantidad vendida de cada producto en todas las filas de la tabla `ventas`.

4. **GROUP BY p.idProducto, p.nombre**  
   Se agrupan los resultados por producto, de modo que la suma corresponda a cada producto individual.

> En resumen, la consulta devuelve cada producto vendido junto con el total de unidades vendidas, proporcionando un resumen claro de las ventas por producto.

### 1.7) Traer todos los productos y la suma total ($) vendida por producto

**Solución SQL:**

```sql
SELECT p.idProducto, p.nombre, COALESCE(SUM(v.cantidad * p.precio), 0) AS total_vendido
FROM productos p
LEFT JOIN ventas v ON p.idProducto = v.idProducto
GROUP BY p.idProducto, p.nombre;
```

## Explicación de la Consulta 3

El objetivo de esta consulta es obtener **todos los productos**, incluyendo los que no han sido vendidos, junto con la **suma total de dinero generada** por cada producto.

### Detalle paso a paso

1. **FROM productos p**  
   Se toma la tabla `productos` como base de la consulta y se le asigna un alias `p`.

2. **LEFT JOIN ventas v ON p.idProducto = v.idProducto**  
   Un `LEFT JOIN` asegura que todos los productos aparezcan en el resultado, incluso si no tienen ventas registradas.

3. **SUM(v.cantidad * p.precio)**  
   Calcula la suma del total vendido en dinero para cada producto.

4. **COALESCE(..., 0)**  
   Se reemplazan valores `NULL` por `0` para los productos que no tienen ventas, evitando resultados vacíos.

5. **GROUP BY p.idProducto, p.nombre**  
   Agrupa los resultados por producto, asegurando que cada suma corresponda al producto correcto.

> En resumen, la consulta proporciona un informe completo de ventas por producto, incluyendo aquellos que no generaron ingresos.
