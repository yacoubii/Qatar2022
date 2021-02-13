package com.example.Qatar2022.Services;

import com.example.Qatar2022.entities.TicketEntity;
import com.example.Qatar2022.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;

    }

    public List<TicketEntity> getTickets(){
        return ticketRepository.findAll();
    }

    public void addNewTicket(TicketEntity ticket) {
        Optional<TicketEntity> ticketOptional = ticketRepository.findTicketEntityByBeginningTime(ticket.getBeginningTime());
        if (ticketOptional.isPresent()){
            throw new IllegalStateException("Beginning time taken");
        }
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long ticketId) {
        ticketRepository.findById(ticketId);
        boolean exists= ticketRepository.existsById(ticketId);
        if(!exists){
            throw new IllegalStateException("Ticket with id " + ticketId + " does not exists");
        }
        ticketRepository.deleteById(ticketId);
    }

    @Transactional
    public void updateTicket(Long ticketId, String teamA, String teamB, LocalDate matchDate, LocalTime beginningTime, Float price, String ticketType, String matchType) {

        TicketEntity ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new IllegalStateException(
                "ticket with id " + ticketId + " does not exist"));
        if (teamA!=null &&
                teamA.length() > 0 &&
                !Objects.equals(ticket.getTeamA(), teamA)){
            ticket.setTeamA(teamA);
        }

        if (teamB!=null &&
                teamB.length() > 0 &&
                !Objects.equals(ticket.getTeamB(), teamB)){
            ticket.setTeamB(teamB);
        }

        if (matchDate!=null &&
                matchDate.toString().length() > 0 &&
                !Objects.equals(ticket.getMatchDate(), matchDate)){
            ticket.setMatchDate(matchDate);
        }
        if (beginningTime!=null &&
                beginningTime.toString().length()>0 &&
                !Objects.equals(ticket.getBeginningTime(), beginningTime)){
            Optional<TicketEntity> ticketOptional = ticketRepository.findTicketEntityByBeginningTime(beginningTime);
            if (ticketOptional.isPresent()){
                throw new IllegalStateException("Beginning time taken");
            }
            ticket.setBeginningTime(beginningTime);

        }

        if (price!=0 && !Objects.equals(ticket.getPrice(), price)){
            ticket.setPrice(price);
        }

        if (ticketType!=null &&
                ticketType.length() > 0 &&
                !Objects.equals(ticket.getTicketType(), ticketType)){
            ticket.setTicketType(ticketType);
        }

        if (matchType!=null &&
                matchType.length() > 0 &&
                !Objects.equals(ticket.getMatchType(), matchType)){
            ticket.setMatchType(matchType);
        }


    }
}
