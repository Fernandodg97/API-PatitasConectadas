
## Installation

Installation PostgreSQL

```bash
  sudo apt update

  sudo apt install -y postgresql

  systemctl status postgresql
```
New user

```bash
sudo -iu postgres createuser -P --interactive fernando
```
View:
```bash
Enter password for new role: *******
Enter it again: *******
Shall the new role be a superuser? (y/n) y
```

New database
```bash
createdb fernando -O fernando
```

Conexion

```bash
psql -U fernando -d postgres
```

External Conexion
```bash
psql -U fernando -d fernando -h localhost -p 5432
```

Installation pgAdmin

Install the public key for the repository (if not done previously):

```bash
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | sudo gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg
```

Create the repository configuration file:

```bash
sudo sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list && apt update'
```
Install for both desktop and web modes:
```bash
sudo apt install pgadmin4
```
Install for desktop mode only:
```bash
sudo apt install pgadmin4-desktop
```
Install for web mode only: 
```bash
sudo apt install pgadmin4-web 
```
Configure the webserver, if you installed pgadmin4-web:
```bash
sudo /usr/pgadmin4/bin/setup-web.sh
```

# Documentación API - Módulo de Perfiles

## Descripción General
Este módulo permite gestionar los perfiles de usuarios en el sistema. Cada perfil está asociado a un usuario específico y contiene información personal como descripción, fecha de nacimiento e imagen.

## Endpoints

### 1. Obtener todos los perfiles
- **Método**: GET
- **URL**: `/perfiles`
- **Descripción**: Retorna una lista de todos los perfiles existentes en el sistema
- **Respuesta Exitosa**:
  ```json
  [
    {
      "id": 1,
      "usuario_id": 123,
      "descripcion": "Descripción del perfil",
      "fecha_nacimiento": "1990-01-01",
      "img": "url_imagen"
    }
  ]
  ```

### 2. Crear un nuevo perfil
- **Método**: POST
- **URL**: `/perfiles`
- **Descripción**: Crea un nuevo perfil en el sistema
- **Cuerpo de la Petición**:
  ```json
  {
    "usuario_id": 123,
    "descripcion": "Descripción del perfil",
    "fecha_nacimiento": "1990-01-01",
    "img": "url_imagen"
  }
  ```
- **Respuesta Exitosa** (201 Created):
  ```json
  {
    "id": 1,
    "usuario_id": 123,
    "descripcion": "Descripción del perfil",
    "fecha_nacimiento": "1990-01-01",
    "img": "url_imagen"
  }
  ```

### 3. Obtener perfil por ID de usuario
- **Método**: GET
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Obtiene el perfil asociado a un usuario específico
- **Parámetros URL**:
  - `id`: ID del usuario

### 4. Actualizar perfil
- **Método**: POST
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Actualiza la información de un perfil existente
- **Parámetros URL**:
  - `id`: ID del usuario

### 5. Eliminar perfil
- **Método**: DELETE
- **URL**: `/usuarios/{id}/perfiles`
- **Descripción**: Elimina un perfil del sistema
- **Parámetros URL**:
  - `id`: ID del usuario

## Modelo de Datos

### PerfilModel
```json
{
  "id": "Long",
  "usuario_id": "Long",
  "descripcion": "String",
  "fecha_nacimiento": "Date (formato: YYYY-MM-DD)",
  "img": "String (URL de la imagen)"
}
```

## Consideraciones Importantes
1. El campo `fecha_nacimiento` debe ser enviado en formato YYYY-MM-DD
2. El campo `img` debe contener la URL completa de la imagen
3. El `usuario_id` debe corresponder a un usuario existente en el sistema
4. Todos los endpoints requieren autenticación

## Manejo de Errores
- Los errores 500 indican problemas del servidor
- Los errores 404 indican que el recurso no fue encontrado
- Los errores 400 indican que la petición es inválida

# Documentación Completa de la API Patitas Conectadas

## 1. Autenticación (Auth)

### `POST /auth/login`
**Descripción:** Inicia sesión de usuario y devuelve un token JWT.

