import { Component, OnDestroy, OnInit } from '@angular/core';
import { ITransaction } from './transaction/transaction';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subscription, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Component({
  selector: 'finance-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Transactions';
  merchantValue = '';
  costValue = '';
  transactions: ITransaction[] = [];
  errorMessage: string = '';
  subscriptions: Subscription = new Subscription();

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.subscriptions.add(this.getAllTransactions().subscribe({
      next: transactions => this.transactions = transactions,
      error: err => this.errorMessage = err
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  getAllTransactions(): Observable<ITransaction[]>{
    return this.http.get<ITransaction[]>("http://localhost:8080/api/v1/transaction/getAllTransactions").pipe(
      tap(data => console.log('All', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  submitTransaction(merchant: string, cost: string): void {
    const headers = {'Content-Type': 'application/json'};
    const body = {"merchant": merchant,  "cost": cost};
    this.subscriptions.add(this.http.post("http://localhost:8080/api/v1/transaction/insertTransaction", body, { headers }).subscribe());
    this.merchantValue = '';
    this.costValue = '';
    this.reloadPage();
  }
  updateTransaction(transactionId: string, merchant: string, cost: string): void {
    const headers = {'Content-Type': 'application/json'};
    const body = {"merchant": merchant,  "cost": cost};
    this.subscriptions.add(this.http.put("http://localhost:8080/api/v1/transaction/" + transactionId, body, { headers }).subscribe());
  }

  deleteTransaction(transactionId: string): void{
    this.subscriptions.add(this.http.delete<ITransaction[]>("http://localhost:8080/api/v1/transaction/" + transactionId).subscribe());
    this.reloadPage();
  }

  private handleError(err: HttpErrorResponse): Observable<never> {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      errorMessage = `Server returned code: ${err.status}, errormessage is: ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(()=>errorMessage);
  }

  reloadPage() {
    window.location.reload();
 }
  



}
