import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  sessionActive = false;
  username = "";

  constructor(private tokenService: TokenService, private router: Router) { }

  ngOnInit(): void {
    if (!this.tokenService.getToken()) {
      this.router.navigate(['/login']);   
    } else {
      this.sessionActive = true;
      this.username = this.tokenService.getUsername();   
    }
  }

}
