import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'

const API_URL = 'http://localhost:8080/api'

function App() {
  const [rooms, setRooms] = useState([])
  const [reservations, setReservations] = useState([])
  const [selectedRoom, setSelectedRoom] = useState(null)
  const [showReservationForm, setShowReservationForm] = useState(false)
  
  const [formData, setFormData] = useState({
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    startTime: '',
    endTime: '',
    requestedItems: []
  })

  useEffect(() => {
    fetchRooms()
    fetchReservations()
  }, [])

  const fetchRooms = async () => {
    try {
      const response = await axios.get(`${API_URL}/rooms`)
      setRooms(response.data)
    } catch (error) {
      console.error('Error fetching rooms:', error)
    }
  }

  const fetchReservations = async () => {
    try {
      const response = await axios.get(`${API_URL}/reservations`)
      setReservations(response.data)
    } catch (error) {
      console.error('Error fetching reservations:', error)
    }
  }

  const handleReserveRoom = (room) => {
    setSelectedRoom(room)
    setShowReservationForm(true)
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmitReservation = async (e) => {
    e.preventDefault()
    
    try {
      const reservationData = {
        roomId: selectedRoom.id,
        ...formData,
        requestedItems: []
      }
      
      await axios.post(`${API_URL}/reservations`, reservationData)
      alert('Reserva creada exitosamente!')
      setShowReservationForm(false)
      setFormData({
        customerName: '',
        customerEmail: '',
        customerPhone: '',
        startTime: '',
        endTime: '',
        requestedItems: []
      })
      fetchReservations()
    } catch (error) {
      alert('Error al crear la reserva: ' + (error.response?.data?.error || error.message))
    }
  }

  return (
    <div className="container">
      <header>
        <h1>🎵 Estudio Musical</h1>
        <p>Sistema de Reservas de Salas de Ensayo</p>
      </header>

      <main>
        <section className="rooms-section">
          <h2>Salas Disponibles</h2>
          <div className="rooms-grid">
            {rooms.map(room => (
              <div key={room.id} className="room-card">
                <h3>{room.name}</h3>
                <p>{room.description}</p>
                <div className="room-details">
                  <p><strong>Capacidad:</strong> {room.capacity} personas</p>
                  <p><strong>Precio:</strong> ${room.pricePerHour}/hora</p>
                  <p><strong>Estado:</strong> {room.available ? '✅ Disponible' : '❌ No disponible'}</p>
                </div>
                {room.availableInstruments && room.availableInstruments.length > 0 && (
                  <div className="room-instruments">
                    <strong>Instrumentos:</strong> {room.availableInstruments.join(', ')}
                  </div>
                )}
                {room.availableMicrophones && room.availableMicrophones.length > 0 && (
                  <div className="room-microphones">
                    <strong>Micrófonos:</strong> {room.availableMicrophones.join(', ')}
                  </div>
                )}
                <button 
                  onClick={() => handleReserveRoom(room)}
                  disabled={!room.available}
                  className="btn-reserve"
                >
                  Reservar
                </button>
              </div>
            ))}
          </div>
        </section>

        {showReservationForm && (
          <section className="reservation-form-section">
            <h2>Reservar: {selectedRoom?.name}</h2>
            <form onSubmit={handleSubmitReservation}>
              <div className="form-group">
                <label>Nombre:</label>
                <input
                  type="text"
                  name="customerName"
                  value={formData.customerName}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="form-group">
                <label>Email:</label>
                <input
                  type="email"
                  name="customerEmail"
                  value={formData.customerEmail}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="form-group">
                <label>Teléfono:</label>
                <input
                  type="tel"
                  name="customerPhone"
                  value={formData.customerPhone}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="form-group">
                <label>Fecha y Hora de Inicio:</label>
                <input
                  type="datetime-local"
                  name="startTime"
                  value={formData.startTime}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="form-group">
                <label>Fecha y Hora de Fin:</label>
                <input
                  type="datetime-local"
                  name="endTime"
                  value={formData.endTime}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="form-actions">
                <button type="submit" className="btn-submit">Crear Reserva</button>
                <button type="button" onClick={() => setShowReservationForm(false)} className="btn-cancel">
                  Cancelar
                </button>
              </div>
            </form>
          </section>
        )}

        <section className="reservations-section">
          <h2>Mis Reservas</h2>
          <div className="reservations-list">
            {reservations.length === 0 ? (
              <p>No hay reservas registradas</p>
            ) : (
              reservations.map(reservation => (
                <div key={reservation.id} className="reservation-card">
                  <h3>Reserva #{reservation.id}</h3>
                  <p><strong>Cliente:</strong> {reservation.customerName}</p>
                  <p><strong>Email:</strong> {reservation.customerEmail}</p>
                  <p><strong>Inicio:</strong> {new Date(reservation.startTime).toLocaleString()}</p>
                  <p><strong>Fin:</strong> {new Date(reservation.endTime).toLocaleString()}</p>
                  <p><strong>Costo Estimado:</strong> ${reservation.estimatedCost}</p>
                  <p><strong>Estado de Pago:</strong> {reservation.paymentStatus}</p>
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  )
}

export default App
