import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = { username: '', password: '' };
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log(response.token); //currently undefined
        localStorage.setItem('token', response.token);
        this.router.navigate(['/user-list']);
        this.errorMessage = null; // Clear any previous error messages
      },
      error: (error) => {
        this.errorMessage = error?.error?.message || 'Invalid credentials';
        console.error('Login error:', error);
      }
    });
  }
}
