# EstudioMusical

Sistema de reservas de salas de ensayo musical con gestión de instrumentos, micrófonos, estimación de costos y control de pagos.

## 🎯 Características

- **Gestión de Salas**: Administración de salas de ensayo con capacidad, precios e inventario
- **Reservas**: Sistema completo de reservas con validación de disponibilidad
- **Instrumentos y Micrófonos**: Solicitud de equipamiento por reserva
- **Estimación de Costos**: Cálculo automático basado en tiempo de uso
- **Control de Pagos**: Seguimiento del estado de pago de las reservas
- **Disponibilidad**: Verificación en tiempo real de la disponibilidad de salas

## 🏗️ Arquitectura

Este proyecto está estructurado como un proyecto Maven multi-módulo:

### Backend (Spring Boot + MongoDB)
- **Framework**: Spring Boot 3.2.0
- **Base de Datos**: MongoDB
- **API**: REST API con endpoints para salas y reservas
- **Puerto**: 8080

### Frontend (Vite + React)
- **Framework**: React con Vite
- **HTTP Client**: Axios
- **Puerto de desarrollo**: 5173

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- Node.js 18 o superior
- MongoDB 4.4 o superior (o Docker para ejecutar MongoDB en contenedor)

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/og-silvaa/EstudioMusical.git
cd EstudioMusical
```

### 2. Iniciar MongoDB

#### Opción A: Con Docker (Recomendado)

```bash
docker-compose up -d
```

#### Opción B: MongoDB Local

Asegúrate de tener MongoDB corriendo en `localhost:27017`:

```bash
mongod
```
```

### 3. Construir el proyecto completo

```bash
mvn clean install
```

### 4. Ejecutar el Backend

```bash
cd backend
mvn spring-boot:run
```

El backend estará disponible en: http://localhost:8080

**Nota:** El backend carga automáticamente datos de ejemplo (4 salas) al iniciar por primera vez con la base de datos vacía.

### 5. Ejecutar el Frontend (en otra terminal)

```bash
cd frontend
npm run dev
```

El frontend estará disponible en: http://localhost:5173

## 📡 API Endpoints

### Salas (Rooms)

- `GET /api/rooms` - Obtener todas las salas
- `GET /api/rooms/available` - Obtener salas disponibles
- `GET /api/rooms/{id}` - Obtener sala por ID
- `POST /api/rooms` - Crear nueva sala
- `PUT /api/rooms/{id}` - Actualizar sala
- `DELETE /api/rooms/{id}` - Eliminar sala

### Reservas (Reservations)

- `GET /api/reservations` - Obtener todas las reservas
- `GET /api/reservations/{id}` - Obtener reserva por ID
- `GET /api/reservations/customer/{email}` - Obtener reservas por cliente
- `GET /api/reservations/room/{roomId}` - Obtener reservas por sala
- `GET /api/reservations/check-availability` - Verificar disponibilidad
- `GET /api/reservations/estimate-cost` - Estimar costo
- `POST /api/reservations` - Crear nueva reserva
- `PUT /api/reservations/{id}` - Actualizar reserva
- `PATCH /api/reservations/{id}/payment-status` - Actualizar estado de pago
- `DELETE /api/reservations/{id}` - Eliminar reserva

## 📊 Modelo de Datos

### Room (Sala)
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "capacity": "integer",
  "pricePerHour": "double",
  "availableInstruments": ["string"],
  "availableMicrophones": ["string"],
  "available": "boolean"
}
```

### Reservation (Reserva)
```json
{
  "id": "string",
  "roomId": "string",
  "customerName": "string",
  "customerEmail": "string",
  "customerPhone": "string",
  "startTime": "datetime",
  "endTime": "datetime",
  "requestedItems": [{
    "name": "string",
    "quantity": "integer",
    "type": "string"
  }],
  "estimatedCost": "double",
  "paymentStatus": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

## 🧪 Testing

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 🔧 Configuración

### Backend (`backend/src/main/resources/application.properties`)

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/estudiomusical
server.port=8080
```

### Frontend (`frontend/src/App.jsx`)

```javascript
const API_URL = 'http://localhost:8080/api'
```

## 📝 Licencia

Este proyecto está bajo la licencia MIT.

## 👥 Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o pull request para sugerencias o mejoras.
