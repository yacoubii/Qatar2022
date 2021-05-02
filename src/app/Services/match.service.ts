import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchEntity } from '../Models/match';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})

export class MatchService {
  

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getMatchEntity(): Observable<MatchEntity[]> {
    return this.http.get<MatchEntity[]>(`${this.apiServerUrl}/match/all`);
  }

  public addMatchEntity(match: MatchEntity): Observable<MatchEntity> {
    return this.http.post<MatchEntity>(`${this.apiServerUrl}/match/add`, match);
  }

  public updateMatchEntity(match: MatchEntity,matchId: number): Observable<void>{
    return this.http.put<void>(`${this.apiServerUrl}/match/${matchId}`, match);
  }

  public deleteMatchEntity(matchId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/match/delete/${matchId}`);
  }

  public linkMatchToTicket(ticketId:number, matchId:number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/match/link/${ticketId}/${matchId}`)
  }
}