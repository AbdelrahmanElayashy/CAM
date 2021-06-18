package edu.kit.stateManager.logic.model.state;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="MEDICAL_DEVICE")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class MedicalDevice  {

    public MedicalDevice(UUID deviceId, DefectPriority defectPriority, State currentState, HistoryStatus historyStates) {
        this.deviceId = deviceId;
        this.defectPriority = defectPriority;
        // this method should be before setCurrentState, because to set the history in state.
        this.setHistoryStatus(historyStates);
        this.setCurrentState(currentState);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="DEVICE_ID")
    @NotNull
    private UUID deviceId;

    @Column(name="DEFECT_PRIORITY", nullable = true)
    @Enumerated(EnumType.STRING)
    private DefectPriority defectPriority;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="CURRENT_STATE")
    @Setter(AccessLevel.NONE)
    private State currentState;


    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="HISTORY_STATE")
    private HistoryStatus historyStates;


    public void setCurrentState(State curState) {

        curState.setHistory(this.historyStates);
        this.currentState = curState;
    }

    public void setHistoryStatus(HistoryStatus histState) {
        this.historyStates = histState;
        histState.setMedicalDev(this);
    }

    public void addToHistory(State state) {
        historyStates.addToHistory(state);
    }

}
