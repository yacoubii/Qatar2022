import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { error } from 'selenium-webdriver';
import { Role } from 'src/app/Models/role';
import { User } from 'src/app/Models/user';
import { RoleService } from 'src/app/Services/role.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
  role: any;
  roleId:any;
  userId:any;
  users: User[] = [];
  roles: Role[] = [];
  rolesToAdd: Role[] = [];
  rolesToUpdate: Role[] = [];
  editUser!: User;
  deleteUser: any;
  constructor(private userService:UserService,private roleService:RoleService) { }

  ngOnInit(): void {
    this.getUsers();
    this.getRoles();
    console.log(this.users)
  }

  public delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  public getUsers(): void {
    this.userService.getUsers().subscribe((response:User[])=>{
      response.forEach((user: any) =>{
        if(!user.hasOwnProperty("cvv")){
          this.users.push(user)
        }
      } )
    },
    (error:HttpErrorResponse)=>{
      console.error(error.message);
    })
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe((response:Role[])=>{
      this.roles=response;
    },
    (error:HttpErrorResponse)=>{
      console.error(error.message);
    })
  }

  public onAddUser(addForm: NgForm):void{
    document.getElementById('add-user-form')?.click();
        this.rolesToAdd=addForm.value.roles;
        
        /*console.log("******to add:********");
        console.log(addForm.value);

        console.log("******roles to add:********");
        console.log(this.rolesToAdd);*/

        delete addForm.value['roles'];
        this.userService.addUser(addForm.value).subscribe(
          (response:any) =>{
            this.userId=response.id
            if (this.rolesToAdd) {
              this.userService
                .clearRoles(this.userId)
                .subscribe(async (response: any) => {
                  console.log(response);
                  await this.delay(1000);
                  this.rolesToAdd.forEach((roleId: any) => {
                    this.roleService
                      .linkNewUserToRole(this.userId, roleId)
                      .subscribe((response: any) => {
                        console.log(response);
                      });
               });
              
              });
  
            }
          },
          (error:HttpErrorResponse)=>{
            console.error(error.message);
          })
        }

  public OnUpdateUser(user: User):void{
    this.rolesToUpdate=user.roles;
    delete user['roles'];

    this.userService.updateUser(user,user.id).subscribe(
      (response:any) =>{
        console.log(response);
        if (this.rolesToUpdate) {
          this.userService
            .clearRoles(user.id)
            .subscribe(async (response: any) => {
              console.log(response);
              await this.delay(1000);
              this.rolesToUpdate.forEach((roleId: any) => {
                this.roleService
                  .linkNewUserToRole(user.id, roleId)
                  .subscribe((response: any) => {
                    console.log(response);
                  });
           });
           await this.delay(1000);
           window.location.reload()
          });
        }
        
      },
      (error:HttpErrorResponse)=>{
        console.error(error.message);
      }
    )

  }


  public OnDeleteUser(userId: number):void{
    this.userService.deleteUser(userId).subscribe(
      (response:any) =>{
        console.log(response);
        window.location.reload()
      },
      (error:HttpErrorResponse)=>{
        console.error(error.message);
      }
    )
  }

  public onOpenModal(user:any, mode:string):void{
    const container=document.getElementById('main-container')
    const button = document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-toggle','modal');
    if(mode === 'add'){
      button.setAttribute('data-target','#addUserModal');
    }
    if(mode === 'edit'){
      this.editUser = user;
      button.setAttribute('data-target','#editUserModal');
    }
    if(mode === 'delete'){
      this.deleteUser = user;
      button.setAttribute('data-target','#deleteUserModal');
    }

    container?.appendChild(button);
    button.click();
  }
}
