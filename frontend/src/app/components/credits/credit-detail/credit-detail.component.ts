import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Credit, CreditService } from '../../../services/credit.service';
import { Remboursement, RemboursementService } from '../../../services/remboursement.service';

@Component({
  selector: 'app-credit-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './credit-detail.component.html',
  styleUrls: ['./credit-detail.component.css']
})
export class CreditDetailComponent implements OnInit {
  credit: Credit | null = null;
  remboursements: Remboursement[] = [];
  loading = true;
  savingCredit = false;
  savingRemboursement = false;
  error = '';
  editMode = false;

  newRemboursement: Remboursement = {
    amount: 0,
    date: new Date().toISOString().split('T')[0],
    type: 'PAYMENT',
    creditId: 0
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private creditService: CreditService,
    private remboursementService: RemboursementService
  ) { }

  ngOnInit(): void {
    this.getCreditDetails();
  }

  getCreditDetails(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loading = true;

    this.creditService.getCredit(id)
      .subscribe({
        next: (credit) => {
          this.credit = credit;
          this.newRemboursement.creditId = credit.id || 0;
          this.getRemboursements(id);
        },
        error: (error) => {
          this.error = 'Failed to load credit details. Please try again later.';
          this.loading = false;
          console.error('Error loading credit:', error);
        }
      });
  }

  getRemboursements(creditId: number): void {
    this.remboursementService.getRemboursementsByCreditId(creditId)
      .subscribe({
        next: (remboursements) => {
          this.remboursements = remboursements;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load remboursements. Please try again later.';
          this.loading = false;
          console.error('Error loading remboursements:', error);
        }
      });
  }

  saveCredit(): void {
    if (!this.credit) return;

    this.savingCredit = true;
    this.creditService.updateCredit(this.credit)
      .subscribe({
        next: (updatedCredit) => {
          this.credit = updatedCredit;
          this.savingCredit = false;
          this.editMode = false;
          this.error = '';
        },
        error: (error) => {
          this.error = 'Failed to update credit. Please try again.';
          this.savingCredit = false;
          console.error('Error updating credit:', error);
        }
      });
  }

  addRemboursement(): void {
    if (!this.credit || !this.credit.id) return;

    this.savingRemboursement = true;
    this.remboursementService.addRemboursement(this.newRemboursement, this.credit.id)
      .subscribe({
        next: (remboursement) => {
          this.remboursements.push(remboursement);
          this.resetNewRemboursement();
          this.savingRemboursement = false;
          this.error = '';
        },
        error: (error) => {
          this.error = 'Failed to add remboursement. Please try again.';
          this.savingRemboursement = false;
          console.error('Error adding remboursement:', error);
        }
      });
  }

  deleteRemboursement(id: number): void {
    if (confirm('Are you sure you want to delete this remboursement?')) {
      this.remboursementService.deleteRemboursement(id)
        .subscribe({
          next: () => {
            this.remboursements = this.remboursements.filter(r => r.id !== id);
          },
          error: (error) => {
            this.error = 'Failed to delete remboursement. Please try again.';
            console.error('Error deleting remboursement:', error);
          }
        });
    }
  }

  resetNewRemboursement(): void {
    this.newRemboursement = {
      amount: 0,
      date: new Date().toISOString().split('T')[0],
      type: 'PAYMENT',
      creditId: this.credit?.id || 0
    };
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      this.getCreditDetails(); // Refresh data when canceling edit
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

  getTotalRepaid(): number {
    return this.remboursements.reduce((total, r) => total + r.amount, 0);
  }

  getRemaining(): number {
    const total = this.credit?.amount || 0;
    const repaid = this.getTotalRepaid();
    return total - repaid;
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
}
