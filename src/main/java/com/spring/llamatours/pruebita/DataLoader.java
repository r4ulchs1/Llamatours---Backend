package com.spring.llamatours.pruebita; // Asegúrate de que este paquete coincida con la ubicación de tu archivo

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.DTOs.UsuarioDTO;
import com.spring.llamatours.DTOs.ReservacionDTO;
import com.spring.llamatours.DTOs.PagoDTO;

// IMPORTACIONES DE ENUMS - VERIFICA QUE ESTAS RUTAS Y NOMBRES EXISTAN EN TU PROYECTO
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.enums.EstadoPago;
import com.spring.llamatours.enums.EstadoReserva;
import com.spring.llamatours.enums.MetodoPago;

import com.spring.llamatours.services.DestinoService;
import com.spring.llamatours.services.UsuarioService;
import com.spring.llamatours.services.ReservacionService;
import com.spring.llamatours.services.PagoService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component // Esta anotación le dice a Spring que gestione este componente y lo ejecute
@RequiredArgsConstructor // Genera un constructor con todos los campos 'final' para inyección de dependencias
@Slf4j // Anotación de Lombok para habilitar el logging (logger.info, logger.warn)
public class DataLoader implements CommandLineRunner {

    // Inyección de los servicios necesarios para guardar los DTOs
    private final DestinoService destinoService;
    private final UsuarioService usuarioService;
    private final ReservacionService reservacionService;
    private final PagoService pagoService;

    // Este método se ejecuta automáticamente cuando la aplicación Spring Boot se inicia
    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando la carga de datos de prueba en la base de datos H2...");

        // --- 1. Crear Usuarios de Prueba ---
        // Ajusta los valores de 'rol' si tus roles de usuario son diferentes (ej. "CUSTOMER" en lugar de "CLIENTE")
        UsuarioDTO user1 = new UsuarioDTO(null, "Juan Pérez", "juan.perez@example.com", "password123", "987654321", "CLIENTE", LocalDateTime.now());
        UsuarioDTO user2 = new UsuarioDTO(null, "María García", "maria.garcia@example.com", "pass456", "912345678", "CLIENTE", LocalDateTime.now());
        UsuarioDTO admin1 = new UsuarioDTO(null, "Admin Llama", "admin@llamatours.com", "adminpass", "900000000", "ADMIN", LocalDateTime.now());

        UsuarioDTO savedUser1 = null;
        UsuarioDTO savedUser2 = null;
        UsuarioDTO savedAdmin1 = null;

        // Intentar guardar usuarios, y si ya existen (ej. por email único), intentar recuperarlos
        try {
            savedUser1 = usuarioService.saveUsuario(user1);
            log.info("Usuario de prueba cargado: {}", savedUser1.getNombre());
        } catch (Exception e) {
            log.warn("Error o usuario Juan Pérez ya existe, intentando recuperarlo: {}", e.getMessage());
        }
        try {
            savedUser2 = usuarioService.saveUsuario(user2);
            log.info("Usuario de prueba cargado: {}", savedUser2.getNombre());
        } catch (Exception e) {
            log.warn("Error o usuario María García ya existe, intentando recuperarlo: {}", e.getMessage());
        }
        try {
            savedAdmin1 = usuarioService.saveUsuario(admin1);
            log.info("Usuario de prueba cargado: {}", savedAdmin1.getNombre());
        } catch (Exception e) {
            log.warn("Error o usuario Admin Llama ya existe, intentando recuperarlo: {}", e.getMessage());
        }
        log.info("Usuarios de prueba procesados.");


        // --- 2. Crear Destinos de Prueba ---
        // Utilizamos DepartamentoNombre.XYZ.name() para obtener el String que tu DTO espera.
        // Las URLs de imagen son placeholders; puedes reemplazarlas con URLs reales si lo deseas.
        DestinoDTO destino1 = new DestinoDTO(
            null,
            "Aventura en el Cañón del Colca",
            "Explora el impresionante Cañón del Colca y observa el vuelo del cóndor. Un viaje inolvidable por la naturaleza andina.",
            "Trekking por el cañón, Observación de cóndores, Visita a pueblos tradicionales, Baños termales de La Calera.",
            new BigDecimal("250.00"),
            LocalDate.now().plusDays(10),
            LocalDate.now().plusDays(13),
            3,
            20,
            DepartamentoNombre.AREQUIPA.name(),
            "Caylloma",
            "https://placehold.co/400x250/E57373/FFFFFF?text=Colca1", // [Image of Colca Canyon 1]
            "https://placehold.co/400x250/FFB74D/FFFFFF?text=Colca2", // [Image of Colca Canyon 2]
            "", ""
        );

