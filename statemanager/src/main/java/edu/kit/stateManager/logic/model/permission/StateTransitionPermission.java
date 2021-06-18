package edu.kit.stateManager.logic.model.permission;

import edu.kit.stateManager.logic.model.state.StateNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StateTransitionPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private StateNames fromState;

    @Enumerated(EnumType.STRING)
    private StateNames toState;

    private String employeeType;

    private String minimumEmployeeRank;

    public StateTransitionPermission(StateNames fromState, StateNames toState, UserLevel minUserLevel) {
        this.fromState = fromState;
        this.toState = toState;
        this.employeeType = minUserLevel.getEmployeeType();
        this.minimumEmployeeRank = minUserLevel.getEmployeeRank();
    }

    public UserLevel getMinUserLevel() {
        return new UserLevel(employeeType, minimumEmployeeRank);
    }
}
