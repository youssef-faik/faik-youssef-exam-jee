import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Remboursement, RemboursementService } from '../../../services/remboursement.service';
import { Credit, CreditService } from '../../../services/credit.service';

@Component({
  selector: 'app-remboursement-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './remboursement-detail.component.html',
  styleUrls: ['./remboursement-detail.component.css']
})
export class RemboursementDetailComponent implements OnInit {
  remboursement: Remboursement | null = null;
  credit: Credit | null = null;
  loading = true;
  savingRemboursement = false;
  error = '';
  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private remboursementService: RemboursementService,
    private creditService: CreditService
  ) { }

  ngOnInit(): void {
    this.getRemboursementDetails();
  }

  getRemboursementDetails(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loading = true;

    this.remboursementService.getRemboursement(id)
      .subscribe({
        next: (remboursement) => {
          this.remboursement = remboursement;
          this.getCreditDetails(remboursement.creditId);
        },
        error: (error) => {
          this.error = 'Failed to load remboursement details. Please try again later.';
          this.loading = false;
          console.error('Error loading remboursement:', error);
        }
      });
  }

  getCreditDetails(creditId: number): void {
    this.creditService.getCredit(creditId)
      .subscribe({
        next: (credit) => {
          this.credit = credit;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load credit details. Please try again later.';
          this.loading = false;
          console.error('Error loading credit:', error);
        }
      });
  }

  saveRemboursement(): void {
    if (!this.remboursement) return;

    this.savingRemboursement = true;
    this.remboursementService.updateRemboursement(this.remboursement)
      .subscribe({
        next: (updatedRemboursement) => {
          this.remboursement = updatedRemboursement;
          this.savingRemboursement = false;
          this.editMode = false;
          this.error = '';
        },
        error: (error) => {
          this.error = 'Failed to update remboursement. Please try again.';
          this.savingRemboursement = false;
          console.error('Error updating remboursement:', error);
        }
      });
  }

  deleteRemboursement(): void {
    if (!this.remboursement || !this.remboursement.id) return;

    if (confirm('Are you sure you want to delete this remboursement?')) {
      this.remboursementService.deleteRemboursement(this.remboursement.id)
        .subscribe({
          next: () => {
            this.router.navigate(['/remboursements']);
          },
          error: (error) => {
            this.error = 'Failed to delete remboursement. Please try again.';
            console.error('Error deleting remboursement:', error);
          }
        });
    }
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      this.getRemboursementDetails(); // Refresh data when canceling edit
    }
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

  getCreditStatusClass(status: string | undefined): string {
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
}
