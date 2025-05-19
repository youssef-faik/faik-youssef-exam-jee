import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Client {
  id?: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8081/api/v1/clients';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  // Get all clients
  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl)
      .pipe(
        tap(_ => console.log('Fetched clients')),
        catchError(this.handleError<Client[]>('getClients', []))
      );
  }

  // Get client by id
  getClient(id: number): Observable<Client> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Client>(url)
      .pipe(
        tap(_ => console.log(`Fetched client id=${id}`)),
        catchError(this.handleError<Client>(`getClient id=${id}`))
      );
  }

  // Add new client
  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(this.apiUrl, client, this.httpOptions)
      .pipe(
        tap((newClient: Client) => console.log(`Added client w/ id=${newClient.id}`)),
        catchError(this.handleError<Client>('addClient'))
      );
  }

  // Update client
  updateClient(client: Client): Observable<any> {
    const url = `${this.apiUrl}/${client.id}`;
    return this.http.put(url, client, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Updated client id=${client.id}`)),
        catchError(this.handleError<any>('updateClient'))
      );
  }

  // Delete client
  deleteClient(id: number): Observable<Client> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<Client>(url, this.httpOptions)
      .pipe(
        tap(_ => console.log(`Deleted client id=${id}`)),
        catchError(this.handleError<Client>('deleteClient'))
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
