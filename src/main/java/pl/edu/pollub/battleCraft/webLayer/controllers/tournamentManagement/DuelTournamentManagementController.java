package pl.edu.pollub.battleCraft.webLayer.controllers.tournamentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pollub.battleCraft.serviceLayer.services.tournamentManagement.DuelTournamentManagementService;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.TournamentProgress.Duel.Battle.DuelBattleRequestDTO;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTOResponse.TournamentProgress.Duel.DuelTournamentProgressResponseDTO;
import pl.edu.pollub.battleCraft.webLayer.toResponseDTOsMappers.TournamentProgress.DuelTournamentProgressDTOMapper;

@RestController
public class DuelTournamentManagementController {

    private final DuelTournamentManagementService duelTournamentManagementService;

    private final DuelTournamentProgressDTOMapper duelTournamentProgressDTOMapper;

    @Autowired
    public DuelTournamentManagementController(DuelTournamentManagementService duelTournamentManagementService, DuelTournamentProgressDTOMapper duelTournamentProgressDTOMapper) {
        this.duelTournamentManagementService = duelTournamentManagementService;
        this.duelTournamentProgressDTOMapper = duelTournamentProgressDTOMapper;
    }

    @GetMapping(value ="/next/tour/duel/tournament")
    public DuelTournamentProgressResponseDTO nextTour(@RequestParam(value = "name") String name){
        return duelTournamentProgressDTOMapper.map(duelTournamentManagementService.nextTour(name));
    }

    @GetMapping(value ="/previous/tour/duel/tournament")
    public DuelTournamentProgressResponseDTO previousTour(@RequestParam(value = "name") String name){
        return duelTournamentProgressDTOMapper.map(duelTournamentManagementService.previousTour(name));
    }

    @PostMapping(value ="/set/points/duel/tournament")
    public DuelTournamentProgressResponseDTO setPointsOnTable(@RequestParam(value = "name") String name, @RequestBody DuelBattleRequestDTO battleDTO){
        return duelTournamentProgressDTOMapper.map(duelTournamentManagementService.setPoints(name, battleDTO));
    }

    @GetMapping(value ="/finish/duel/tournament")
    public DuelTournamentProgressResponseDTO finishTournament(@RequestParam(value = "name") String name){
        return duelTournamentProgressDTOMapper.map(duelTournamentManagementService.finishTournament(name));
    }
}