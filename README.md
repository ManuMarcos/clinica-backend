# Clínica Médica - Gestión de Consultas, Pacientes, Médicos, Servicios y Facturaciones

# Spring Boot Application

Este repositorio contiene una aplicación desarrollada con **Spring Boot**. A continuación, se detallan las instrucciones para ejecutar la aplicación en tu entorno local.

## Requisitos previos

Asegúrate de tener instalado lo siguiente antes de ejecutar la aplicación:

- **JDK 11 o superior**  
- **Maven**

## Clonar el repositorio

Ejecuta el siguiente comando para clonar el repositorio:

```bash
git clone https://github.com/ManuMarcos/clinica-backend.git
cd clinica-backend
```

## Construcción y ejecución

Para compilar y ejecutar la aplicación, usa los siguientes comandos:

```bash
mvn clean install
mvn spring-boot:run
```

## Documentación de la API

La documentación de la API generada con **Swagger** está disponible en:

```
http://localhost:8080/swagger-ui/index.html#/
```

## Consigna

Una clínica médica necesita desarrollar una aplicación web para gestionar las consultas, pacientes, médicos, servicios y facturaciones. Un analista funcional llevó a cabo un relevamiento detallado de las acciones y aspectos a tener en cuenta para el desarrollo del sistema.

## Servicios Médicos

La clínica ofrece diversos servicios médicos, tales como:

- Consultas generales
- Consultas especializadas (cardiología, dermatología, pediatría, etc.)
- Exámenes médicos (análisis de sangre, radiografías, ecografías, etc.)
- Cirugías
- Terapias y tratamientos varios

### Datos de cada servicio:

- `codigo_servicio`
- `nombre`
- `descripcion`
- `precio`

## Paquete de Servicios

Un paquete de servicios es una combinación de diferentes servicios médicos ofrecidos a un precio con descuento. Por ejemplo, un paquete puede incluir una consulta general y un examen de sangre.

### Datos de cada paquete de servicios:

- `codigo_paquete`
- `lista_servicios_incluidos`
- `precio_paquete`

El precio del paquete se calcula como la suma de los precios de los servicios que lo componen, con un descuento de 15% por ser un paquete.

Ejemplo:
Si la consulta general cuesta $2,000 y el examen de sangre $500, la sumatoria es $2,500, y el precio del paquete sería $2,500 - 15% = **$2,125**.

Además, se debe hacer un **descuento adicional de 20%** a aquellos pacientes que cuenten con Obra Social/Seguro Médico.

## Pacientes

El sistema debe permitir registrar nuevos pacientes. Para cada paciente se requiere la siguiente información:

- `id_paciente`
- `nombre`
- `apellido`
- `dni`
- `fecha_nac`
- `email`
- `telefono`
- `direccion`

## Médicos

El sistema debe permitir registrar médicos de la clínica. Cada médico debe contar con los mismos datos que un paciente, añadiendo:

- `especialidad médica`
- `turnos disponibles`
- `sueldo`

## Consultas/Citas Médicas

El sistema debe permitir registrar las consultas médicas, que pueden ser de diversos tipos (generales o especializadas). Cada consulta tendrá los siguientes datos:

- `id_consulta`
- `fecha_consulta`
- `hora_consulta`
- `un_paciente`
- `un_medico`
- `un_servicio` o `un_paquete` (el servicio o paquete contratado)
- `monto_total`
- `pagado` (Sí/No)

## ABML (Altas, bajas, modificaciones y lecturas necesarias)

El sistema debe ser capaz de realizar las operaciones ABML de los diferentes servicios, paquetes, pacientes, médicos y consultas. Para cada uno de ellos, se debe poder realizar:

- Alta
- Baja
- Modificación
- Consulta

## Entregables

1. Diagrama de clases del modelo de datos (UML).
2. Repositorios de GitHub (Frontend y Backend).
3. Especificación de configuraciones de base de datos.
4. Aplicación desplegada y en funcionamiento.
5. Documento de supuestos realizados por el analista/programador.

## Se Valorará

- Diseño atractivo y funcional.
- Visualización eficiente de los datos.
- Uso de buenas prácticas en el desarrollo.

## Bonus Point

El director de la clínica desea conocer las ganancias diarias y mensuales generadas por cada tipo de consulta o servicio. Se otorgarán puntos extra si el sistema permite:

- Calcular las ganancias de manera eficiente.
- Manejar alguna forma de registro de pagos, facturaciones y medios de pago.

Aún más puntos serán otorgados si el sistema permite la generación de recibos/facturas en formato PDF.
