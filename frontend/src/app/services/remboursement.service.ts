import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Remboursement {
  id?: number;
  amount: number;
  date: string;
  type?: string;
  creditId: number;
}

@Injectable({
  providedIn: 'root'
})
export class RemboursementService {
  private apiUrl = 'http://localhost:8081/api/v1/remboursements';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  // Get all remboursements
  getRemboursements(type?: string): Observable<Remboursement[]> {
    const url = type ? `${this.apiUrl}?type=${type}` : this.apiUrl;
    return this.http.get<Remboursement[]>(url)
      .pipe(
        tap(_ => console.log('Fetched remboursements')),
        catchError(this.handleError<Remboursement[]>('getRemboursements', []))
      );
  }

  // Get remboursement by id
  getRemboursement(id: number): Observable<Remboursement> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Remboursement>(url)
      .pipe(
        tap(_ => console.log(`Fetched remboursement id=${id}`)),
        catchError(this.handleError<Remboursement>(`getRemboursement id=${id}`))
      );
  }

  // Get remboursements by credit id
  getRemboursementsByCreditId(creditId: number): Observable<Remboursement[]> {
    const url = `${this.apiUrl}/credits/${creditId}`;
    return this.http.get<Remboursement[]>(url)
      .pipe(
        tap(_ => console.log(`Fetched remboursements for credit id=${creditId}`)),
        catchError(this.handleError<Remboursement[]>(`getRemboursementsByCreditId creditId=${creditId}`, []))
      );
  }

  // Add new remboursement for a credit
  addRemboursement(remboursement: Remboursement, creditId: number): Observable<Remboursement> {
    const url = `${this.apiUrl}/credits/${creditId}`;
    return this.http.post<Remboursement>(url, remboursement, this.httpOptions)
      .pipe(
        tap((newRemboursement: Remboursement) => console.log(`Added remboursement w/ id=${newRemboursement.id}`)),
        catchError(this.handleError<Remboursement>('addRemboursement'))
      );
  }

  // Update remboursement
  updateRemboursement(remboursement: Remboursement): Observable<any> {
    const url = `${this.apiUrl}/${remboursement.id}`;
    return this.http.put(url, remboursement, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Updated remboursement id=${remboursement.id}`)),
        catchError(this.handleError<any>('updateRemboursement'))
      );
  }

  // Delete remboursement
  deleteRemboursement(id: number): Observable<Remboursement> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<Remboursement>(url, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Deleted remboursement id=${id}`)),
        catchError(this.handleError<Remboursement>('deleteRemboursement'))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
