import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Role } from '../Models/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getRoles():Observable<Role[]>{
    return this.http.get<[Role]>(`${this.apiServerUrl}/role`);
  }

  public addRole(role: Role):Observable<void>{
    console.log(role)
    return this.http.post<void>(`${this.apiServerUrl}/role`,role)
  }

  public updateRole(role : Role, roleId:number): Observable<void>{
    return  this.http.put<void>(`${this.apiServerUrl}/role/${roleId}`,role)
  }

  public deleteRole(roleId:number): Observable<void>{
    return  this.http.delete<void>(`${this.apiServerUrl}/role/${roleId}`)
  }

  public linkNewUserToRole(userId:number, roleId:number): Observable<void>{
    return this.http.get<void>(`${this.apiServerUrl}/role/link/${userId}/${roleId}`);
  }

}
