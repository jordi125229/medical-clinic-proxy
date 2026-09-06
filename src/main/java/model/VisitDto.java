package model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDto {
    private ClinicDto clinicDto;
    private String doctorEmail;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private String patientEmail;
}
