package com.example.Qatar2022.entities;


import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
public class TicketEntity {
    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )

    private Long id;
    private String teamA;
    private String teamB;
    private LocalDate matchDate;
    private LocalTime beginningTime;
    private Float price;
    private String ticketType;
    private String matchType;

    public TicketEntity() {
    }

    public TicketEntity(Long id, String teamA, String teamB, LocalDate matchDate, LocalTime beginningTime, Float price, String ticketType, String matchType) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchDate = matchDate;
        this.beginningTime = beginningTime;
        this.price = price;
        this.ticketType = ticketType;
        this.matchType = matchType;
    }

    public TicketEntity(String teamA, String teamB, LocalDate matchDate, LocalTime beginningTime, Float price, String ticketType, String matchType) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchDate = matchDate;
        this.beginningTime = beginningTime;
        this.price = price;
        this.ticketType = ticketType;
        this.matchType = matchType;
    }

    public Long getId() {
        return id;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public LocalTime getBeginningTime() {
        return beginningTime;
    }

    public Float getPrice() {
        return price;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public void setBeginningTime(LocalTime beginningTime) {
        this.beginningTime = beginningTime;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id=" + id +
                ", firstName='" + teamA + '\'' +
                ", lastName='" + teamB + '\'' +
                ", email='" + matchDate + '\'' +
                ", password='" + beginningTime + '\'' +
                ", country='" + price + '\'' +
                ", tel='" + ticketType + '\'' +
                ", dob=" + matchType +

                '}';
    }
}
