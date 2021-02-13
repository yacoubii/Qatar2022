package com.example.Qatar2022.controllers;


import com.example.Qatar2022.Services.TicketService;
import com.example.Qatar2022.Services.UserService;
import com.example.Qatar2022.entities.TicketEntity;
import com.example.Qatar2022.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService=ticketService;
    }

    @GetMapping
    public List<TicketEntity> getTickets(){
        return ticketService.getTickets();
    }

    @PostMapping
    public void registerNewTicket(@RequestBody TicketEntity ticket){
        ticketService.addNewTicket(ticket);
    }

    @DeleteMapping(path="{ticketId}")
    public void deleteTicket(@PathVariable("ticketId") Long ticketId){
        ticketService.deleteTicket(ticketId);

    }

    @PutMapping(path="{ticketId}")
    public void updateTicket(
            @PathVariable("ticketId") Long ticketId,
            @RequestParam(required = false) String teamA,
            @RequestParam(required = false) String teamB,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime beginningTime,
            @RequestParam(required = false,defaultValue="0") Float price,
            @RequestParam(required = false) String ticketType,
            @RequestParam(required = false) String matchType){
        ticketService.updateTicket(ticketId, teamA, teamB, matchDate,beginningTime,price,ticketType,matchType);

    }






}
