package edu.kit.stateManager.logic.model.state;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="STATE")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class State {

    public State(StateNames state, UUID enteredBy, LocalDateTime enteredAt) {
        this.state = state;
        this.enteredBy = enteredBy;
        this.enteredAt = enteredAt;
        this.publicationDate = enteredAt.toLocalDate();
    }

    public State(StateNames state, UUID enteredBy, LocalDateTime enteredAt, LocalDate date) {
        this.state = state;
        this.enteredBy = enteredBy;
        this.enteredAt = enteredAt;
        this.publicationDate = date;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private StateNames state;

    @Column(name="USER_ID")
    private UUID enteredBy;       //User who changed the devices state to this one

    @ManyToOne
    @JsonIgnore
    private HistoryStatus history;


    @Column(name = "LOCAL_DATE_TIME",   columnDefinition = "TIME")
    private LocalDateTime enteredAt;    //Time when the devices state was changed into this one

    @Column(name = "PUBLICATION_DATE", columnDefinition = "DATE")
    private LocalDate publicationDate;


    @Column(name = "LOCAL_DATE", columnDefinition = "DATE")
    private LocalDate availabilityDate; //Date when a device will be available (only used in defect states and bought state)

    @OneToMany(mappedBy="state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalInformation> additionalInformation = new ArrayList<>();

    public void addAdditionalInformation(AdditionalInformation additionalInformation) {

        this.additionalInformation.add(additionalInformation);

    }

}
