package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicDto {
    private Long clinicId;
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String number;
}