        DestinoDTO destino2 = new DestinoDTO(
            null,
            "Tour Místico a Machu Picchu",
            "Descubre la maravilla ancestral de Machu Picchu y el enigmático Valle Sagrado de los Incas. Una experiencia cultural profunda.",
            "Visita guiada a Machu Picchu, Recorrido por Ollantaytambo y Pisac, Caminata a la Montaña Huayna Picchu (opcional), Ceremonia de ofrenda a la Pachamama.",
            new BigDecimal("750.50"),
            LocalDate.now().plusMonths(1),
            LocalDate.now().plusMonths(1).plusDays(5),
            5,
            15,
            DepartamentoNombre.CUSCO.name(),
            "Urubamba",
            "https://placehold.co/400x250/81C784/FFFFFF?text=MachuPicchu1", // [Image of Machu Picchu 1]
            "https://placehold.co/400x250/4DD0E1/FFFFFF?text=MachuPicchu2", // [Image of Machu Picchu 2]
            "", ""
        );

        DestinoDTO destino3 = new DestinoDTO(
            null,
            "Ruta del Pisco en Ica",
            "Recorre las bodegas vitivinícolas más importantes de la región y disfruta de la gastronomía local y los paisajes desérticos.",
            "Degustación de pisco y vinos, Visita al oasis de Huacachina, Paseo en tubulares y sandboarding en las dunas, Cena campestre.",
            new BigDecimal("180.00"),
            LocalDate.now().plusWeeks(3),
            LocalDate.now().plusWeeks(3).plusDays(2),
            2,
            25,
            DepartamentoNombre.ICA.name(),
            "Ica",
            "https://placehold.co/400x250/A1887F/FFFFFF?text=Pisco1", // [Image of Pisco Route 1]
            "", "", ""
        );

        DestinoDTO savedDestino1 = null;
        DestinoDTO savedDestino2 = null;
        DestinoDTO savedDestino3 = null;

        // Guardar destinos. Si tu servicio tiene validación de unicidad (ej. por título),
        // y el destino ya existe, el save fallará. Considera añadir un findByTitulo en tu servicio.
        try {
            savedDestino1 = destinoService.saveDestino(destino1);
            log.info("Destino de prueba cargado: {}", savedDestino1.getTitulo());
        } catch (Exception e) {
            log.warn("Error al guardar destino 1 (podría ser duplicado o validación): {}", e.getMessage());
        }
        try {
            savedDestino2 = destinoService.saveDestino(destino2);
            log.info("Destino de prueba cargado: {}", savedDestino2.getTitulo());
        } catch (Exception e) {
            log.warn("Error al guardar destino 2 (podría ser duplicado o validación): {}", e.getMessage());
        }
        try {
            savedDestino3 = destinoService.saveDestino(destino3);
            log.info("Destino de prueba cargado: {}", savedDestino3.getTitulo());
        } catch (Exception e) {
            log.warn("Error al guardar destino 3 (podría ser duplicado o validación): {}", e.getMessage());
        }
        log.info("Destinos de prueba procesados.");

        // --- 3. Crear Reservaciones de Prueba (requiere que usuarios y destinos existan y hayan sido guardados) ---
        // Usamos EstadoReserva.PENDIENTE.name() para obtener el String que tu DTO espera.
        if (savedUser1 != null && savedDestino1 != null) {
            ReservacionDTO reserva1 = new ReservacionDTO(
                null,
                savedUser1.getId(),
                savedDestino1.getId(),
                LocalDate.now().plusDays(5),
                2,
                "Juan Pérez, Ana Díaz",
                EstadoReserva.PENDIENTE.name()
            );
            ReservacionDTO savedReserva1 = null;
            try {
                savedReserva1 = reservacionService.saveReservacion(reserva1);
                log.info("Reservación de prueba 1 cargada para usuario {} en destino {}", savedUser1.getNombre(), savedDestino1.getTitulo());

                // --- 4. Crear Pago de Prueba para la Reservación 1 ---
                // NOTA IMPORTANTE: El 'monto' en PagoDTO es solo un valor para el DTO.
                // Tu PagoService.savePago() debe contener la lógica para calcular el monto real
                // basado en la reservación y el destino asociado. Este valor aquí será sobrescrito.
                PagoDTO pago1 = new PagoDTO(
                    null,
                    savedReserva1.getId(),
                    new BigDecimal("0.00"), // El monto se calculará en el servicio, aquí es un placeholder
                    MetodoPago.TARJETA.name(),
                    EstadoPago.COMPLETADO.name(),
                    LocalDate.now()
                );
                pagoService.savePago(pago1);
                log.info("Pago de prueba 1 cargado para reservación {}", savedReserva1.getId());

            } catch (Exception e) {
                log.warn("Error al guardar reservación 1 o su pago: {}", e.getMessage());
            }
        }

        if (savedUser2 != null && savedDestino2 != null) {
            ReservacionDTO reserva2 = new ReservacionDTO(
                null,
                savedUser2.getId(),
                savedDestino2.getId(),
                LocalDate.now().plusMonths(1).plusDays(10),
                1,
                "María García",
                EstadoReserva.PENDIENTE.name()
            );
            try {
                ReservacionDTO savedReserva2 = reservacionService.saveReservacion(reserva2);
                log.info("Reservación de prueba 2 cargada para usuario {} en destino {}", savedUser2.getNombre(), savedDestino2.getTitulo());
            } catch (Exception e) {
                log.warn("Error al guardar reservación 2: {}", e.getMessage());
            }
        }

        log.info("Carga de datos de prueba finalizada.");
    }
}

