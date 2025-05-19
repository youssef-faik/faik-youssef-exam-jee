import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="not-found-container">
      <div class="not-found-content">
        <i class="fas fa-exclamation-triangle not-found-icon"></i>
        <h1>404</h1>
        <h2>Page Not Found</h2>
        <p>The page you are looking for doesn't exist or has been moved.</p>
        <a routerLink="/" class="btn btn-primary">Go to Homepage</a>
      </div>
    </div>
  `,
  styles: [`
    .not-found-container {
      display: flex;
      justify-content: center;
      align-items: center;
      height: calc(100vh - 150px);
      text-align: center;
    }

    .not-found-content {
      max-width: 500px;
      padding: 40px;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    }

    .not-found-icon {
      font-size: 5rem;
      color: #f8bb86;
      margin-bottom: 20px;
    }

    h1 {
      font-size: 6rem;
      margin: 0;
      color: var(--primary-color);
      line-height: 1;
    }

    h2 {
      font-weight: 600;
      margin-bottom: 20px;
      color: #333;
    }

    p {
      margin-bottom: 30px;
      color: #666;
    }
  `]
})
export class NotFoundComponent {}
