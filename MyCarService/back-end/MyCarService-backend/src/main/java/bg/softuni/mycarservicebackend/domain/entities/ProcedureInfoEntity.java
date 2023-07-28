package bg.softuni.mycarservicebackend.domain.entities;

import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "procedure_info")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureInfoEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum serviceType;

    @Column
    private Double price;

    @Column
    private Boolean isReady;
}