**Ejemplo Request:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Ejemplo Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### `POST /auth/register`
**Descripción:** Registra un nuevo usuario en el sistema.

**Ejemplo Request:**
```json
{
  "email": "nuevo@ejemplo.com",
  "password": "contraseña123",
  "nombre": "Juan",
  "apellido": "Pérez"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "nuevo@ejemplo.com"
}
```

### `GET /auth/me`
**Descripción:** Obtiene información del usuario autenticado.

**Headers requeridos:**
- Authorization: Bearer {token}

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "nuevo@ejemplo.com",
  "perfil": {
    "id": 1,
    "usuarioId": 1,
    "descripcion": "Amante de los animales",
    "ubicacion": "Barcelona"
  },
  "mascotas": [
    {
      "id": 1,
      "usuarioId": 1,
      "nombre": "Toby",
      "genero": "Macho",
      "raza": "Labrador"
    }
  ]
}
```

## 2. Usuarios

### `GET /usuarios`
**Descripción:** Obtiene todos los usuarios registrados.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@ejemplo.com"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "López",
    "email": "maria@ejemplo.com"
  }
]
```

### `GET /usuarios/{id}`
**Descripción:** Obtiene información de un usuario específico.

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com"
}
```

### `POST /usuarios`
**Descripción:** Crea un nuevo usuario.

**Ejemplo Request:**
```json
{
  "nombre": "Carlos",
  "apellido": "Gómez",
  "email": "carlos@ejemplo.com",
  "password": "contraseña123"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "nombre": "Carlos",
  "apellido": "Gómez",
  "email": "carlos@ejemplo.com"
}
```

### `PUT /usuarios/{id}`
**Descripción:** Actualiza información de un usuario existente.

**Ejemplo Request:**
```json
{
  "nombre": "Carlos",
  "apellido": "Gómez Rodríguez",
  "email": "carlos@ejemplo.com",
  "password": "contraseña123"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "nombre": "Carlos",
  "apellido": "Gómez Rodríguez",
  "email": "carlos@ejemplo.com"
}
```

### `DELETE /usuarios/{id}`
**Descripción:** Elimina un usuario del sistema.

**Ejemplo Response:**
```json
{
  "mensaje": "Usuario con ID: 3 eliminado correctamente"
}
```

## 3. Mascotas

### `GET /usuarios/{usuarioId}/mascotas`
**Descripción:** Obtiene todas las mascotas de un usuario.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "nombre": "Toby",
    "genero": "Macho",
    "raza": "Labrador"
  },
  {
    "id": 2,
    "usuarioId": 1,
    "nombre": "Luna",
    "genero": "Hembra",
    "raza": "Siames"
  }
]
```

### `GET /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Obtiene información de una mascota específica.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "nombre": "Toby",
  "genero": "Macho",
  "raza": "Labrador"
}
```

### `POST /usuarios/{usuarioId}/mascotas`
**Descripción:** Añade una nueva mascota a un usuario.

**Ejemplo Request:**
```json
{
  "nombre": "Rocky",
  "genero": "Macho",
  "raza": "Bulldog"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "usuarioId": 1,
  "nombre": "Rocky",
  "genero": "Macho",
  "raza": "Bulldog"
}
```

### `PUT /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Actualiza información de una mascota.

**Ejemplo Request:**
```json
{
  "nombre": "Rocky",
  "genero": "Macho",
  "raza": "Bulldog Francés"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "usuarioId": 1,
  "nombre": "Rocky",
  "genero": "Macho",
  "raza": "Bulldog Francés"
}
```

### `DELETE /usuarios/{usuarioId}/mascotas/{mascotaId}`
**Descripción:** Elimina una mascota de un usuario.

**Ejemplo Response:**
```json
{
  "mensaje": "Mascota eliminada con ID: 3"
}
```

## 4. Perfiles

