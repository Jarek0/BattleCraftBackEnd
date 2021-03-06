package pl.edu.pollub.battleCraft.dataLayer.domain.User.subClasses.Player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pollub.battleCraft.dataLayer.domain.User.UserAccount;
import pl.edu.pollub.battleCraft.dataLayer.domain.User.enums.UserType;
import pl.edu.pollub.battleCraft.dataLayer.domain.Participation.Participation;
import pl.edu.pollub.battleCraft.dataLayer.domain.Play.Play;

import javax.persistence.*;
import java.util.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@DiscriminatorValue("Player")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Player extends UserAccount {

    public Player() {
        super(UserType.ACCEPTED);
        this.banned = false;
    }

    public Player(UserType userType) {
        super(userType);
        this.banned = false;
    }

    public void setBanned(boolean banned){
        this.banned = banned;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  mappedBy = "player")
    protected List<Participation> participation = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "player")
    protected List<Play> battles = new ArrayList<>();

    public void addParticipationByOneSide(Participation participation) {
        this.deleteParticipationWithTheSameTournamentName(participation.getParticipatedTournament().getName());
        this.participation.add(participation);
    }

    public void deleteParticipationByOneSide(Participation participation){
        if(this.participation.contains(participation))
            this.participation.remove(participation);
    }

    private void deleteParticipationWithTheSameTournamentName(String tournamentName){
        Participation participation = this.participation.stream()
                .filter(participation1 -> participation1.getParticipatedTournament().getName().equals(tournamentName))
                .findFirst().orElse(null);
        if(participation!=null){
            this.participation.remove(participation);
        }
    }

    public void addBattleByOneSide(Play battle){
        this.battles.add(battle);
    }

    public void removeBattleByOneSide(Play battle){
        this.battles.remove(battle);
    }

    public void setParticipation(List<Participation> participation){
        this.participation = participation;
    }
}
