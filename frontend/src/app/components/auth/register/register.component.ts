import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    name: ''
  };
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor() {}

  onSubmit(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    // Basic validation
    if (this.registerForm.password !== this.registerForm.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      this.isLoading = false;
      return;
    }

    // TODO: Implement actual registration logic with API
    // For now, just simulate a registration process
    setTimeout(() => {
      if (this.registerForm.username && this.registerForm.email && this.registerForm.password && this.registerForm.name) {
        // TODO: Call authentication service
        console.log('Registration attempt with:', {
          username: this.registerForm.username,
          email: this.registerForm.email,
          password: this.registerForm.password,
          clientInfo: {
            name: this.registerForm.name,
            email: this.registerForm.email
          }
        });

        this.successMessage = 'Registration successful! You can now login.';
        // Reset form
        this.registerForm = {
          username: '',
          email: '',
          password: '',
          confirmPassword: '',
          name: ''
        };
      } else {
        this.errorMessage = 'Please fill in all the required fields.';
      }
      this.isLoading = false;
    }, 1000);
  }
}
