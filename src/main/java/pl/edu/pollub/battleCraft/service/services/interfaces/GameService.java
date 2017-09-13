package pl.edu.pollub.battleCraft.service.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pollub.battleCraft.data.searchSpecyficators.searchCritieria.SearchCriteria;

import java.util.List;

public interface GameService {
    List<String> getAllGamesNames();

    Page getPageOfGames(Pageable pageable, List<SearchCriteria> searchCriteria);

    void banGames(String... gamesToBanUniqueNames);

    void unlockGames(String... gamesToUnlockUniqueNames);

    void deleteGames(String... gamesToDeleteUniqueNames);

    void acceptGames(String... gamesToDeleteUniqueNames);

    void cancelAcceptGames(String... gamesToDeleteUniqueNames);

    Resource getGameRules(String gameName);
}
