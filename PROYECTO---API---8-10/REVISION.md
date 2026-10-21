# Registro de Revisión y Corrección de Defectos (REVISION.md)

Este documento registra los riesgos y defectos detectados e inspeccionados en el código generado, tanto en la capa de APIs como en la capa de persistencia, junto con sus correcciones y verificaciones.

---

## 1. Capa de APIs y Red

### Defecto/Riesgo 1: Manejo de respuestas nulas o mapas vacíos en Dog CEO
- **Capa afectada**: Capa de Mapeadores y API (`DogMapper.kt`).
- **Archivo y método**: `DogMapper#toDomainList(dto: DogBreedsResponseDto)`.
- **Defecto/Riesgo**: El endpoint de Dog CEO devuelve un mapa `message` (`Map<String, List<String>>`) donde algunas razas podrían tener listas nulas o vacías. Si no se maneja adecuadamente, un acceso directo sin operadores seguros (`?.`) puede provocar una excepción de tipo `NullPointerException`.
- **Por qué es un problema**: Provocaría el cierre inesperado (`crash`) de la aplicación al procesar el JSON de la API.
- **Corrección aplicada**: Se utilizó operador de llamada segura `dto.message ?: return emptyList()` y verificación en la conversión del mapa, asegurando que `subrazas` reciba una lista vacía por defecto si el valor es nulo.
- **Verificación realizada**: Se probó con respuestas simuladas y con la API real, comprobando que la aplicación procesa correctamente el mapa y calcula el tamaño de las subrazas sin errores.

### Defecto/Riesgo 2: Bloqueo del hilo principal por llamadas de red síncronas
- **Capa afectada**: Capa de Repositorios (`WeatherRepository.kt`, `DogRepository.kt`, `CatFactRepository.kt`).
- **Archivo y método**: `WeatherRepository#getWeather()`.
- **Defecto/Riesgo**: Realizar llamadas a Retrofit sin asegurar el contexto de ejecución en un hilo secundario de E/S.
- **Por qué es un problema**: Las peticiones HTTP síncronas en el hilo principal congelan la interfaz de usuario y provocan el cierre por ANR (Application Not Responding).
- **Corrección aplicada**: Se encapsularon todas las llamadas a los servicios Retrofit dentro de bloques `withContext(Dispatchers.IO)` y bloques `try-catch` que retornan objetos `Result`.
- **Verificación realizada**: Se inspeccionó el código fuente de los repositorios y se confirmó que las operaciones de red se ejecutan en el hilo de E/S y son recolectadas de forma asíncrona mediante corrutinas en los ViewModels.

---

## 2. Capa de Persistencia (Room / Base de Datos)

### Defecto/Riesgo 3: Duplicación de registros en la tabla de favoritos
- **Capa afectada**: Capa de Base de Datos y DAO (`RazaFavoritaDao.kt`).
- **Archivo y método**: `RazaFavoritaDao#insertarFavorito(entity: RazaFavoritaEntity)`.
- **Defecto/Riesgo**: Si el usuario pulsa repetidamente el botón de marcar favorita, se podrían insertar múltiples filas con el mismo nombre de raza si la estrategia de conflicto no está definida.
- **Por qué es un problema**: Generaría datos redundantes en la base de datos y fallos lógicos al eliminar o contar favoritos.
- **Corrección aplicada**: Se especificó la estrategia de conflicto `@Insert(onConflict = OnConflictStrategy.REPLACE)` en el DAO de favoritos, garantizando que el nombre (clave primaria) actúe como actualizador único.
- **Verificación realizada**: Se realizaron múltiples pulsaciones consecutivas sobre el botón de favorito de una misma raza y se verificó mediante la observación del `Flow` que la base de datos mantiene un único registro por raza.

### Defecto/Riesgo 4: Consultas de Room ejecutadas en el hilo principal de la UI
- **Capa afectada**: Capa de ViewModel y UI (`FavoritesViewModel.kt`).
- **Archivo y método**: `FavoritesViewModel#eliminarFavorito(nombre: String)`.
- **Defecto/Riesgo**: Invocar operaciones de escritura o borrado en la base de datos directamente desde el hilo de la interfaz gráfica.
- **Por qué es un problema**: Room lanza una excepción en tiempo de ejecución (`Room cannot run queries on the main thread`) si detecta consultas de escritura síncronas en el hilo principal.
- **Corrección aplicada**: Se utilizó `viewModelScope.launch` para ejecutar las operaciones de inserción y borrado del repositorio de favoritos en un hilo secundario de forma asíncrona.
- **Verificación realizada**: Se ejecutó la acción de eliminar un favorito desde la pantalla correspondiente y se comprobó que el elemento desaparece instantáneamente de la lista sin generar excepciones de hilo principal.
