package com.example.visita_medica.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitDto {
    private Long id;
    private String type;
    private LocalDate date;
    private LocalTime time;

    private Long patientId;
    private Long doctorId;
}
