# Registro de Uso de Inteligencia Artificial (IA_LOG.md)

## 1. Herramienta de IA Utilizada
- **Asistente**: Gemini / Claude (Integrado en Android Studio a través del agente de desarrollo).

## 2. Prompts Principales
- "Implementar la aplicación Android completa para el Grupo 2: Explorador de naturaleza y clima, siguiendo la arquitectura API → Repository → Mapper → DB, MVVM, Room, y Retrofit para Open-Meteo, Dog CEO y Cat Facts."
- "Configurar la entidad RazaFavoritaEntity, RazaFavoritaDao con Flow y suspend, y AppDatabase como Singleton."
- "Implementar DogViewModel con derivedStateOf para el buscador en tiempo real sobre la lista de razas de perros."

## 3. Funcionalidades Intervenidas por la IA
- Diseño y estructuración de paquetes por capas (`data/remote`, `data/local`, `data/repository`, `data/mapper`, `domain/model`, `ui`).
- Implementación de los servicios Retrofit y DTOs para las tres APIs obligatorias (Open-Meteo, Dog CEO con `Map<String, List<String>>`, y Cat Facts).
- Configuración de Room con KSP y definición de la entidad `RazaFavoritaEntity` y su DAO reactivo con `Flow`.
- Creación de pantallas en Jetpack Compose con Material 3 y navegación mediante pestañas inferiores (`NavigationBar`).

## 4. Decisiones de Diseño Propuestas por la IA y Criterios Aplicados
- **Decisión 1**: Uso de `derivedStateOf` en `DogViewModel` para el buscador de razas de perros.
  - *Mantenida*: Evita recomputar toda la lista en cada pulsación del teclado, optimizando el rendimiento en Jetpack Compose.
- **Decisión 2**: Inyección manual de repositorios y DAO mediante `AppDatabase.getDatabase(application)` en los ViewModels.
  - *Mantenida*: Sencilla y robusta para una aplicación de alcance de parcial sin requerir Hilt/Dagger complejo, manteniendo desacoplada la interfaz de Room.
- **Decisión 3**: Mapeo explícito de `Map<String, List<String>>` de Dog CEO a modelos de dominio `Breed`.
  - *Mantenida*: Cumple rigurosamente con la separación entre DTO y modelo de dominio, permitiendo calcular `cantidadSubrazas` de manera limpia.

## 5. Datos a Completar por el Desarrollador
- Ninguno pendiente; todos los componentes de código, DTOs, entidades de Room y documentación están implementados y verificados.
