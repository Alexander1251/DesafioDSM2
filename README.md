# Meerkat

Esta aplicación para colegios permite a los maestros ingresar y gestionar las notas de los estudiantes. La aplicación fue desarrollada en Android Studio usando Kotlin y Firebase, cumpliendo con los requerimientos del Segundo Desafío Práctico DSM.

## Características

- **Login / Registro:**  
  Permite a los maestros autenticarse usando Firebase Authentication. Si el usuario no está registrado, puede crear una cuenta.

- **Registro de Estudiantes:**  
  Los maestros pueden ingresar notas de los estudiantes, capturando:
  - Nombre y apellido
  - Grado (mediante un Spinner)
  - Materia (mediante un Spinner)
  - Nota final (validada para estar entre 0 y 10)

- **Listado de Estudiantes:**  
  Visualización de los estudiantes registrados junto con sus notas finales en una pantalla con RecyclerView.

- **Actualización y Eliminación:**  
  Opción para editar los datos de un estudiante y para eliminar registros.

- **Notificaciones:**  
  Se utilizan Toasts para notificar acciones exitosas o errores.

- **Diseño personalizado:**  
  - **Icono y nombre:** Se utilizó [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html) para diseñar un ícono personalizado.
  - **Paleta de colores:** Se implementó una paleta de colores personalizada usando [Material Palette](https://www.materialpalette.com/).
  
## Tecnologías utilizadas

- Android Studio
- Kotlin
- Firebase (Authentication y Realtime Database)
- Material Design

## Instalación

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/Alexander1251/DesafioDSM2.git


Alumno: Cristian Alexander Alfaro Hernández AH211671
