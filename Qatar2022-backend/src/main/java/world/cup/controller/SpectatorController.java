package world.cup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import world.cup.models.Spectator;
import world.cup.models.User;
import world.cup.service.SpectatorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/spectator")
public class SpectatorController {
    private final SpectatorService spectatorService;

    @Autowired
    public SpectatorController(SpectatorService spectatorService){
        this.spectatorService=spectatorService;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @GetMapping
    public List<Spectator> getSpectators(){
        return spectatorService.getSpectators();
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @GetMapping(path = "{spectatorId}")
    User getSpectator(@PathVariable Long spectatorId) {
        return spectatorService.getSpectator(spectatorId);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @PostMapping
    public Spectator registerNewSpectator(@Valid @RequestBody Spectator spectator, BindingResult bindingResult){
        return spectatorService.addNewSpectator(spectator,bindingResult);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @PutMapping(path="{userId}")
    public void updateSpectator(
            @PathVariable("userId") Long spectatorId,
            @RequestBody Spectator spectatorUpdate
    ){
        spectatorService.updateSpectator(spectatorId, spectatorUpdate);

    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteSpectatorById(@PathVariable Long id) {
        spectatorService.DeleteSpectator(id);
    }

    @PostMapping(path="/link/{spectatorId}/{ticketId}")
    public void linkSpectatorToTicket(@PathVariable("ticketId") Long ticketId, @PathVariable("spectatorId") Long spectatorId){
        spectatorService.linkNewSpectatorToTicket(spectatorId, ticketId);
    }
}

