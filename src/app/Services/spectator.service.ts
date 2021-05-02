import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Spectator } from '../Models/spectator';



@Injectable({
  providedIn: 'root'
})
export class SpectatorService {
  private apiServerUrl =environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getSpectators(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/spectator`);

  }

  public addSpectator(spectator: Spectator): Observable<Spectator>{
    return this.http.post<Spectator>(`${this.apiServerUrl}/spectator`, spectator);

  }

  public updateSpectator(spectatorId: number, spectator: Spectator): Observable<Spectator>{
    return this.http.put<Spectator>(`${this.apiServerUrl}/spectator/${spectatorId}`, spectator);

  }
  public deleteSpectator(spectatorId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/spectator/${spectatorId}`);
  }

  public linkSpectatorToTicket(spectatorId:number,ticketId:number): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/spectator/link/${spectatorId}/${ticketId}`,null);
  }
  
}
