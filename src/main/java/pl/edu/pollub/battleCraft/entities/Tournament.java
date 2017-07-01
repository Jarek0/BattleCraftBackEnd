package pl.edu.pollub.battleCraft.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tournament extends AddressOwner{

    public Tournament() {
    }

    public Tournament(String name, short maxPlayers, short tablesCount, Date dateOfStart, boolean active, Game game, Address address) {
        super(address);
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.tablesCount = tablesCount;
        this.dateOfStart = dateOfStart;
        this.active = active;
        this.game = game;
    }

    @Column(length = 100)
    private String name;

    private short maxPlayers;

    private short tablesCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfStart;

    private boolean active;

    @ManyToOne
    private Game game;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(short maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public short getTablesCount() {
        return tablesCount;
    }

    public void setTablesCount(short tablesCount) {
        this.tablesCount = tablesCount;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
