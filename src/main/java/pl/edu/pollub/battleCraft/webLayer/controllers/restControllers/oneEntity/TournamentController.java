package pl.edu.pollub.battleCraft.webLayer.controllers.restControllers.oneEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pollub.battleCraft.serviceLayer.exceptions.UncheckedExceptions.EntityValidation.EntityValidationException;
import pl.edu.pollub.battleCraft.serviceLayer.exceptions.UncheckedExceptions.EntityNotFoundException;
import pl.edu.pollub.battleCraft.serviceLayer.services.oneEnitity.implementations.TournamentServiceImpl;
import pl.edu.pollub.battleCraft.serviceLayer.services.oneEnitity.interfaces.TournamentService;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTORequest.Tournament.TournamentRequestDTO;
import pl.edu.pollub.battleCraft.webLayer.DTO.DTOResponse.Tournament.TournamentResponseDTO;

@RestController
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService){
        this.tournamentService = tournamentService;
    }

    @PostMapping(value = "/add/tournament", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TournamentResponseDTO addTournament(@RequestBody TournamentRequestDTO tournamentRequestDTO, BindingResult bindingResult){
        return tournamentService.organizeTournament(tournamentRequestDTO, bindingResult);
    }

    @PostMapping(value = "/edit/tournament", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TournamentResponseDTO editTournament(@RequestBody TournamentRequestDTO tournamentRequestDTO, BindingResult bindingResult){
        return tournamentService.editTournament(tournamentRequestDTO, bindingResult);
    }

    @GetMapping(value = "/get/tournament")
    public TournamentResponseDTO getTournament(@RequestParam(value = "name") String name){
        return tournamentService.getTournament(name);
    }
}