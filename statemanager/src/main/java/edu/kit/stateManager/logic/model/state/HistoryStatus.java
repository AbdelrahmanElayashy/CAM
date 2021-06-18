package edu.kit.stateManager.logic.model.state;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="HISTORY_STATUS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class HistoryStatus {

    public HistoryStatus(List<State> historyStates) {
        this.historyStates = historyStates;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToOne(mappedBy="historyStates")
    @JoinColumn(name="MEDICAL_DEVICE")
    private MedicalDevice medicalDev;

    @OneToMany(mappedBy="history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<State> historyStates = new ArrayList<>();

    public void addToHistory(State state) {
        historyStates.add(state);
    }

}
