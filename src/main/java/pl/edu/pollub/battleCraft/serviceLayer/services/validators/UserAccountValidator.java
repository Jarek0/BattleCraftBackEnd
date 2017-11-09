package pl.edu.pollub.battleCraft.serviceLayer.services.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.pollub.battleCraft.dataLayer.domain.AddressOwner.Tournament.Tournament;
import pl.edu.pollub.battleCraft.dataLayer.domain.AddressOwner.Tournament.enums.TournamentType;
import pl.edu.pollub.battleCraft.dataLayer.domain.AddressOwner.User.UserAccount;
import pl.edu.pollub.battleCraft.dataLayer.dao.jpaRepositories.*;
import pl.edu.pollub.battleCraft.dataLayer.domain.AddressOwner.User.subClasses.Player.Player;
import pl.edu.pollub.battleCraft.serviceLayer.exceptions.UncheckedExceptions.EntityNotFoundException;
import pl.edu.pollub.battleCraft.serviceLayer.exceptions.UncheckedExceptions.EntityValidation.EntityValidationException;
import pl.edu.pollub.battleCraft.serviceLayer.services.singleEnitity.InvitationDTO.DuelTournamentInvitationDTO;
import pl.edu.pollub.battleCraft.serviceLayer.services.singleEnitity.InvitationDTO.GroupTournamentInvitationDTO;
import pl.edu.pollub.battleCraft.serviceLayer.services.singleEnitity.InvitationDTO.InvitationDTO;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.UserAccount.Invitation.InvitationRequestDTO;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.UserAccount.Invitation.InvitationRequestPlayerDTO;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.UserAccount.UserAccountRequestDTO;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserAccountValidator implements Validator {
    private Errors errors;

    private final TournamentRepository tournamentRepository;

    private final AddressValidator addressValidator;

    private final UserAccountRepository userAccountRepository;

    private final PlayerRepository playerRepository;

    public UserAccountValidator(TournamentRepository tournamentRepository, AddressValidator addressValidator, UserAccountRepository userAccountRepository, PlayerRepository playerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.addressValidator = addressValidator;
        this.userAccountRepository = userAccountRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserAccountRequestDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        this.errors = errors;
        UserAccountRequestDTO userAccountRequestDTO = (UserAccountRequestDTO) o;
        this.validateName(userAccountRequestDTO.getName());
        this.validateName(userAccountRequestDTO.getNameChange());
        this.validateEMail(userAccountRequestDTO.getEmail());
        this.validateFirstName(userAccountRequestDTO.getFirstname());
        this.validateLastName(userAccountRequestDTO.getLastname());
        this.validatePhoneNumber(userAccountRequestDTO.getPhoneNumber());
        addressValidator.validate(userAccountRequestDTO,errors);
    }

    private void validateName(String name){
        if(name==null || !name.matches("^[A-ZĄĆĘŁŃÓŚŹŻa-zzżźćńółęąś1-9]{3,30}$"))
            errors.rejectValue("nameChange","","Name must start with big letter and have between 3 to 30 chars");
    }

    private void validateFirstName(String firstname){
        if(firstname==null || !firstname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-zzżźćńółęąś]{2,19}$"))
            errors.rejectValue("firstname","","First name must start with big letter and have between 3 to 30 chars");
    }

    private void validateLastName(String lastname){
        if(lastname==null || !lastname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-zzżźćńółęąś]{2,19}$"))
            errors.rejectValue("lastname","","Last name must start with big letter and have between 3 to 30 chars");
    }

    private void validatePhoneNumber(String phoneNumber){
        if(!phoneNumber.equals("") && !phoneNumber.matches("^[0-9]{9,11}$"))
            errors.rejectValue("phoneNumber","","Invalid phone number");
    }

    private void validateEMail(String email){
        if(email==null || !email.matches("(^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.+[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@+(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$)"))
            errors.rejectValue("email","","Invalid email");
    }

    public UserAccount getValidatedUserAccountToEdit(UserAccountRequestDTO userAccountRequestDTO, BindingResult bindingResult){
        return Optional.ofNullable(userAccountRepository.findUserAccountByUniqueName(userAccountRequestDTO.getName()))
                .orElseThrow(() -> new EntityNotFoundException(UserAccount.class,userAccountRequestDTO.getName()));
    }

    public List<InvitationDTO> getValidatedPlayersInvitations(List<InvitationRequestPlayerDTO> invitations, BindingResult bindingResult){
        if(invitations.size()==0)
            return new ArrayList<>();
        List<String> tournamentsNames = invitations.stream()
                .map(invitation -> invitation.getFirstPlayerInvitation().getTournamentName())
                .collect(Collectors.toList());
        if(containsDuplicates(tournamentsNames))
            bindingResult.rejectValue("participatedTournaments","","You cannot have duplicated tournament");

        List<Tournament> tournaments = tournamentRepository.findAcceptedTournamentsByUniqueNames(tournamentsNames);
        return tournaments.stream().map(tournament -> {
            if(tournament.getTournamentType()== TournamentType.GROUP){
                InvitationRequestPlayerDTO invitation = this.getInvitationByTournamentName(invitations,tournament.getName());
                Player player = playerRepository.findNotBannedPlayerByUniqueName(invitation.getSecondPlayerName());
                return new GroupTournamentInvitationDTO(tournament,
                        this.checkIfPlayerInvitationIsAcceptedForTournament(invitations,tournament.getName()),
                        player);
            }
            else{
                return new DuelTournamentInvitationDTO(tournament,
                        this.checkIfPlayerInvitationIsAcceptedForTournament(invitations,tournament.getName()));
            }
        }).collect(Collectors.toList());
    }

    public List<InvitationDTO> getValidatedOrganizersInvitations(List<InvitationRequestDTO> invitations, BindingResult bindingResult){
        if(invitations.size()==0)
            return new ArrayList<>();
        List<String> tournamentsNames = invitations.stream()
                .map(InvitationRequestDTO::getTournamentName)
                .collect(Collectors.toList());
        if(containsDuplicates(tournamentsNames))
            bindingResult.rejectValue("organizedTournaments","","You cannot have duplicated tournament");
        List<Tournament> tournaments = tournamentRepository.findAcceptedTournamentsByUniqueNames(tournamentsNames);

        return tournaments.stream().map(tournament ->
                new InvitationDTO(tournament,this.checkIfInvitationIsAcceptedForTournament(invitations,tournament.getName()))).collect(Collectors.toList());
    }

    private InvitationRequestPlayerDTO getInvitationByTournamentName(List<InvitationRequestPlayerDTO> invitations, String tournamentName){
        return invitations.stream()
                .filter(invitation -> invitation.getFirstPlayerInvitation().getTournamentName().equals(tournamentName))
                .findFirst().get();
    }

    private boolean checkIfPlayerInvitationIsAcceptedForTournament(List<InvitationRequestPlayerDTO> invitations, String tournamentName){
        return invitations.stream().map(InvitationRequestPlayerDTO::getFirstPlayerInvitation)
                .filter(invitationRequestDTO -> invitationRequestDTO.getTournamentName().equals(tournamentName))
                .findFirst().get().isAccepted();
    }

    private boolean checkIfInvitationIsAcceptedForTournament(List<InvitationRequestDTO> invitations, String tournamentName){
        return invitations.stream().filter(invitationRequestDTO -> invitationRequestDTO.getTournamentName().equals(tournamentName))
                .findFirst().get().isAccepted();
    }

    private boolean containsDuplicates(List<String> values){
        Set<String> setWithoutDuplicates = new HashSet<>(values);
        return setWithoutDuplicates.size() < values.size();
    }

    public void finishValidation(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Invalid user account data", bindingResult);
        }
    }
}
