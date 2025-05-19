import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Credit {
  id?: number;
  amount: number;
  date?: string;
  status?: string;
  clientId?: number;
  typeCredit?: string;
  motif?: string;
  bienType?: string;
  raisonSociale?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CreditService {
  private apiUrl = 'http://localhost:8081/api/v1/credits';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  // Get all credits
  getCredits(status?: string): Observable<Credit[]> {
    const url = status ? `${this.apiUrl}?status=${status}` : this.apiUrl;
    return this.http.get<Credit[]>(url)
      .pipe(
        tap(_ => console.log('Fetched credits')),
        catchError(this.handleError<Credit[]>('getCredits', []))
      );
  }

  // Get credit by id
  getCredit(id: number): Observable<Credit> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Credit>(url)
      .pipe(
        tap(_ => console.log(`Fetched credit id=${id}`)),
        catchError(this.handleError<Credit>(`getCredit id=${id}`))
      );
  }

  // Get credits by client id
  getCreditsByClientId(clientId: number): Observable<Credit[]> {
    const url = `${this.apiUrl}/clients/${clientId}`;
    return this.http.get<Credit[]>(url)
      .pipe(
        tap(_ => console.log(`Fetched credits for client id=${clientId}`)),
        catchError(this.handleError<Credit[]>(`getCreditsByClientId clientId=${clientId}`, []))
      );
  }

  // Add new credit for a client
  addCredit(credit: Credit, clientId: number): Observable<Credit> {
    const url = `${this.apiUrl}/clients/${clientId}`;
    return this.http.post<Credit>(url, credit, this.httpOptions)
      .pipe(
        tap((newCredit: Credit) => console.log(`Added credit w/ id=${newCredit.id}`)),
        catchError(this.handleError<Credit>('addCredit'))
      );
  }

  // Update credit
  updateCredit(credit: Credit): Observable<any> {
    const url = `${this.apiUrl}/${credit.id}`;
    return this.http.put(url, credit, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Updated credit id=${credit.id}`)),
        catchError(this.handleError<any>('updateCredit'))
      );
  }

  // Delete credit
  deleteCredit(id: number): Observable<Credit> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<Credit>(url, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Deleted credit id=${id}`)),
        catchError(this.handleError<Credit>('deleteCredit'))
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