### `GET /perfiles`
**Descripción:** Obtiene todos los perfiles existentes en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuario_id": 123,
    "descripcion": "Descripción del perfil",
    "fecha_nacimiento": "1990-01-01",
    "img": "url_imagen"
  },
  {
    "id": 2,
    "usuario_id": 124,
    "descripcion": "Amante de los animales",
    "fecha_nacimiento": "1995-05-15",
    "img": "https://ejemplo.com/imagen.jpg"
  }
]
```

### `POST /perfiles`
**Descripción:** Crea un nuevo perfil en el sistema.

**Ejemplo Request:**
```json
{
  "usuario_id": 123,
  "descripcion": "Descripción del perfil",
  "fecha_nacimiento": "1990-01-01",
  "img": "url_imagen"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 123,
  "descripcion": "Descripción del perfil",
  "fecha_nacimiento": "1990-01-01",
  "img": "url_imagen"
}
```

### `GET /usuarios/{id}/perfiles`
**Descripción:** Obtiene el perfil asociado a un usuario específico.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 123,
  "descripcion": "Descripción del perfil",
  "fecha_nacimiento": "1990-01-01",
  "img": "url_imagen"
}
```

### `POST /usuarios/{id}/perfiles`
**Descripción:** Actualiza la información de un perfil existente.

**Ejemplo Request:**
```json
{
  "descripcion": "Nueva descripción del perfil",
  "fecha_nacimiento": "1990-01-01",
  "img": "nueva_url_imagen"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuario_id": 123,
  "descripcion": "Nueva descripción del perfil",
  "fecha_nacimiento": "1990-01-01",
  "img": "nueva_url_imagen"
}
```

### `DELETE /usuarios/{id}/perfiles`
**Descripción:** Elimina un perfil del sistema.

**Ejemplo Response:**
```json
{
  "mensaje": "Perfil eliminado correctamente"
}
```

## 5. Valoraciones

### `GET /valoraciones`
**Descripción:** Obtiene todas las valoraciones registradas.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "autorId": 1,
    "autorNombre": "Juan Pérez",
    "receptorId": 2,
    "receptorNombre": "María López",
    "puntuacion": "5",
    "contenido": "Excelente cuidado de mi mascota",
    "fecha": "2023-10-15T14:30:00"
  }
]
```

### `GET /valoraciones/{id}`
**Descripción:** Obtiene una valoración específica.

**Ejemplo Response:**
```json
{
  "id": 1,
  "autorId": 1,
  "autorNombre": "Juan Pérez",
  "receptorId": 2,
  "receptorNombre": "María López",
  "puntuacion": "5",
  "contenido": "Excelente cuidado de mi mascota",
  "fecha": "2023-10-15T14:30:00"
}
```

### `POST /valoraciones/usuarios/{autorId}/receptor/{receptorId}`
**Descripción:** Crea una nueva valoración de un usuario a otro.

**Ejemplo Request:**
```json
{
  "puntuacion": "4",
  "contenido": "Muy buen trato con mi gato"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "autorId": 2,
  "autorNombre": "María López",
  "receptorId": 1,
  "receptorNombre": "Juan Pérez",
  "puntuacion": "4",
  "contenido": "Muy buen trato con mi gato",
  "fecha": "2023-10-16T10:15:00"
}
```

### `PUT /valoraciones/{id}`
**Descripción:** Actualiza una valoración existente.

**Ejemplo Request:**
```json
{
  "puntuacion": "5",
  "contenido": "Excelente trato con mi gato"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "autorId": 2,
  "autorNombre": "María López",
  "receptorId": 1,
  "receptorNombre": "Juan Pérez",
  "puntuacion": "5",
  "contenido": "Excelente trato con mi gato",
  "fecha": "2023-10-16T10:15:00"
}
```

### `DELETE /valoraciones/{id}`
**Descripción:** Elimina una valoración existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Valoración eliminada correctamente"
}
```

### `GET /valoraciones/usuarios/{receptorId}/recibidas`
**Descripción:** Obtiene todas las valoraciones recibidas por un usuario.

