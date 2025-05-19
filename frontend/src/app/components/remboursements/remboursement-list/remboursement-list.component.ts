import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Remboursement, RemboursementService } from '../../../services/remboursement.service';
import { Credit, CreditService } from '../../../services/credit.service';

@Component({
  selector: 'app-remboursement-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './remboursement-list.component.html',
  styleUrls: ['./remboursement-list.component.css']
})
export class RemboursementListComponent implements OnInit {
  remboursements: Remboursement[] = [];
  filteredRemboursements: Remboursement[] = [];
  credits: Map<number, Credit> = new Map();
  loading = true;
  error = '';
  searchTerm = '';
  typeFilter = '';
  dateFilter = '';
  creditFilter = '';
  startDate = '';
  endDate = '';

  constructor(
    private remboursementService: RemboursementService,
    private creditService: CreditService
  ) { }

  ngOnInit(): void {
    this.getRemboursements();
  }

  getRemboursements(): void {
    this.loading = true;
    this.remboursementService.getRemboursements()
      .subscribe({
        next: (remboursements) => {
          this.remboursements = remboursements;
          this.filteredRemboursements = remboursements;
          this.loading = false;

          // Get credit details for each remboursement
          const creditIds = new Set(remboursements.map(r => r.creditId));
          creditIds.forEach(id => this.fetchCreditDetails(id));
        },
        error: (error) => {
          this.error = 'Failed to load remboursements. Please try again later.';
          this.loading = false;
          console.error('Error loading remboursements:', error);
        }
      });
  }

  fetchCreditDetails(creditId: number): void {
    this.creditService.getCredit(creditId)
      .subscribe({
        next: (credit) => {
          this.credits.set(creditId, credit);
        },
        error: (error) => {
          console.error(`Error loading credit ${creditId}:`, error);
        }
      });
  }

  applyFilters(): void {
    this.filteredRemboursements = this.remboursements.filter(remboursement => {
      // Apply search filter
      const searchMatch = !this.searchTerm ||
                         (remboursement.id?.toString().includes(this.searchTerm) ||
                          remboursement.amount.toString().includes(this.searchTerm) ||
                          remboursement.creditId.toString().includes(this.searchTerm));

      // Apply type filter
      const typeMatch = !this.typeFilter || remboursement.type === this.typeFilter;

      // Apply credit filter
      const creditMatch = !this.creditFilter || remboursement.creditId.toString() === this.creditFilter;

      // Apply date range filter
      let dateMatch = true;
      if (this.startDate && this.endDate) {
        const remboursementDate = new Date(remboursement.date);
        const start = new Date(this.startDate);
        const end = new Date(this.endDate);
        end.setHours(23, 59, 59); // Include the entire end day
        dateMatch = remboursementDate >= start && remboursementDate <= end;
      }

      return searchMatch && typeMatch && creditMatch && dateMatch;
    });
  }

  onSearch(): void {
    this.applyFilters();
  }

  onTypeChange(): void {
    this.applyFilters();
  }

  onCreditChange(): void {
    this.applyFilters();
  }

  onDateChange(): void {
    this.applyFilters();
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.typeFilter = '';
    this.creditFilter = '';
    this.startDate = '';
    this.endDate = '';
    this.filteredRemboursements = this.remboursements;
  }

  deleteRemboursement(id: number): void {
    if (confirm('Are you sure you want to delete this remboursement?')) {
      this.remboursementService.deleteRemboursement(id)
        .subscribe({
          next: () => {
            this.remboursements = this.remboursements.filter(r => r.id !== id);
            this.filteredRemboursements = this.filteredRemboursements.filter(r => r.id !== id);
          },
          error: (error) => {
            this.error = 'Failed to delete remboursement. Please try again.';
            console.error('Error deleting remboursement:', error);
          }
        });
    }
  }

  getCreditInfo(creditId: number): string {
    const credit = this.credits.get(creditId);
    if (credit) {
      return `Credit #${creditId} (${credit.amount} ${credit.typeCredit})`;
    }
    return `Credit #${creditId}`;
  }

  getRemboursementTypeLabel(type: string | undefined): string {
    if (!type) return '';

    switch (type) {
      case 'PAYMENT':
        return 'Regular Payment';
      case 'EARLY_PAYMENT':
        return 'Early Payment';
      case 'PARTIAL_PAYMENT':
        return 'Partial Payment';
      default:
        return type;
    }
  }

  getTypeClass(type: string | undefined): string {
    if (!type) return '';

    switch (type) {
      case 'PAYMENT':
        return 'bg-success';
      case 'EARLY_PAYMENT':
        return 'bg-info';
      case 'PARTIAL_PAYMENT':
        return 'bg-warning';
      default:
        return '';
    }
  }

  getUniqueCredits(): number[] {
    return [...new Set(this.remboursements.map(r => r.creditId))];
  }
}
