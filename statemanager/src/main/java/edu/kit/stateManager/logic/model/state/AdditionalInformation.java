package edu.kit.stateManager.logic.model.state;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="INFORMATION")
public class AdditionalInformation {

    public AdditionalInformation(UUID userId, LocalDateTime addedAt, @Length(min = 5, message = "*Your info must have at least 5 characters") @NotEmpty(message = "*Please provide info") String info) {
        this.userId = userId;
        this.addedAt = addedAt;
        this.info = info;
    }

    public AdditionalInformation(UUID userId, LocalDateTime addedAt, State state, @Length(min = 5, message = "*Your info must have at least 5 characters") @NotEmpty(message = "*Please provide info") String info) {
        this.userId = userId;
        this.addedAt = addedAt;
        this.setState(state);
        this.info = info;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="USER_ID")
    private UUID userId;      //User who added the information

    @Column(name = "LOCAL_DATE_TIME", columnDefinition = "TIMESTAMP")
    private LocalDateTime addedAt;    //Time when the information was added

    @ManyToOne
    @JoinColumn(name="STATE_ID")
    @Setter(AccessLevel.NONE)
    private State state;

    @Column(name="INFO", nullable = false, columnDefinition = "TEXT")
    @Length(min = 5, message = "*Your info must have at least 5 characters")
    @NotEmpty(message = "*Please provide info")
    private String info;


    public void setState(State state) {
        this.state = state;
        state.addAdditionalInformation(this);
    }
}
