package edu.kit.stateManager.logic.model.permission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommandPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private Command command;

    private String type;

    private String minimumEmployeeRank;

    public CommandPermission(Command command, UserLevel minUserlevel) {
        this.command = command;
        this.type = minUserlevel.getEmployeeType();
        this.minimumEmployeeRank = minUserlevel.getEmployeeRank();
    }

    public UserLevel getMinUserLevel() {
        return new UserLevel(type, minimumEmployeeRank);
    }
}
