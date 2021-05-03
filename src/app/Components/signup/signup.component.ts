import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/Services/user.service';
import { RoleService } from 'src/app/Services/role.service';
import { Role } from 'src/app/Models/role';
import { User } from 'src/app/Models/user';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';
import { TokenStorageService } from 'src/app/Services/token-storage.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  role: any;
  
  roleId:any;
  userId:any;
  users: User[] = [];
  roles: Role[] = [];
  rolesToAdd: Role[] = [];
  rolesToUpdate: Role[] = [];
  editUser!: User;
  deleteUser: any;
  isLoginFailed: any=false;
  isLoggedIn: any;
  constructor(private userService:UserService,private roleService:RoleService,private tokenStorage:TokenStorageService ,private authService:AuthService,private router: Router) { }


  ngOnInit(): void {
    this.getRoles();
    if (this.tokenStorage.getToken()) {
      this.router.navigate(['/match']).then(() => {
        window.location.reload();
      });
    }
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe((response:Role[])=>{
      this.roles=response;
    },
    (error:HttpErrorResponse)=>{
      console.error(error.message);
    })
  }

  
  public onAddUser(addForm: NgForm): void {
    document.getElementById('add-user-form')?.click();
    this.rolesToAdd=addForm.value.roles;
    delete addForm.value['roles'];

    this.userService.addUser(addForm.value).subscribe(
      (response: any) =>{
                    this.userId=response.id
                    this.roleService
                    .linkNewUserToRole(this.userId, 1)
                    .subscribe((response: any) => {
                      console.log(response);
                    });

        this.router.navigateByUrl('/');
        addForm.reset();
      },
      (error: HttpErrorResponse) =>{
        alert(error.message);
        addForm.reset();

      }
    );

  }

  public onLoginUser(loginForm: NgForm): void {
    this.authService.login(loginForm.value).subscribe(
      (response: any) =>{
        this.tokenStorage.saveToken(response.accessToken);
        this.tokenStorage.saveUser(response);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.router.navigate(['/match']).then(() => {
          window.location.reload();
        });
      },
      (error: HttpErrorResponse) =>{
        this.isLoginFailed = true;
      }
    );

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
    if(mode === 'login'){
      button.setAttribute('data-target','#loginUserModal');
    }

    container?.appendChild(button);
    button.click();
  }

}
