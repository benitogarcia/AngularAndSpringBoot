import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isLogged = false;
  username: string;
  roles: string[];
  isAdmin = false;

  constructor(private tokenService: TokenService, private router: Router) { }

  ngOnInit(): void {

    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.username = this.tokenService.getUsername();
    }

    this.roles = this.tokenService.getAuthorities();
    this.roles.forEach(rol => {
      if (rol === "ROLE_ADMIN") {
        this.isAdmin = true;
      }
    });

  }

  onLogOut(): void {
    this.tokenService.logOut();
    window.localStorage.clear();
    window.sessionStorage.clear();
    this.router.navigate(['/login']);
  }

}
