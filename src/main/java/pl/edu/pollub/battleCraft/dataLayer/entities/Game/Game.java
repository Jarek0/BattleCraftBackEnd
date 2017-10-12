package pl.edu.pollub.battleCraft.dataLayer.entities.Game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Formula;
import pl.edu.pollub.battleCraft.dataLayer.entities.Game.enums.GameStatus;
import pl.edu.pollub.battleCraft.dataLayer.entities.Tournament.Tournament;
import pl.edu.pollub.battleCraft.dataLayer.entities.User.subClasses.organizers.Organizer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Game{
    public Game(String name, Organizer creator){
        this.name = name;
        this.creator = creator;
        this.status = GameStatus.NEW;
        this.banned = false;
        this.dateOfCreation = new Date();
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(length = 30)
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game", cascade = CascadeType.ALL)
    private List<Tournament> tournaments = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Organizer creator;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;

    @Formula("(select count(*) from tournament t where t.game_id = id)")
    private int tournamentsNumber;

    private boolean banned;

    public void addTournamentByOneSide(Tournament tournament) {
        this.deleteTournamentWithTheSameName(tournament.getName());
        this.tournaments.add(tournament);
    }

    private void deleteTournamentWithTheSameName(String tournamentName){
        Tournament tournamentToDelete = this.tournaments.stream()
                .filter(tournament -> tournament.getName().equals(tournamentName))
                .findFirst().orElse(null);
        if(tournamentToDelete!=null)
            this.tournaments.remove(tournamentToDelete);
    }

    private void setTournaments(List<Tournament> tournaments){
        this.tournaments = tournaments;
    }

    private void setCreator(Organizer creator){
        this.creator = creator;
    }

    private void setTournamentsNumber(int tournamentsNumber){
        this.tournamentsNumber = tournamentsNumber;
    }
}