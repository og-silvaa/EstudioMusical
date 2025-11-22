package com.estudiomusical.backend.config;

import com.estudiomusical.backend.model.Equipment;
import com.estudiomusical.backend.model.Room;
import com.estudiomusical.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final RoomRepository roomRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (roomRepository.count() == 0) {
                log.info("Cargando datos de ejemplo...");

                Room sala1 = new Room();
                sala1.setName("Sala Rock");
                sala1.setDescription("Sala amplia ideal para bandas de rock");
                sala1.setCapacity(5);
                sala1.setPricePerHour(25.0);
                sala1.setAvailableInstruments(Arrays.asList(
                    new Equipment("Batería", 1),
                    new Equipment("Amplificador Guitarra", 2),
                    new Equipment("Amplificador Bajo", 1)
                ));
                sala1.setAvailableMicrophones(Arrays.asList(
                    new Equipment("Shure SM58", 3),
                    new Equipment("Shure SM57", 2)
                ));
                sala1.setAvailable(true);

                Room sala2 = new Room();
                sala2.setName("Sala Acústica");
                sala2.setDescription("Sala pequeña perfecta para ensayos acústicos");
                sala2.setCapacity(3);
                sala2.setPricePerHour(15.0);
                sala2.setAvailableInstruments(Arrays.asList(
                    new Equipment("Piano", 1),
                    new Equipment("Guitarra Acústica", 2)
                ));
                sala2.setAvailableMicrophones(Arrays.asList(
                    new Equipment("AKG C414", 2)
                ));
                sala2.setAvailable(true);

                Room sala3 = new Room();
                sala3.setName("Sala Premium");
                sala3.setDescription("Sala profesional con equipo de alta gama");
                sala3.setCapacity(8);
                sala3.setPricePerHour(50.0);
                sala3.setAvailableInstruments(Arrays.asList(
                    new Equipment("Batería Pearl", 1),
                    new Equipment("Amplificador Marshall", 2),
                    new Equipment("Amplificador Fender", 2),
                    new Equipment("Teclado Yamaha", 1)
                ));
                sala3.setAvailableMicrophones(Arrays.asList(
                    new Equipment("Shure SM7B", 2),
                    new Equipment("Neumann U87", 1),
                    new Equipment("Shure SM58", 4)
                ));
                sala3.setAvailable(true);

                Room sala4 = new Room();
                sala4.setName("Sala Pequeña");
                sala4.setDescription("Ideal para práctica individual o dúos");
                sala4.setCapacity(2);
                sala4.setPricePerHour(10.0);
                sala4.setAvailableInstruments(Arrays.asList(
                    new Equipment("Amplificador pequeño", 1)
                ));
                sala4.setAvailableMicrophones(Arrays.asList(
                    new Equipment("Shure SM58", 1)
                ));
                sala4.setAvailable(true);

                List<Room> rooms = Arrays.asList(sala1, sala2, sala3, sala4);
                roomRepository.saveAll(rooms);

                log.info("Datos de ejemplo cargados: {} salas", rooms.size());
            } else {
                log.info("Datos ya existen en la base de datos, omitiendo carga inicial");
            }
        };
    }
}
