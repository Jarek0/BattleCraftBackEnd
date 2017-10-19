package pl.edu.pollub.battleCraft.dataLayer.entities.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import pl.edu.pollub.battleCraft.dataLayer.entities.Address.AddressOwner;
import pl.edu.pollub.battleCraft.dataLayer.entities.User.subClasses.enums.UserType;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount extends AddressOwner{

    public UserAccount(){
        super();
        this.status = UserType.NEW;
    }

    protected UserAccount(UserType userType){
        super();
        this.status = userType;
    }

    @Enumerated(EnumType.STRING)
    private UserType status;

    @Column(length = 20)
    private String firstname;

    @Column(length = 20)
    private String lastname;

    @Column(length = 30, unique = true)
    private String name;

    @Column(length = 50, unique = true)
    private String email;

    @JsonIgnore
    @Column(length = 100)
    private String password;

    @Column(length = 11)
    private String phoneNumber;

}