**Ejemplo Response:**
```json
[
  {
    "id": 2,
    "autorId": 2,
    "autorNombre": "María López",
    "receptorId": 1,
    "receptorNombre": "Juan Pérez",
    "puntuacion": "5",
    "contenido": "Excelente trato con mi gato",
    "fecha": "2023-10-16T10:15:00"
  }
]
```

### `GET /valoraciones/usuarios/{autorId}/enviadas`
**Descripción:** Obtiene todas las valoraciones emitidas por un usuario.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "autorId": 1,
    "autorNombre": "Juan Pérez",
    "receptorId": 2,
    "receptorNombre": "María López",
    "puntuacion": "5",
    "contenido": "Excelente cuidado de mi mascota",
    "fecha": "2023-10-15T14:30:00"
  }
]
```

## 6. Eventos

### `GET /eventos`
**Descripción:** Obtiene todos los eventos registrados.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Jornada de adopción",
    "descripcion": "Ven a conocer a tus futuros compañeros",
    "ubicacion": "Parque Central",
    "fecha": "2023-11-20"
  }
]
```

### `GET /eventos/{id}`
**Descripción:** Obtiene información de un evento específico.

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Jornada de adopción",
  "descripcion": "Ven a conocer a tus futuros compañeros",
  "ubicacion": "Parque Central",
  "fecha": "2023-11-20"
}
```

### `POST /eventos`
**Descripción:** Crea un nuevo evento.

**Ejemplo Request:**
```json
{
  "nombre": "Taller de adiestramiento",
  "descripcion": "Aprende técnicas básicas de adiestramiento",
  "ubicacion": "Centro comunitario",
  "fecha": "2023-12-05"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "nombre": "Taller de adiestramiento",
  "descripcion": "Aprende técnicas básicas de adiestramiento",
  "ubicacion": "Centro comunitario",
  "fecha": "2023-12-05"
}
```

### `PUT /eventos/{id}`
**Descripción:** Actualiza información de un evento existente.

**Ejemplo Request:**
```json
{
  "nombre": "Taller de adiestramiento canino",
  "descripcion": "Aprende técnicas básicas y avanzadas de adiestramiento",
  "ubicacion": "Centro comunitario",
  "fecha": "2023-12-05"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "nombre": "Taller de adiestramiento canino",
  "descripcion": "Aprende técnicas básicas y avanzadas de adiestramiento",
  "ubicacion": "Centro comunitario",
  "fecha": "2023-12-05"
}
```

### `DELETE /eventos/{id}`
**Descripción:** Elimina un evento existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Evento eliminado con ID: 2"
}
```

## 7. Grupos

### `GET /grupos`
**Descripción:** Obtiene todos los grupos existentes.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "nombre": "Amantes de los perros",
    "descripcion": "Grupo para compartir experiencias con perros"
  }
]
```

### `GET /grupos/{id}`
**Descripción:** Obtiene información de un grupo específico.

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "Amantes de los perros",
  "descripcion": "Grupo para compartir experiencias con perros"
}
```

### `POST /grupos?usuarioId={usuarioId}`
**Descripción:** Crea un nuevo grupo y asigna al usuario como administrador.

**Ejemplo Request:**
```json
{
  "nombre": "Gatos en adopción",
  "descripcion": "Grupo para conectar gatos con familias adoptivas"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "nombre": "Gatos en adopción",
  "descripcion": "Grupo para conectar gatos con familias adoptivas"
}
```

### `PUT /grupos/{id}`
**Descripción:** Actualiza información de un grupo existente.

**Ejemplo Request:**
```json
{
  "nombre": "Gatos para adopción",
  "descripcion": "Grupo para conectar gatos con familias adoptivas en Barcelona"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "nombre": "Gatos para adopción",
  "descripcion": "Grupo para conectar gatos con familias adoptivas en Barcelona"
}
```

### `DELETE /grupos/{id}`
**Descripción:** Elimina un grupo existente.

**Ejemplo Response:**
```json
"Grupo eliminado correctamente"
```

## 8. Seguidos

