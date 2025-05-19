import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClientService, Client } from '../../../services/client.service';

@Component({
  selector: 'app-client-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {
  client: Client | null = null;
  isLoading = true;
  errorMessage = '';
  isEditing = false;

  // For the form
  editedClient: Client = { name: '', email: '' };

  // Mock data for credits
  clientCredits = [
    { id: 201, amount: 10000, date: '2025-03-15', status: 'APPROVED', type: 'PERSONNEL' },
    { id: 202, amount: 25000, date: '2025-04-20', status: 'PENDING', type: 'IMMOBILIER' },
    { id: 203, amount: 5000, date: '2025-05-10', status: 'REJECTED', type: 'PERSONNEL' }
  ];

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService
  ) { }

  ngOnInit(): void {
    this.getClient();
  }

  getClient(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(id)) {
      this.errorMessage = 'Invalid client ID.';
      this.isLoading = false;
      return;
    }

    // For demo purposes, using mock data
    // In real app, use this.clientService.getClient(id)
    setTimeout(() => {
      // Mock client
      this.client = {
        id,
        name: 'John Doe',
        email: 'john.doe@example.com'
      };
      this.editedClient = { ...this.client };
      this.isLoading = false;
    }, 1000);

    // In real app:
    // this.clientService.getClient(id)
    //   .subscribe(
    //     client => {
    //       this.client = client;
    //       this.editedClient = { ...client };
    //       this.isLoading = false;
    //     },
    //     error => {
    //       this.errorMessage = 'Failed to load client details. Please try again later.';
    //       this.isLoading = false;
    //       console.error('Error loading client:', error);
    //     }
    //   );
  }

  toggleEditMode(): void {
    this.isEditing = !this.isEditing;
    if (this.client) {
      this.editedClient = { ...this.client };
    }
  }

  saveChanges(): void {
    if (!this.client) return;

    // In real app:
    // this.clientService.updateClient(this.editedClient)
    //   .subscribe(
    //     () => {
    //       this.client = { ...this.editedClient };
    //       this.isEditing = false;
    //     },
    //     error => {
    //       console.error('Error updating client:', error);
    //       // Handle error
    //     }
    //   );

    // For demo:
    this.client = { ...this.editedClient };
    this.isEditing = false;
  }

  deleteClient(): void {
    if (!this.client) return;

    if (confirm(`Are you sure you want to delete client ${this.client.name}?`)) {
      // In real app:
      // this.clientService.deleteClient(this.client.id!)
      //   .subscribe(
      //     () => {
      //       // Navigate back to clients list
      //       // this.router.navigate(['/clients']);
      //     },
      //     error => {
      //       console.error('Error deleting client:', error);
      //       // Handle error
      //     }
      //   );

      // For demo, just log
      console.log('Client deleted:', this.client);
      // Normally would navigate back to list view
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'APPROVED': return 'badge bg-success';
      case 'PENDING': return 'badge bg-warning text-dark';
      case 'REJECTED': return 'badge bg-danger';
      default: return 'badge bg-secondary';
    }
  }

  getTypeClass(type: string): string {
    switch (type) {
      case 'PERSONNEL': return 'badge bg-info text-dark';
      case 'IMMOBILIER': return 'badge bg-primary';
      case 'PROFESSIONNEL': return 'badge bg-secondary';
      default: return 'badge bg-light text-dark';
    }
  }
}
