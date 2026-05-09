# ds-project

## Descripción

Este repositorio es el proyecto final de la materia de Estructura de Datos.

## Git - Flujo de trabajo y convenciones

1. Usar convenciones de nomenclatura para los commits, por ejemplo:
   - `feat: agregar nueva funcionalidad`
   - `fix: corregir bug`
   - `docs: actualizar documentación`
   - `refactor: refactorizar código`
   - `test: agregar o modificar pruebas`
   - `chore: tareas de mantenimiento`
   - `style: cambios de formato sin afectar la lógica`
2. Moverse a la rama `dev` para trabajar en nuevas funcionalidades o correcciones.
   - `git checkout dev`
   - `git pull origin dev` para asegurarse de tener la última versión.
3. Empezar a trabajar en la nueva funcionalidad o corrección.
4. Agregar los cambios al área de staging:
   - `git add .`
5. Hacer un commit con un mensaje descriptivo siguiendo las convenciones de nomenclatura:
   - `git commit -m "feat: add new feature"`
6. Subir los cambios a la rama `dev`:
   - `git pull origin dev` para asegurarse de que no haya conflictos con otros cambios.
   - Resolver cualquier conflicto si es necesario.
   - `git push origin dev`

Nota: No se deben hacer commits directamente en la rama `main`. Todos los cambios deben pasar por la rama `dev` para asegurar un flujo de trabajo organizado y controlado.