### `GET /seguidos`
**Descripción:** Obtiene todas las relaciones de seguimiento entre usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "seguidorId": 1,
    "seguidorNombre": "Juan Pérez",
    "seguidoId": 2,
    "seguidoNombre": "María López"
  }
]
```

### `POST /seguidos`
**Descripción:** Crea una nueva relación de seguimiento entre usuarios.

**Ejemplo Request:**
```json
{
  "seguidorId": 2,
  "seguidoId": 3
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "seguidorId": 2,
  "seguidorNombre": "María López",
  "seguidoId": 3,
  "seguidoNombre": "Carlos Gómez"
}
```

### `GET /seguidos/usuario/{usuarioId}/seguidos`
**Descripción:** Obtiene todos los usuarios seguidos por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "seguidorId": 1,
    "seguidorNombre": "Juan Pérez",
    "seguidoId": 2,
    "seguidoNombre": "María López"
  }
]
```

### `GET /seguidos/usuario/{usuarioId}/seguidores`
**Descripción:** Obtiene todos los seguidores de un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 2,
    "seguidorId": 2,
    "seguidorNombre": "María López",
    "seguidoId": 1,
    "seguidoNombre": "Juan Pérez"
  }
]
```

### `DELETE /seguidos/{id}`
**Descripción:** Elimina una relación de seguimiento entre usuarios.

**Ejemplo Response:**
```json
{
  "mensaje": "Relación de seguimiento eliminada correctamente"
}
```

## Consideraciones generales

- Todas las solicitudes que modifican datos (POST, PUT, DELETE) requieren autenticación.
- El token JWT debe incluirse en el header Authorization como "Bearer {token}".
- Los errores devuelven un objeto JSON con una propiedad "error" que describe el problema.
- Las fechas se envían en formato ISO 8601 (YYYY-MM-DDTHH:MM:SS) o simplemente YYYY-MM-DD para fechas sin hora.
- Los IDs de recursos son números enteros (Long).

## 9. Notificaciones

### `GET /notificaciones`
**Descripción:** Obtiene todas las notificaciones registradas en el sistema.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "fecha": "2023-10-15T14:30:00"
  },
  {
    "id": 2,
    "fecha": "2023-10-16T10:15:00"
  }
]
```

### `GET /notificaciones/{id}`
**Descripción:** Obtiene una notificación específica por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "fecha": "2023-10-15T14:30:00"
}
```

### `POST /notificaciones`
**Descripción:** Crea una nueva notificación en el sistema.

**Ejemplo Request:**
```json
{
  "fecha": "2023-10-17T09:00:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "fecha": "2023-10-17T09:00:00"
}
```

### `PUT /notificaciones/{id}`
**Descripción:** Actualiza una notificación existente.

**Ejemplo Request:**
```json
{
  "fecha": "2023-10-17T10:00:00"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "fecha": "2023-10-17T10:00:00"
}
```

### `DELETE /notificaciones/{id}`
**Descripción:** Elimina una notificación existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Notificación eliminada correctamente"
}
```

## 10. Relaciones Usuario-Grupo

### `GET /usuario-grupo`
**Descripción:** Obtiene todas las relaciones entre usuarios y grupos.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "grupoId": 1,
    "grupoNombre": "Amantes de los perros",
    "rol": "Admin"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "usuarioNombre": "María López",
    "grupoId": 1,
    "grupoNombre": "Amantes de los perros",
    "rol": "Miembro"
  }
]
```

### `GET /usuario-grupo/{id}`
**Descripción:** Obtiene una relación específica entre usuario y grupo por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "usuarioNombre": "Juan Pérez",
  "grupoId": 1,
  "grupoNombre": "Amantes de los perros",
  "rol": "Admin"
}
```

### `POST /usuario-grupo`
**Descripción:** Crea una nueva relación entre un usuario y un grupo.

**Ejemplo Request:**
```json
{
  "usuarioId": 3,
  "grupoId": 1,
  "rol": "Miembro"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "usuarioId": 3,
  "usuarioNombre": "Carlos Gómez",
  "grupoId": 1,
  "grupoNombre": "Amantes de los perros",
  "rol": "Miembro"
}
```

