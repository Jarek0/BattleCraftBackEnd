package pl.edu.pollub.battleCraft.data.entities.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class AddressOwner implements Serializable {


    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Address address;

    public AddressOwner(Address address) {
        this.address = address;
        address.setAddressOwnerByOneSide(this);
    }

    public void setAddress(Address address){
        this.address = address;
        address.setAddressOwnerByOneSide(this);
    }

    public void setAddressByOneSide(Address address){
        this.address = address;
    }
}
