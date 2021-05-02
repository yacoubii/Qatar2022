
import { Component, OnInit } from '@angular/core';
import { Spectator } from 'src/app/Models/spectator';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { SpectatorService } from 'src/app/Services/spectator.service';
import { TicketService } from 'src/app/Services/ticket.service';
import { TicketEntity } from 'src/app/Models/ticket';


@Component({
  selector: 'app-spectator',
  templateUrl: './spectator.component.html',
  styleUrls: ['./spectator.component.css']
})
export class SpectatorComponent implements OnInit{
  title = 'qatar2022';
  public spectators: Spectator[] = [];
  public tickets: TicketEntity[] = [];
  public editSpectator!: Spectator;
  public deleteSpectator: any;
  ticketDetails: any;
  constructor(private spectatorService: SpectatorService, private ticketService: TicketService) { }

  ngOnInit(){
    this.getSpectators();
    this.getTickets();
  }

  public getTickets(): void {
    this.ticketService.getTicketEntity().subscribe(
      (Response: TicketEntity[] ) =>{
        this.tickets = Response;
        console.log(Response);
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );
  }

  public getSpectators(): void {
    this.spectatorService.getSpectators().subscribe(
      (Response: Spectator[] ) =>{
        this.spectators = Response;
        console.log(Response);
      }, 
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
       
    );
  }

  public delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  public onAddSpectator(addForm: NgForm): void {

    document.getElementById('add-spectator-form')?.click();
    this.spectatorService.addSpectator(addForm.value).subscribe(
      async (response: Spectator) =>{
        console.log(response);
        await this.delay(1000);
     this.spectatorService.linkSpectatorToTicket(response.id,addForm.value.tickets[0]).subscribe(
          async (response: any) =>{
            console.log(response);
            await this.delay(1000);
            window.location.reload();
          }
        )
        this.getSpectators();
        addForm.reset();
      },
      (error: HttpErrorResponse) =>{
        alert(error.message);
        addForm.reset();

      }
    );

  }

  public onUpdateSpectator(spectatorId: any,spectator: Spectator): void {
    this.spectatorService.updateSpectator(spectatorId, spectator).subscribe(
      async (response: Spectator) =>{
        console.log(response);
        await this.delay(1000);
        this.spectatorService.linkSpectatorToTicket(spectatorId,spectator.tickets[0]).subscribe(
          async (response: any) =>{
            console.log(response); 
            await this.delay(1000);
            window.location.reload();
          }
        )
        
        //this.getSpectators();
      },
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );

  }
  public onDeleteSpectator(spectatorId: number): void {
    this.spectatorService.deleteSpectator(spectatorId).subscribe(
      async (response: void) =>{
        console.log(response);
        await this.delay(1000);
        window.location.reload();
      },
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    );

  }

  public searchSpectators(key : string): void {
    console.log(key);
    const results : Spectator[]= [];
    for (const spectator of this.spectators){
      if (spectator.firstName.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ||spectator.lastName.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ||spectator.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ||spectator.username.toLowerCase().indexOf(key.toLowerCase()) !== -1){
        results.push(spectator);
      }
    }
    this.spectators= results;
    if (results.length === 0 || !key){
      this.getSpectators();
    }
  }

  public onOpenModal(specator: any, mode: string): void{
    const container=document.getElementById('main-container')
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display ='none';
    button.setAttribute('data-toggle','modal');
    if (mode==='add'){
      button.setAttribute('data-target','#addSpectatorModal');
    }
    if (mode==='edit'){
      this.editSpectator=specator;
      button.setAttribute('data-target','#updateSpectatorModal');
    }
    if (mode === 'delete') {
      this.deleteSpectator=specator;
      button.setAttribute('data-target', '#deleteSpectatorModal');
    }
    if(mode=="details"){
      this.ticketDetails=specator;
      button.setAttribute('data-target', '#sessionDetailsModal');
    }
    container?.appendChild(button);
    button.click();
  }
}

