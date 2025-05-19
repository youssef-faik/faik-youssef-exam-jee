import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ClientService, Client } from '../../../services/client.service';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  isLoading = true;
  errorMessage = '';

  // For client form
  showForm = false;
  editMode = false;
  currentClient: Client = { name: '', email: '' };

  // For search and filter
  searchTerm = '';
  filteredClients: Client[] = [];

  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.getClients();
  }

  getClients(): void {
    this.isLoading = true;

    // For demo purposes, using mock data
    // In real app, use this.clientService.getClients()
    setTimeout(() => {
      // Mock data
      this.clients = [
        { id: 1, name: 'John Doe', email: 'john.doe@example.com' },
        { id: 2, name: 'Jane Smith', email: 'jane.smith@example.com' },
        { id: 3, name: 'Robert Johnson', email: 'robert.johnson@example.com' },
        { id: 4, name: 'Emily Davis', email: 'emily.davis@example.com' },
        { id: 5, name: 'Michael Wilson', email: 'michael.wilson@example.com' }
      ];
      this.filteredClients = [...this.clients];
      this.isLoading = false;
    }, 1000);

    // In real app:
    // this.clientService.getClients()
    //   .subscribe(
    //     clients => {
    //       this.clients = clients;
    //       this.filteredClients = [...this.clients];
    //       this.isLoading = false;
    //     },
    //     error => {
    //       this.errorMessage = 'Failed to load clients. Please try again later.';
    //       this.isLoading = false;
    //       console.error('Error loading clients:', error);
    //     }
    //   );
  }

  toggleForm(edit = false, client?: Client): void {
    this.showForm = !this.showForm;
    this.editMode = edit;

    if (edit && client) {
      this.currentClient = { ...client };
    } else {
      this.currentClient = { name: '', email: '' };
    }
  }

  saveClient(): void {
    if (this.editMode && this.currentClient.id) {
      // Update existing client
      console.log('Updating client:', this.currentClient);
      // In real app:
      // this.clientService.updateClient(this.currentClient)
      //   .subscribe(() => {
      //     this.getClients();
      //     this.showForm = false;
      //   });

      // For demo:
      const index = this.clients.findIndex(c => c.id === this.currentClient.id);
      if (index !== -1) {
        this.clients[index] = { ...this.currentClient };
        this.filteredClients = this.applyFilter();
      }
      this.showForm = false;
    } else {
      // Add new client
      console.log('Adding client:', this.currentClient);
      // In real app:
      // this.clientService.addClient(this.currentClient)
      //   .subscribe(client => {
      //     this.clients.push(client);
      //     this.filteredClients = this.applyFilter();
      //     this.showForm = false;
      //   });

      // For demo:
      const newId = Math.max(...this.clients.map(c => c.id || 0)) + 1;
      const newClient = { ...this.currentClient, id: newId };
      this.clients.push(newClient);
      this.filteredClients = this.applyFilter();
      this.showForm = false;
    }
  }

  deleteClient(client: Client): void {
    if (confirm(`Are you sure you want to delete client ${client.name}?`)) {
      console.log('Deleting client:', client);
      // In real app:
      // this.clientService.deleteClient(client.id!)
      //   .subscribe(() => {
      //     this.clients = this.clients.filter(c => c.id !== client.id);
      //     this.filteredClients = this.applyFilter();
      //   });

      // For demo:
      this.clients = this.clients.filter(c => c.id !== client.id);
      this.filteredClients = this.applyFilter();
    }
  }

  search(): void {
    this.filteredClients = this.applyFilter();
  }

  private applyFilter(): Client[] {
    if (!this.searchTerm.trim()) {
      return [...this.clients];
    }

    const term = this.searchTerm.toLowerCase();
    return this.clients.filter(client =>
      client.name.toLowerCase().includes(term) ||
      client.email.toLowerCase().includes(term)
    );
  }
}
