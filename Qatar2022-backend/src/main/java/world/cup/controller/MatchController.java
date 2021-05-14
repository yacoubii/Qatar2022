package world.cup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import world.cup.models.Match;
import world.cup.service.MatchService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MODERATOR','ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Match>> getAllMatchEntity() {
        List<Match> matchs = matchService.findAllMatchEntity();
        return new ResponseEntity<>(matchs, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MODERATOR','ROLE_ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Match> getMatchEntity(@PathVariable("id") Long id) {
        Match match = matchService.findMatchEntityById(id);
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Match> addMatchEntity(@RequestBody Match match) {
        Match newmatch = matchService.addMatchEntity(match);
        return new ResponseEntity<>(newmatch, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMatchEntity(@PathVariable("id") Long id) {
        matchService.deleteMatchEntity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR','ROLE_ADMIN')")
    @PutMapping(path="{id}")
    public void updateMatchEntity(
            @PathVariable("id") Long id,
            @RequestBody Match matchUpdate
    ){
        matchService.updateMatchEntity(id,matchUpdate);
    }



    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MODERATOR','ROLE_ADMIN')")
    @GetMapping(path="/link/{ticketId}/{matchId}")
    public void linkNewTicketToMatch(@PathVariable("ticketId") Long ticketId, @PathVariable("matchId") Long matchId){
        matchService.linkNewTicketToMatch(ticketId, matchId);
    }

}