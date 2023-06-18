import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { OverlayService } from './overlay.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'new-transaction-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.css']
})
export class OverlayComponent {
  private overlaySubscription: Subscription;
  public overlayVisible = false;
  public merchantValue: string | undefined;
  public amountValue: number | undefined;
  public categoryValue: string | undefined;
  public dateValue: string | undefined;
  public subscriptions: Subscription = new Subscription();

  constructor(private http: HttpClient, private overlayService: OverlayService, private router: Router) {
    this.overlaySubscription = this.overlayService.overlayOpen$.subscribe(() => {
      console.log('Overlay opened!');
      this.overlayVisible = true;
    });
  }
  
  submitForm() {
    const headers = {'Content-Type': 'application/json'};
    const body = {"merchant": this.merchantValue,  "amount": this.amountValue, "category": this.categoryValue, "transactionDate": this.dateValue};
    this.subscriptions.add(this.http.post("http://localhost:8080/api/v1/transaction/insertTransaction", body, { headers }).subscribe());
    this.closeOverlayAndReloadTransactions();
  }

  closeOverlayAndReloadTransactions() {
    this.overlayVisible = false;
    this.router.navigateByUrl('/');
  }

  roundAmount() {
    if (this.amountValue !== undefined) {
      this.amountValue = parseFloat(this.amountValue.toFixed(2));
    }
  }
  

  ngOnDestroy() {
    this.overlaySubscription.unsubscribe();
    this.subscriptions.unsubscribe();
  }
}
