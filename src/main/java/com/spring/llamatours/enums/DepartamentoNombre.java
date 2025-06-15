package com.spring.llamatours.enums;

public enum DepartamentoNombre {
    AMAZONAS("Amazonas"),
    ANCASH("Áncash"),
    APURIMAC("Apurímac"),
    AREQUIPA("Arequipa"),
    AYACUCHO("Ayacucho"),
    CAJAMARCA("Cajamarca"),
    CALLAO("Callao"),
    CUSCO("Cusco"),
    HUANCAVELICA("Huancavelica"),
    HUANUCO("Huánuco"),
    ICA("Ica"),
    JUNIN("Junín"),
    LA_LIBERTAD("La Libertad"),
    LAMBAYEQUE("Lambayeque"),
    LIMA("Lima"),
    LORETO("Loreto"),
    MADRE_DE_DIOS("Madre de Dios"),
    MOQUEGUA("Moquegua"),
    PASCO("Pasco"),
    PIURA("Piura"),
    PUNO("Puno"),
    SAN_MARTIN("San Martín"),
    TACNA("Tacna"),
    TUMBES("Tumbes"),
    UCAYALI("Ucayali");

    private final String nombreVisible;

    DepartamentoNombre(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
}
