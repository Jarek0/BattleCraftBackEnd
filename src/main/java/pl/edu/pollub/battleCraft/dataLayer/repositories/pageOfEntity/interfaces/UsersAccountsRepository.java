package pl.edu.pollub.battleCraft.dataLayer.repositories.pageOfEntity.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pollub.battleCraft.dataLayer.repositories.helpers.search.criteria.SearchCriteria;

import java.util.List;

public interface UsersAccountsRepository {
    Page getPageOfUserAccounts(List<SearchCriteria> searchCriteria, Pageable requestedPage);

    void banUsersAccounts(String... usersAccountsToBanUniqueNames);

    void deleteUsersAccounts(String... usersAccountsToDeleteUniqueNames);

    void unlockUsersAccounts(String... usersAccountsToBanUniqueNames);

    void acceptUsersAccounts(String... usersAccountsToAcceptUniqueNames);

    void cancelAcceptUsersAccounts(String... usersAccountsToCancelAcceptUniqueNames);

    void advancePlayersToOrganizer(String... playersToAdvanceToOrganizersUniqueNames);

    void degradeOrganizerToPlayers(String... organizerToDegradeToPlayersUniqueNames);
}
