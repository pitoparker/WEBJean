# Thymeleaf — Resumen de elementos utilizados

## Namespace

```html
<html lang="es" xmlns:th="http://www.thymeleaf.org">
```

Declara el prefijo `th:` que activa los atributos de Thymeleaf. Sin esta declaración, el navegador los ignora como atributos desconocidos.

---

## Expresiones

### `${...}` — Variable expression

Accede a variables del modelo añadidas en el controller con `model.addAttribute(...)`.

```html
th:value="${name}"
th:text="${persona.name}"
```

### `@{...}` — Link expression

Genera URLs correctas respetando el context path de la aplicación.

```html
th:href="@{/web}"
th:href="@{/web/editar/{id}(id=${persona.id})}"
th:action="@{/web/nueva}"
```

La sintaxis `{id}(id=${persona.id})` sustituye el segmento `{id}` del path con el valor de la variable.

### `#temporals` — Utilidad de fechas

Formatea objetos `LocalDate` / `LocalDateTime`. Disponible automáticamente con Spring Boot.

```html
th:text="${#temporals.format(p.birthDate, 'dd/MM/yyyy')}"
```

### `#lists` — Utilidad de colecciones

Proporciona métodos de ayuda para trabajar con listas.

```html
th:if="${#lists.isEmpty(personas)}"
th:text="'Resultados: ' + ${#lists.size(personas)} + ' persona(s)'"
```

---

## Atributos `th:`

### `th:text`

Reemplaza el contenido de texto del elemento. Escapa HTML automáticamente (protege contra XSS).

```html
<td th:text="${p.name}"></td>
<span th:text="${p.rol}"></span>
```

### `th:href` / `th:action` / `th:value`

Versiones Thymeleaf de atributos HTML estándar. Permiten usar expresiones `${...}` y `@{...}` que el HTML nativo no puede evaluar.

```html
<a th:href="@{/web/nueva}">Nueva persona</a>
<form th:action="@{/web/borrar-grupo}" method="post">
<input type="text" th:value="${name}">
```

### `th:if` / `th:unless`

Renderiza el elemento solo si la condición es verdadera (`th:if`) o falsa (`th:unless`). Si no se cumple, el elemento **no aparece en el HTML generado**.

```html
<tr th:if="${#lists.isEmpty(personas)}">
    <td colspan="6">No se encontraron personas.</td>
</tr>

<div th:if="${persona != null}">
    <!-- formulario de edición -->
</div>

<div th:if="${persona == null}">
    <!-- mensaje de error -->
</div>
```

### `th:each`

Itera sobre una colección generando una copia del elemento por cada ítem. La variable de iteración es local al elemento y sus hijos.

```html
<!-- Una <tr> por cada Persona -->
<tr th:each="p : ${personas}">
    <td th:text="${p.id}"></td>
    <td th:text="${p.name}"></td>
</tr>

<!-- Un <option> por cada valor del enum Rol -->
<option th:each="r : ${roles}" th:value="${r}" th:text="${r}"></option>
```

### `th:selected`

Marca un `<option>` como seleccionado si la condición es verdadera. Se usa para mantener el valor activo en los filtros de búsqueda tras recargar la página.

```html
<option th:each="r : ${roles}"
        th:value="${r}"
        th:text="${r}"
        th:selected="${r.name() == rol}">
</option>
```

`r.name()` llama al método Java del enum para obtener el String (`"ALUMNO"`, `"PROFESOR"`...) y lo compara con el parámetro `rol` recibido del controller.

---

## Resumen por plantilla

| Plantilla | Elementos destacados |
|---|---|
| `lista.html` | `th:each` en filas y opciones, `th:selected` en filtro de rol, path variable en `@{/web/borrar/{id}(id=...)}` |
| `nueva.html` | `th:each` para generar el `<select>` del enum `Rol`, `th:action` en el formulario |
| `editar.html` | `th:if` doble (persona encontrada / no encontrada), `th:value` para precargar el campo con la clase actual |
| `fechas.html` | `th:if="${personas != null}"` para mostrar la tabla solo tras buscar, `#temporals.format`, `#lists.size` |
