import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Credit, CreditService } from '../../../services/credit.service';

@Component({
  selector: 'app-credit-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.css']
})
export class CreditListComponent implements OnInit {
  credits: Credit[] = [];
  filteredCredits: Credit[] = [];
  loading = true;
  error = '';
  searchTerm = '';
  statusFilter = '';
  typeCreditFilter = '';

  constructor(private creditService: CreditService) { }

  ngOnInit(): void {
    this.getCredits();
  }

  getCredits(): void {
    this.loading = true;
    this.creditService.getCredits()
      .subscribe({
        next: (credits) => {
          this.credits = credits;
          this.filteredCredits = credits;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load credits. Please try again later.';
          this.loading = false;
          console.error('Error loading credits:', error);
        }
      });
  }

  applyFilters(): void {
    this.filteredCredits = this.credits.filter(credit => {
      // Apply search filter
      const searchMatch = !this.searchTerm ||
                         (credit.id?.toString().includes(this.searchTerm) ||
                          credit.amount.toString().includes(this.searchTerm) ||
                          credit.clientId?.toString().includes(this.searchTerm) ||
                          (credit.motif && credit.motif.toLowerCase().includes(this.searchTerm.toLowerCase())));

      // Apply status filter
      const statusMatch = !this.statusFilter || credit.status === this.statusFilter;

      // Apply type filter
      const typeMatch = !this.typeCreditFilter || credit.typeCredit === this.typeCreditFilter;

      return searchMatch && statusMatch && typeMatch;
    });
  }

  onSearch(): void {
    this.applyFilters();
  }

  onStatusChange(): void {
    this.applyFilters();
  }

  onTypeChange(): void {
    this.applyFilters();
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.statusFilter = '';
    this.typeCreditFilter = '';
    this.filteredCredits = this.credits;
  }

  deleteCredit(id: number): void {
    if (confirm('Are you sure you want to delete this credit?')) {
      this.creditService.deleteCredit(id)
        .subscribe({
          next: () => {
            this.credits = this.credits.filter(c => c.id !== id);
            this.filteredCredits = this.filteredCredits.filter(c => c.id !== id);
          },
          error: (error) => {
            this.error = 'Failed to delete credit. Please try again.';
            console.error('Error deleting credit:', error);
          }
        });
    }
  }

  getStatusClass(status: string | undefined): string {
    if (!status) return '';

    switch (status) {
      case 'APPROVED':
        return 'bg-success';
      case 'PENDING':
        return 'bg-warning';
      case 'REJECTED':
        return 'bg-danger';
      default:
        return '';
    }
  }

  getTypeLabel(type: string | undefined): string {
    if (!type) return '';

    switch (type) {
      case 'PERSONNEL':
        return 'Personal';
      case 'IMMOBILIER':
        return 'Real Estate';
      case 'PROFESSIONNEL':
        return 'Professional';
      default:
        return type;
    }
  }
}
