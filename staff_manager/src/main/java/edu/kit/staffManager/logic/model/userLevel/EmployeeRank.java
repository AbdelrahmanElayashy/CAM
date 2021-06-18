package edu.kit.staffManager.logic.model.userLevel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "EMPLOYEE_RANK")
public class EmployeeRank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "rank")
    private String rank;

    @ManyToOne
    private EmployeeType type;

    private int importance;
}
