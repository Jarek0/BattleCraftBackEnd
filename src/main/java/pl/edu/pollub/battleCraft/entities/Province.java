package pl.edu.pollub.battleCraft.entities;

import javax.persistence.*;

/*I made field location as new Entity class because I wanted to create a list of possible provinces in poland in database*/
@Entity
public class Province {

    public Province(){}

    public Province(String location) {
        this.location = location;
    }

    public Province(String location,Address address) {
        this.address = address;
        this.location = location;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30)
    String location;

    @OneToOne(mappedBy = "province",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Address address;

    @Override
    public String toString() {
        return location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}