package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Long doctorId;
    private String email;
    private String name;
    private String lastName;
    private String specialization;
}
