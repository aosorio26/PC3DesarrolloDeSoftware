# Reporta Tu Calle - PC3 Patrones Estructurales

## Instrucciones para revisión del programa (para que se pueda ejecutar)
Profesor, le dejo presente las instrucciones por si se necesita correr el programa.
## Cómo correr el backend

Desde VSCode:

```bash
cd backend
```

Ejecuta:

```bash
mvn spring-boot:run
```

El backend inicia en:

```text
http://localhost:8080
```

## Cómo correr el frontend

En otra terminal:

```bash
cd frontend
```

Luego se ejecuta

```bash
python -m http.server 5500
```
En caso no le funcione, profesor, use: 

```bash
py -m http.server 5500
```
Y en el navegador:

```text
http://localhost:5500
```