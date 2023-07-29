package bg.softuni.mycarservicebackend.domain.dtos;

import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureInfoDto {

    private Long id;
    private ServiceTypeEnum serviceType;
    private Double price;
    private Boolean isReady;
}
