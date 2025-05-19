import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // Mock data for the dashboard
  clientStats = {
    total: 32,
    active: 28,
    new: 5
  };

  creditStats = {
    total: 45,
    pending: 8,
    approved: 32,
    rejected: 5
  };

  remboursementStats = {
    total: 128,
    due: 12,
    overdue: 3
  };

  recentCredits = [
    { id: 101, clientName: 'John Doe', amount: 12000, type: 'PERSONNEL', status: 'APPROVED', date: '2025-05-15' },
    { id: 102, clientName: 'Jane Smith', amount: 50000, type: 'IMMOBILIER', status: 'PENDING', date: '2025-05-17' },
    { id: 103, clientName: 'Robert Johnson', amount: 8000, type: 'PERSONNEL', status: 'APPROVED', date: '2025-05-18' },
    { id: 104, clientName: 'Emily Davis', amount: 25000, type: 'PROFESSIONNEL', status: 'REJECTED', date: '2025-05-12' }
  ];

  recentRemboursements = [
    { id: 201, clientName: 'John Doe', creditId: 101, amount: 1000, type: 'PAYMENT', date: '2025-05-10' },
    { id: 202, clientName: 'Jane Smith', creditId: 98, amount: 2500, type: 'PAYMENT', date: '2025-05-15' },
    { id: 203, clientName: 'Robert Johnson', creditId: 103, amount: 800, type: 'PARTIAL_PAYMENT', date: '2025-05-16' }
  ];

  constructor() {}

  ngOnInit(): void {
    // In a real application, these would be loaded from a service
    // this.loadDashboardData();
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'APPROVED': return 'text-success';
      case 'PENDING': return 'text-warning';
      case 'REJECTED': return 'text-danger';
      default: return '';
    }
  }

  getTypeClass(type: string): string {
    switch (type) {
      case 'PERSONNEL': return 'bg-info';
      case 'IMMOBILIER': return 'bg-primary';
      case 'PROFESSIONNEL': return 'bg-secondary';
      default: return 'bg-light';
    }
  }

  // loadDashboardData(): void {
  //   // TODO: Load real data from API service
  // }
}
