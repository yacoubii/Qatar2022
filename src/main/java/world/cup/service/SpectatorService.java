package world.cup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import world.cup.models.*;
import world.cup.repositories.RoleRepository;
import world.cup.repositories.SpectatorRepository;
import world.cup.repositories.TicketRepository;

import java.util.*;

@Service
public class SpectatorService {
    private final SpectatorRepository spectatorRepository;
    private final TicketRepository ticketRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public SpectatorService(SpectatorRepository spectatorRepository,TicketRepository ticketRepository, RoleRepository roleRepository){
        this.spectatorRepository=spectatorRepository;
        this.ticketRepository=ticketRepository;
        this.roleRepository=roleRepository;
    }

    public List<Spectator> getSpectators(){
        return spectatorRepository.findAll();
    }

    public User getSpectator(Long id) {
        User spectator = getSpectators().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return spectator;
    }

    public void addNewSpectator(Spectator spectator, BindingResult bindingResult) {
        Optional<Spectator> spectatorOptional = spectatorRepository.findSpectatorByEmail(spectator.getEmail());
        String password=spectator.getPassword();
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        spectator.setPassword(pw_hash);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findRoleByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        spectator.setRoles(roles);

        if (spectatorOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        spectatorRepository.save(spectator);
    }

    public void DeleteSpectator(Long id){
        Optional<Spectator> spectatorId = spectatorRepository.findById(id);
        if(!spectatorId.isPresent()){
            throw new IllegalStateException("spectator does not exist");
        }
        spectatorRepository.deleteById(id);
    }

    public void updateSpectator(Long spectatorId, Spectator spectatorUpdate) {
        Spectator spectator = spectatorRepository.findById(spectatorId).orElseThrow(()-> new IllegalStateException(
                "spectator with id " + spectatorId + " does not exist"));
        if (spectatorUpdate.getCardNumber() !=null &&
                spectatorUpdate.getCardNumber().length() > 0 &&
                !Objects.equals(spectator.getCardNumber(), spectatorUpdate.getCardNumber())){
            spectator.setCardNumber(spectatorUpdate.getCardNumber());
        }

        if (spectatorUpdate.getExpiration() !=null &&
                spectatorUpdate.getExpiration().toString().length() > 0 &&
                !Objects.equals(spectator.getExpiration(), spectatorUpdate.getExpiration())){
            spectator.setExpiration(spectatorUpdate.getExpiration());
        }

        if (spectatorUpdate.getCvv() !=null &&
                spectatorUpdate.getCvv().length() > 0 &&
                !Objects.equals(spectator.getCvv(), spectatorUpdate.getCvv())){
            spectator.setCvv(spectatorUpdate.getCvv());
        }

        spectatorRepository.save(spectator);

    }

    public void linkNewSpectatorToTicket(Long spectatorId, Long ticketId){
        Spectator spectator = spectatorRepository.findById(spectatorId).orElseThrow(()-> new IllegalStateException(
                "spectator with id " + spectatorId + " does not exist"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new IllegalStateException(
                "ticket with id " + ticketId + " does not exist"));
        spectator.setTicket(ticket);
        spectatorRepository.save(spectator);

    }
}
