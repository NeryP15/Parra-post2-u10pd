# Productos Service - Post-Contenido 2 Unidad 10

[![CI con SonarQube](https://github.com/USUARIO/parra-post2-u10/actions/workflows/ci.yml/badge.svg)](https://github.com/USUARIO/parra-post2-u10/actions/workflows/ci.yml)

Repositorio para el laboratorio **Unidad 10: Metricas de Calidad y SonarQube**. El proyecto configura JaCoCo, SonarQube, pruebas unitarias y un workflow de GitHub Actions para automatizar la inspeccion en cada push.

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

Compilar, ejecutar pruebas y generar cobertura JaCoCo:

```bash
mvn clean verify
```

Ejecutar el segundo analisis de SonarQube:

```bash
mvn clean verify sonar:sonar -Dsonar.token=TU_TOKEN
```

Si el servidor local no usa los valores por defecto, agrega:

```bash
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=TU_TOKEN
```

## GitHub Actions

El workflow se encuentra en `.github/workflows/ci.yml` y ejecuta:

```bash
mvn -B clean verify sonar:sonar
```

Para usar SonarQube Cloud en GitHub Actions, configura el secreto:

`Settings > Secrets and variables > Actions > New repository secret > SONAR_TOKEN`

## Evidencia del analisis

### Dashboard antes de las correcciones

![Dashboard antes](capturas/dashboard-antes.png)

### Quality Gate Estandar Universidad

![Quality Gate](capturas/quality-gate.png)

### Dashboard despues de las correcciones

![Dashboard despues](capturas/dashboard-despues.png)

## Comparacion antes/despues

Actualiza la columna "Antes" con los datos del Post-Contenido 1 y la columna "Despues" con el segundo analisis.

| Metrica | Antes | Despues | Resultado esperado |
|---|---:|---:|---|
| Bugs | 1 | 0 | Bug `orElse(null)` corregido |
| Code Smells | 8 | 5 o menos | Al menos 3 Code Smells corregidos |
| Coverage | Completar con SonarQube | 60% o mas | JaCoCo visible en SonarQube |
| Duplicated Lines (%) | Completar con SonarQube | 5% o menos | Dentro del Quality Gate |

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
