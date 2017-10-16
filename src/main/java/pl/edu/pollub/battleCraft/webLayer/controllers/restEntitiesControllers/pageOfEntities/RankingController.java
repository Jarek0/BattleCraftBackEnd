package pl.edu.pollub.battleCraft.webLayer.controllers.restEntitiesControllers.pageOfEntities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.pollub.battleCraft.serviceLayer.services.pageOfEntities.interfaces.RankingService;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.Page.GetPageObjectsDTO;

@RestController
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping(value = "/page/ranking", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page getPageOfTournaments(@RequestBody GetPageObjectsDTO getPageObjectsDTO) {
        System.out.println("Try to get rankings");
        return rankingService.getPageOfRanking(getPageObjectsDTO.unwrapPageRequest(),
                getPageObjectsDTO.getSearchCriteria());
    }
}
