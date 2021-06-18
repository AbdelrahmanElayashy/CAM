package edu.kit.staffManager.logic.model.userLevel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="EMPLOYEE_TYPE")
public class EmployeeType {

    @Id
    @Column(name = "type")
    private String type;
}
