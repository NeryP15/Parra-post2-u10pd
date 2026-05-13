# Productos Service - Post-Contenido 2 Unidad 10

[![CI con SonarQube](https://github.com/NeryP15/Parra-post2-u10pd/actions/workflows/ci.yml/badge.svg)](https://github.com/NeryP15/Parra-post2-u10pd/actions/workflows/ci.yml)

Repositorio para el laboratorio **Unidad 10: Metricas de Calidad y SonarQube**. El proyecto implementa:
- JaCoCo para cobertura de código (60%+)
- SonarQube con Quality Gate personalizado
- Pruebas unitarias exhaustivas
- Workflow de GitHub Actions para CI/CD automatizado
- Buenas prácticas de programación en Java 21

## Datos del entregable

| Elemento | Especificacion |
|---|---|
| Plataforma | GitHub, repositorio publico |
| Nombre del repositorio | `parra-post2-u10` |
| Proyecto SonarQube | `Productos Service` |
| Quality Gate | `Estandar Universidad` |
| Estructura requerida | `README.md`, `src/`, `.github/workflows/ci.yml`, `capturas/` |
| Commits minimos | 3 commits descriptivos |

## Requisitos

- JDK 21.
- Maven 3.9 o superior.
- Docker Desktop en ejecucion.
- SonarQube local disponible en `http://localhost:9000`.
- Token de SonarQube para ejecutar el analisis.

## Quality Gate configurado

En SonarQube se debe crear el Quality Gate **Estandar Universidad** con estas condiciones:

| Metrica | Condicion |
|---|---|
| Bugs | `is greater than 0` |
| Coverage | `is less than 60%` |
| Code Smells | `is greater than 5` |
| Duplicated Lines (%) | `is greater than 5%` |

El Quality Gate se asigna desde el dashboard del proyecto: **Project Settings > Quality Gate > Estandar Universidad**.

## Correcciones realizadas

| Hallazgo | Correccion aplicada | Archivo |
|---|---|---|
| Bug: `orElse(null)` en `buscar()` | Se reemplazo por `orElseThrow()` con `NoSuchElementException` descriptiva. | `src/main/java/edu/universidad/productos/service/ProductoService.java` |
| Code Smell: `@Autowired` en campo | Se reemplazo por inyeccion por constructor y atributo `final`. | `ProductoService.java` |
| Code Smell: comparacion con cadena vacia | Se usa `nombre == null || nombre.isBlank()`. | `ProductoService.java` |
| Code Smell: complejidad ciclomatica | Se extrajo la validacion al metodo privado `validarDatos()`. | `ProductoService.java` |

## Pruebas agregadas

Las pruebas unitarias verifican:

- `buscar()` retorna el producto cuando existe.
- `buscar()` lanza `NoSuchElementException` para un id inexistente.
- `procesarProducto()` normaliza el nombre y guarda un producto valido.
- `procesarProducto()` rechaza nombre vacio, precio invalido, precio superior al maximo y stock negativo.

## Ejecucion local

### Paso 1: Compilación, pruebas y cobertura

Limpia el proyecto anterior, compila el código, ejecuta pruebas unitarias y genera el reporte de cobertura JaCoCo:

```bash
mvn clean verify
```

**Resultado esperado:**
- ✅ Todos los tests pasan
- ✅ Reporte de cobertura generado en `target/site/jacoco/index.html`
- ✅ Binarios compilados listos en `target/classes/`

### Paso 2: Análisis con SonarQube local

Ejecuta el análisis en el servidor SonarQube local (asumiendo `http://localhost:9000`):

```bash
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=MI_TOKEN
```

Donde `MI_TOKEN` es tu token generado en SonarQube (Settings > Security > User Tokens).

**Alternativamente, sin especificar host (si usas valores por defecto):**

```bash
mvn clean verify sonar:sonar -Dsonar.token=MI_TOKEN
```

**Resultado esperado:**
- ✅ El análisis se envía a SonarQube local
- ✅ Dashboard disponible en `http://localhost:9000/dashboard?id=parra-post2-u10`
- ✅ Se evalúa contra el Quality Gate asignado

## GitHub Actions Workflow

El workflow se encuentra en `.github/workflows/ci.yml` y ejecuta automáticamente en cada push a `main`:

```bash
mvn -B clean verify sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.token=${{ secrets.SONAR_TOKEN }}
```

### Configuración del workflow

1. En tu repositorio GitHub, ve a **Settings > Secrets and variables > Actions**
2. Crea un secreto llamado `SONAR_TOKEN` con tu token de SonarCloud
3. El workflow se ejecutará automáticamente en cada push

**Nota:** El workflow actual usa SonarCloud. Para usar SonarQube local, modifica el archivo `.github/workflows/ci.yml` y cambia `https://sonarcloud.io` por `http://localhost:9000`.

## Evidencia del analisis

### Capturas requeridas

Después de ejecutar `mvn clean verify sonar:sonar`, toma estas capturas y guárdalas en `capturas/`:

1. **dashboard-antes.png**: Dashboard inicial (si ejecutaste antes de las correcciones)
2. **quality-gate.png**: Quality Gate configurado (Project Settings > Quality Gate)
3. **dashboard-despues.png**: Dashboard final después de las correcciones
4. **cobertura.png**: Reporte de cobertura (Coverage > línea de cobertura)

### Ubicación de capturas

```
capturas/
├── dashboard-antes.png
├── quality-gate.png
├── dashboard-despues.png
├── cobertura.png
└── README.md
```

**URLs en SonarQube local:**
- Dashboard: `http://localhost:9000/dashboard?id=parra-post2-u10`
- Quality Gate: `http://localhost:9000/project/quality_gates?id=parra-post2-u10`
- Cobertura: `http://localhost:9000/project/coverage?id=parra-post2-u10`

## Comparacion antes/despues

| Metrica | Antes | Despues | Resultado esperado |
|---|---:|---:|---|
| Bugs | - | 0 | Bug `orElse(null)` corregido ✅ |
| Code Smells | - | ≤ 5 | Al menos 3 Code Smells corregidos ✅ |
| Coverage (%) | - | ≥ 60 | JaCoCo reportado en SonarQube |
| Duplicated Lines (%) | - | ≤ 5 | Dentro del Quality Gate |

**Instrucciones para completar:**
1. Ejecuta un primer análisis **antes** de cualquier cambio: `mvn clean verify sonar:sonar`
2. Anota los valores en la columna "Antes"
3. Realiza las correcciones necesarias
4. Ejecuta segundo análisis: `mvn clean verify sonar:sonar`
5. Anota los valores finales en la columna "Despues"

## Historial de commits sugerido

El repositorio debe conservar como minimo estos commits descriptivos:

1. `docs: documentar analisis inicial de sonarqube`
2. `fix: corregir bug y code smells de producto service`
3. `ci: agregar analisis de sonarqube en github actions`

## Checklist de entrega

- [ ] Repositorio publico en GitHub con nombre `parra-post2-u10`.
- [ ] Quality Gate **Estandar Universidad** creado con 4 condiciones.
- [ ] Proyecto **Productos Service** asignado al Quality Gate personalizado.
- [ ] Segundo analisis ejecutado con `mvn clean verify sonar:sonar -Dsonar.token=TU_TOKEN`.
- [ ] Capturas reales guardadas en `capturas/` y visibles en este README.
- [ ] Workflow de GitHub Actions ejecutando Maven y SonarQube.
- [ ] Minimo 3 commits descriptivos en el historial.