### `PUT /usuario-grupo/{id}`
**Descripción:** Actualiza una relación existente entre usuario y grupo.

**Ejemplo Request:**
```json
{
  "usuarioId": 3,
  "grupoId": 1,
  "rol": "Moderador"
}
```

**Ejemplo Response:**
```json
{
  "id": 3,
  "usuarioId": 3,
  "usuarioNombre": "Carlos Gómez",
  "grupoId": 1,
  "grupoNombre": "Amantes de los perros",
  "rol": "Moderador"
}
```

### `DELETE /usuario-grupo/{id}`
**Descripción:** Elimina una relación existente entre usuario y grupo.

**Ejemplo Response:**
```json
"Eliminado correctamente"
```

### `GET /usuario-grupo/usuario/{usuario_id}`
**Descripción:** Obtiene todos los grupos a los que pertenece un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "grupoId": 1,
    "grupoNombre": "Amantes de los perros",
    "rol": "Admin"
  },
  {
    "id": 4,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "grupoId": 2,
    "grupoNombre": "Gatos para adopción",
    "rol": "Miembro"
  }
]
```

### `GET /usuario-grupo/grupo/{grupo_id}`
**Descripción:** Obtiene todos los usuarios que pertenecen a un grupo específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "grupoId": 1,
    "grupoNombre": "Amantes de los perros",
    "rol": "Admin"
  },
  {
    "id": 2,
    "usuarioId": 2,
    "usuarioNombre": "María López",
    "grupoId": 1,
    "grupoNombre": "Amantes de los perros",
    "rol": "Miembro"
  }
]
```

### `GET /usuario-grupo/usuario-grupo?usuario_id={usuarioId}&grupo_id={grupoId}`
**Descripción:** Obtiene una relación específica entre un usuario y un grupo por sus IDs.

**Ejemplo Response:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "usuarioNombre": "Juan Pérez",
  "grupoId": 1,
  "grupoNombre": "Amantes de los perros",
  "rol": "Admin"
}
```

## 11. Chat

### `POST /chat/enviar`
**Descripción:** Envía un nuevo mensaje de un usuario a otro.

**Ejemplo Request:**
```json
{
  "emisorId": 1,
  "receptorId": 2,
  "contenido": "Hola, ¿cómo estás?",
  "visto": false
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "emisorId": 1,
  "emisorNombre": "Juan Pérez",
  "receptorId": 2,
  "receptorNombre": "María López",
  "contenido": "Hola, ¿cómo estás?",
  "fechaHora": "2023-10-15T10:30:00",
  "visto": false
}
```

### `GET /chat/conversacion/{usuario1Id}/{usuario2Id}`
**Descripción:** Obtiene la conversación completa entre dos usuarios.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "emisorId": 1,
    "emisorNombre": "Juan Pérez",
    "receptorId": 2,
    "receptorNombre": "María López",
    "contenido": "Hola, ¿cómo estás?",
    "fechaHora": "2023-10-15T10:30:00",
    "visto": true
  },
  {
    "id": 2,
    "emisorId": 2,
    "emisorNombre": "María López",
    "receptorId": 1,
    "receptorNombre": "Juan Pérez",
    "contenido": "Muy bien, ¿y tú?",
    "fechaHora": "2023-10-15T10:35:00",
    "visto": false
  }
]
```

### `PUT /chat/marcar-vistos/{usuarioId}/{otroUsuarioId}`
**Descripción:** Marca como vistos todos los mensajes enviados de un usuario a otro.

**Ejemplo Response:**
```json
{
  "message": "Mensajes marcados como vistos"
}
```

### `GET /chat/no-vistos/{usuarioId}`
**Descripción:** Obtiene todos los mensajes no vistos por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 3,
    "emisorId": 2,
    "emisorNombre": "María López",
    "receptorId": 1,
    "receptorNombre": "Juan Pérez",
    "contenido": "¿Nos vemos mañana?",
    "fechaHora": "2023-10-15T10:40:00",
    "visto": false
  }
]
```

### `GET /chat/enviados/{usuarioId}`
**Descripción:** Obtiene todos los mensajes enviados por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "emisorId": 1,
    "emisorNombre": "Juan Pérez",
    "receptorId": 2,
    "receptorNombre": "María López",
    "contenido": "Hola, ¿cómo estás?",
    "fechaHora": "2023-10-15T10:30:00",
    "visto": true
  }
]
```

