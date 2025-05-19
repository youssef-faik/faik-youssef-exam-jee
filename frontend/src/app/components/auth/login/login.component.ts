import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = {
    username: '',
    password: ''
  };
  isLoading = false;
  errorMessage = '';

  constructor() {}

  onSubmit(): void {
    this.isLoading = true;
    this.errorMessage = '';

    // TODO: Implement actual login logic with API
    // For now, just simulate a login process
    setTimeout(() => {
      if (this.loginForm.username && this.loginForm.password) {
        // TODO: Call authentication service
        console.log('Login attempt with:', this.loginForm);
        // Navigate to dashboard after successful login
        // this.router.navigate(['/dashboard']);
      } else {
        this.errorMessage = 'Please enter both username and password.';
      }
      this.isLoading = false;
    }, 1000);
  }
}
