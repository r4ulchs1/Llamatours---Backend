package com.spring.llamatours.pruebita;

import com.spring.llamatours.DTOs.DestinoDTO;
import com.spring.llamatours.enums.DepartamentoNombre;
import com.spring.llamatours.services.DestinoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoaderExtraDestinos implements CommandLineRunner {

    private final DestinoService destinoService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Cargando destinos adicionales de prueba...");

        DestinoDTO[] destinos = new DestinoDTO[] {
            new DestinoDTO(null, "Aventura en la Selva de Tarapoto",
                "Sumérgete en la selva amazónica con cascadas, caminatas y contacto con comunidades nativas.",
                "Trekking en la selva, Visita a la Catarata de Ahuashiyacu, Baños en pozas naturales.",
                new BigDecimal("320.00"), LocalDate.now().plusDays(15), LocalDate.now().plusDays(18),
                3, 18, DepartamentoNombre.SAN_MARTIN.name(), "Tarapoto",
                "https://tse1.mm.bing.net/th/id/OIP.L-fRfaRgq2WGu4hpD84O2gHaEk?rs=1&pid=ImgDetMain&o=7&rm=3",
                "https://s3-us-west-2.amazonaws.com/denomades/blog/wp-content/uploads/2020/01/28162747/tarapoto-1024x683.jpg",
                "https://www.viajesydestinos.com.pe/wp-content/uploads/2023/04/Cascada-de-Carpishuyacu-1024x768.jpg",
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/310039613.jpg?k=6d02720caae2e71464a29fc7519bd4a214b54ac47422c99a10a06835488d5361&o=&hp=1"),

            new DestinoDTO(null, "Playas Encantadas de Máncora",
                "Relájate en las cálidas playas del norte con sol, surf y gastronomía marina.",
                "Clases de surf, Tour en moto acuática, Cena romántica frente al mar.",
                new BigDecimal("480.00"), LocalDate.now().plusWeeks(2), LocalDate.now().plusWeeks(2).plusDays(3),
                3, 30, DepartamentoNombre.PIURA.name(), "Máncora",
                "https://i.ytimg.com/vi/wMqpNT3ZF-I/maxresdefault.jpg",
                "https://tse1.mm.bing.net/th/id/OIP.kopi3d88q7NC6urCQ0C5DgHaEK?rs=1&pid=ImgDetMain&o=7&rm=3",
                "https://tse2.mm.bing.net/th/id/OIP.VdrBvtNrOg2SaJssg1Li2QHaEW?rs=1&pid=ImgDetMain&o=7&rm=3",
                "https://tse4.mm.bing.net/th/id/OIP.kqEYMiEPRbCDALQJneJCYAHaFj?rs=1&pid=ImgDetMain&o=7&rm=3"),

            new DestinoDTO(null, "Cultura Viva en Chachapoyas",
                "Explora la historia ancestral de los Chachapoyas y visita la fortaleza de Kuélap.",
                "Tour arqueológico, Caminata ecológica, Intercambio cultural con locales.",
                new BigDecimal("540.00"), LocalDate.now().plusWeeks(4), LocalDate.now().plusWeeks(4).plusDays(4),
                4, 16, DepartamentoNombre.AMAZONAS.name(), "Chachapoyas",
                "https://www.tierrasvivas.com/en/kuelap-fortress-chachapoyas-peru",
                "https://tse2.mm.bing.net/th/id/OIP.wjwEoSTj_Qwdh0OHcdRzYQHaFj?rs=1&pid=ImgDetMain&o=7&rm=3",
                "https://www.viajesfantasticos.net/wp-content/uploads/lugares-turisticos-de-chachapoyas.jpg",
                "https://th.bing.com/th/id/R.d30014f676da25aa29b3fadfb024f0ad?rik=2DAZG0Aiyj3B4w&riu=http%3a%2f%2fhablemosdeculturas.com%2fwp-content%2fuploads%2f2019%2f01%2fcultura-chachapoyas-1.jpg&ehk=37IO90fYRAeG0UH4KRikqAcOxSmM0kMvRrVsOeTkq60%3d&risl=&pid=ImgRaw&r=0"),

            new DestinoDTO(null, "Misterios de las Líneas de Nazca",
                "Observa los antiguos geoglifos desde el cielo y recorre el desierto en buggy.",
                "Sobrevuelo en avioneta, Paseo en buggy, Sandboarding.",
                new BigDecimal("390.00"), LocalDate.now().plusDays(20), LocalDate.now().plusDays(22),
                2, 20, DepartamentoNombre.ICA.name(), "Nazca",
                "https://historia.nationalgeographic.com.es/medio/2022/12/21/istock-1336962106_4b78ead8_221221101144_1200x630.jpg",
                "https://picchutravel.com/wp-content/uploads/las-lineas-de-nazca.jpg",
                "https://raicesindigenas.net/wp-content/uploads/intrincadas-misteriosas-lineas-desierto.webp",
                "https://tse1.mm.bing.net/th/id/OIP.9XsT8U3GqOrRLMj0_DkbmQHaE8?rs=1&pid=ImgDetMain&o=7&rm=3"),

            new DestinoDTO(null, "Encanto Andino en Ayacucho",
                "Vive las costumbres y paisajes de la sierra ayacuchana en una ruta cultural.",
                "Taller de retablos, Ruta de iglesias coloniales, Caminata al mirador Acuchimay.",
                new BigDecimal("280.00"), LocalDate.now().plusWeeks(1), LocalDate.now().plusWeeks(1).plusDays(2),
                2, 22, DepartamentoNombre.AYACUCHO.name(), "Ayacucho",
                "https://portal.andina.pe/EDPfotografia3/Thumbnail/2022/07/14/000883243W.jpg",
                "https://www.ytuqueplanes.com/imagenes/fotos/ofertas/Campanayuq-urpillay_04042023_P.jpg",
                "https://tse1.mm.bing.net/th/id/OIP.xWOfER5Vn50Fu9DyM9_QUQHaE8?rs=1&pid=ImgDetMain&o=7&rm=3",
                "https://tse3.mm.bing.net/th/id/OIP.IArkgNT69OAGaYVBnM_wOwHaE7?rs=1&pid=ImgDetMain&o=7&rm=3")
        };

        for (DestinoDTO destino : destinos) {
            try {
                destinoService.saveDestino(destino);
                log.info("Destino cargado: {}", destino.getTitulo());
            } catch (Exception e) {
                log.warn("No se pudo guardar el destino '{}': {}", destino.getTitulo(), e.getMessage());
            }
        }
    }
}