### `GET /chat/recibidos/{usuarioId}`
**Descripción:** Obtiene todos los mensajes recibidos por un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 2,
    "emisorId": 2,
    "emisorNombre": "María López",
    "receptorId": 1,
    "receptorNombre": "Juan Pérez",
    "contenido": "Muy bien, ¿y tú?",
    "fechaHora": "2023-10-15T10:35:00",
    "visto": false
  },
  {
    "id": 3,
    "emisorId": 2,
    "emisorNombre": "María López",
    "receptorId": 1,
    "receptorNombre": "Juan Pérez",
    "contenido": "¿Nos vemos mañana?",
    "fechaHora": "2023-10-15T10:40:00",
    "visto": false
  }
]
```

### `DELETE /chat/eliminar/{usuario1Id}/{usuario2Id}`
**Descripción:** Elimina toda la conversación entre dos usuarios.

**Ejemplo Response:**
```json
{
  "message": "Conversación eliminada"
}
```

## 12. Posts

### `GET /posts`
**Descripción:** Obtiene todas las publicaciones con posibilidad de filtrar por contenido o fechas.

**Parámetros opcionales:**
- `contenido`: Texto para filtrar publicaciones por contenido
- `fechaInicio`: Fecha inicial para filtrar (formato ISO)
- `fechaFin`: Fecha final para filtrar (formato ISO)

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "Mi perro aprendió un nuevo truco",
    "fecha": "2023-10-10T14:30:00",
    "img": "https://ejemplo.com/imagen1.jpg",
    "comentarios": [
      {
        "id": 1,
        "creadorId": 2,
        "nombreCreador": "María",
        "apellidoCreador": "López",
        "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
        "fecha": "2023-10-10T15:00:00"
      }
    ]
  }
]
```

### `POST /posts`
**Descripción:** Crea una nueva publicación.

**Ejemplo Request:**
```json
{
  "grupoId": 1,
  "creadorId": 1,
  "contenido": "Hoy fuimos al parque con mi mascota",
  "img": "https://ejemplo.com/imagen2.jpg"
}
```

**Ejemplo Response:**
```json
{
  "id": 2,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los perros",
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "Hoy fuimos al parque con mi mascota",
  "fecha": "2023-10-15T09:30:00",
  "img": "https://ejemplo.com/imagen2.jpg",
  "comentarios": []
}
```

### `GET /posts/{id}`
**Descripción:** Obtiene una publicación específica por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los perros",
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "Mi perro aprendió un nuevo truco",
  "fecha": "2023-10-10T14:30:00",
  "img": "https://ejemplo.com/imagen1.jpg",
  "comentarios": [
    {
      "id": 1,
      "creadorId": 2,
      "nombreCreador": "María",
      "apellidoCreador": "López",
      "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
      "fecha": "2023-10-10T15:00:00"
    }
  ]
}
```

### `PUT /posts/{id}`
**Descripción:** Actualiza una publicación existente.

**Ejemplo Request:**
```json
{
  "contenido": "Mi perro aprendió a dar la pata, estoy muy orgulloso",
  "img": "https://ejemplo.com/imagen_actualizada.jpg"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "grupoId": 1,
  "nombreGrupo": "Amantes de los perros",
  "creadorId": 1,
  "nombreCreador": "Juan",
  "apellidoCreador": "Pérez",
  "contenido": "Mi perro aprendió a dar la pata, estoy muy orgulloso",
  "fecha": "2023-10-10T14:30:00",
  "img": "https://ejemplo.com/imagen_actualizada.jpg",
  "comentarios": [
    {
      "id": 1,
      "creadorId": 2,
      "nombreCreador": "María",
      "apellidoCreador": "López",
      "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
      "fecha": "2023-10-10T15:00:00"
    }
  ]
}
```

### `DELETE /posts/{id}`
**Descripción:** Elimina una publicación existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Post eliminado correctamente"
}
```

