import { Role } from 'src/app/Models/role';
import {Component, OnInit} from "@angular/core";
import { NgForm } from '@angular/forms';
import { RoleService } from 'src/app/Services/role.service';
import {UserService} from "../../Services/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {User} from "../../Models/user";


@Component({
  selector:'app-roles-list',
  templateUrl:'roles-list.component.html',
  styleUrls:['roles-list.component.css']
})

export class RolesListComponent implements OnInit{
  roles:Role[]=[]
  editRole!: Role;
  deleteRole: any;
  constructor(private roleService:RoleService) { }
  ngOnInit() : void{
    this.getRoles();
    console.log("role",this.roles)
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe((response:Role[])=>{
      this.roles=response
    },
      (error:HttpErrorResponse)=>{
        console.error(error.message);
      })
  }

  public onAddRole(addForm: NgForm):void{
    document.getElementById("add-role-form")?.click();
    console.log(addForm.value)
    this.roleService.addRole(addForm.value).subscribe(
      (response:any)=>{
        console.log(response)
        this.getRoles();
      },(error:HttpErrorResponse)=>{
        console.error(error.message);
      }
    )
  }

  public OnUpdateRole(role: Role):void{
    document.getElementById("update-role-form")?.click();
    this.roleService.updateRole(role,role.id).subscribe((response:any) =>{
        this.getRoles();
      },
      (error:HttpErrorResponse)=>{
        console.error(error.message);
      })
  }

  public OnDeleteRole(roleId: number):void{
    this.roleService.deleteRole(roleId).subscribe(
      (response:any) =>{
        console.log(response);
        this.getRoles();
      },
      (error:HttpErrorResponse)=>{
        console.error(error.message);
      }
    )
  }

  public onOpenModal(role:any, mode:string):void{
    const container=document.getElementById('main-container')
    const button = document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-toggle','modal');
    if(mode === 'add'){
      button.setAttribute('data-target','#addRoleModal');
    }
    if(mode === 'edit'){
      this.editRole = role;
      button.setAttribute('data-target','#editRoleModal');
    }
    if(mode === 'delete'){
      this.deleteRole = role;
      button.setAttribute('data-target','#deleteRoleModal');
    }

    container?.appendChild(button);
    button.click();
  }
}



