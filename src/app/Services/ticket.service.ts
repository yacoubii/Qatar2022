import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TicketEntity } from '../Models/ticket';


@Injectable({
  providedIn: 'root'
})

export class TicketService {
  

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getTicketEntity(): Observable<TicketEntity[]> {
    return this.http.get<TicketEntity[]>(`${this.apiServerUrl}/ticket/all`);
  }

  public addTicketEntity(ticket: TicketEntity): Observable<TicketEntity> {
    return this.http.post<TicketEntity>(`${this.apiServerUrl}/ticket/add`, ticket);
  }

  public updateTicketEntity(ticket: TicketEntity,ticketId: number): Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/ticket/${ticketId}`, ticket);
  }

  public deleteTicketEntity(ticketId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/ticket/${ticketId}`);
  }



}