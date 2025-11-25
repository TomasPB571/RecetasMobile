# 📱 RecetasMobile -- Documentación General

## 🎯 Propósito y Alcance

Este documento ofrece una visión general de **RecetasMobile**, una
aplicación Android diseñada para gestionar recetas de cocina mediante
creación, visualización, edición, eliminación y organización con
categorías.

Incluye una descripción de: - Funcionalidades principales\
- Arquitectura\
- Componentes clave\
- Base de datos\
- Tecnologías utilizadas

------------------------------------------------------------------------

## 🍽️ Propósito de la Aplicación

**RecetasMobile** es una app nativa Android que permite administrar
recetas personales. Provee un CRUD completo tanto para recetas como para
categorías, facilitando un recetario digital organizado e intuitivo.

------------------------------------------------------------------------

## ⭐ Funcionalidades Principales

  -----------------------------------------------------------------------
  Funcionalidad                          Descripción
  -------------------------------------- --------------------------------
  **Gestión de Recetas**                 Añadir, ver, editar y eliminar
                                         recetas con nombre, tipo,
                                         descripción e imagen URL
                                         opcional

  **Gestión de Categorías**              Crear y administrar categorías
                                         personalizadas

  **Búsqueda y Filtros**                 Buscar por nombre y filtrar por
                                         tipo: Desayuno, Almuerzo, Cena,
                                         Postre

  **Almacenamiento Local**               Persistencia en SQLite

  **Integración con API Externa**        Base para importar recetas desde
                                         TheMealDB

  **Navegación Intuitiva**               Flujo claro entre pantallas
                                         basado en Activities
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 🧰 Tecnologías Utilizadas

-   **Android SDK**
-   **Kotlin/Java**
-   **AndroidX**
-   **Material Design Components**
-   **SQLite**
-   **API 24+**
-   **Compilado con API 36**

------------------------------------------------------------------------

## 🏗️ Arquitectura General

La aplicación sigue una arquitectura por capas:

-   Presentación (Activities)
-   Adapters (RecyclerView)
-   DataProvider (Fachada)
-   DatabaseHelper (CRUD SQLite)
-   ApiService (API externa)
-   Modelos (Receta, Categoria)

------------------------------------------------------------------------

## 🔧 Componentes Principales

  Componente             Clase
  ---------------------- --------------------------
  Lista de recetas       MainActivity
  Adaptador              RecetaAdapter
  Detalles               DetalleRecetaActivity
  Crear receta           AgregarRecetaActivity
  Editar receta          EditarRecetaActivity
  Categorías             CategoriasActivity
  Adaptador categorías   CategoriaAdapter
  Crear categoría        AgregarCategoriaActivity
  Editar categoría       EditarCategoriaActivity
  Base de datos          DatabaseHelper
  Acceso simplificado    DataProvider
  API externa            ApiService
  Modelos                Receta, Categoria

------------------------------------------------------------------------

## 🗄️ Base de Datos (SQLite)

**Base:** recetas_db\
**Versión:** 2

Tablas: - recetas (id, nombre, tipo, descripcion, imagen) - categorias
(id, nombre, UNIQUE) - usuarios (no usada actualmente)

------------------------------------------------------------------------

## 🚀 Ciclo de Inicio

1.  MainActivity inicia\
2.  DataProvider se inicializa\
3.  Se carga estructura de API externa\
4.  RecyclerView + Adaptadores\
5.  Spinner configurado\
6.  Búsqueda y filtros operativos

------------------------------------------------------------------------

## 🔄 Flujo de Datos

-   MainActivity y adaptadores usan DataProvider\
-   CRUD directo usa DatabaseHelper\
-   Búsqueda filtrada en RecetaAdapter\
-   Filtros por tipo en DataProvider

------------------------------------------------------------------------

## 🔐 Permisos

    <uses-permission android:name="android.permission.INTERNET" />

------------------------------------------------------------------------

## 📦 Estructura del Proyecto

    com.example.libroderecetaspro
     ├── Activities
     ├── Adapters
     ├── DatabaseHelper
     ├── DataProvider
     ├── ApiService
     ├── Receta
     └── Categoria

------------------------------------------------------------------------

## ⚙️ Configuración Build

  Config           Valor
  ---------------- -------------------------------
  Application ID   com.example.libroderecetaspro
  minSdk           24
  targetSdk        36
  compileSdk       36
  versionCode      1
  versionName      1.0
  Java             11
  ProGuard         OFF