### `GET /posts/usuarios/{userId}/posts`
**Descripción:** Obtiene todas las publicaciones de un usuario específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "Mi perro aprendió a dar la pata, estoy muy orgulloso",
    "fecha": "2023-10-10T14:30:00",
    "img": "https://ejemplo.com/imagen_actualizada.jpg",
    "comentarios": [
      {
        "id": 1,
        "creadorId": 2,
        "nombreCreador": "María",
        "apellidoCreador": "López",
        "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
        "fecha": "2023-10-10T15:00:00"
      }
    ]
  },
  {
    "id": 2,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "Hoy fuimos al parque con mi mascota",
    "fecha": "2023-10-15T09:30:00",
    "img": "https://ejemplo.com/imagen2.jpg",
    "comentarios": []
  }
]
```

### `GET /posts/grupos/{grupoId}/posts`
**Descripción:** Obtiene todas las publicaciones de un grupo específico.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "grupoId": 1,
    "nombreGrupo": "Amantes de los perros",
    "creadorId": 1,
    "nombreCreador": "Juan",
    "apellidoCreador": "Pérez",
    "contenido": "Mi perro aprendió a dar la pata, estoy muy orgulloso",
    "fecha": "2023-10-10T14:30:00",
    "img": "https://ejemplo.com/imagen_actualizada.jpg",
    "comentarios": [
      {
        "id": 1,
        "creadorId": 2,
        "nombreCreador": "María",
        "apellidoCreador": "López",
        "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
        "fecha": "2023-10-10T15:00:00"
      }
    ]
  }
]
```

## 13. Comentarios

### `GET /posts/{postId}/comentarios`
**Descripción:** Obtiene todos los comentarios de una publicación específica.

**Ejemplo Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "creadorId": 2,
    "nombreCreador": "María",
    "apellidoCreador": "López",
    "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
    "fecha": "2023-10-10T15:00:00"
  },
  {
    "id": 2,
    "postId": 1,
    "creadorId": 3,
    "nombreCreador": "Carlos",
    "apellidoCreador": "Gómez",
    "contenido": "¡Increíble! Mi perro también aprendió ese truco hace poco",
    "fecha": "2023-10-10T16:20:00"
  }
]
```

### `POST /posts/{postId}/comentarios`
**Descripción:** Crea un nuevo comentario en una publicación específica.

**Ejemplo Request:**
```json
{
  "creadorId": 2,
  "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
  "img": "https://ejemplo.com/imagen_comentario.jpg"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 2,
  "nombreCreador": "María",
  "apellidoCreador": "López",
  "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
  "fecha": "2023-10-10T15:00:00",
  "img": "https://ejemplo.com/imagen_comentario.jpg"
}
```

### `GET /comentarios/{id}`
**Descripción:** Obtiene un comentario específico por su ID.

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 2,
  "nombreCreador": "María",
  "apellidoCreador": "López",
  "contenido": "¡Qué lindo! ¿Qué truco aprendió?",
  "fecha": "2023-10-10T15:00:00",
  "img": "https://ejemplo.com/imagen_comentario.jpg"
}
```

### `PUT /comentarios/{id}`
**Descripción:** Actualiza un comentario existente.

**Ejemplo Request:**
```json
{
  "contenido": "¡Qué lindo! Me encantaría ver un video de ese truco"
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "postId": 1,
  "creadorId": 2,
  "nombreCreador": "María",
  "apellidoCreador": "López",
  "contenido": "¡Qué lindo! Me encantaría ver un video de ese truco",
  "fecha": "2023-10-10T15:00:00",
  "img": "https://ejemplo.com/imagen_comentario.jpg"
}
```

### `DELETE /comentarios/{id}`
**Descripción:** Elimina un comentario existente.

**Ejemplo Response:**
```json
{
  "mensaje": "Comentario eliminado correctamente"
}
```