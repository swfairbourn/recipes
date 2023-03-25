import { Component } from '@angular/core';

@Component({
  selector: 'finance-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'finance-ui';
  merchantValue = '';
  costValue = '';

  async submitTransaction(merchant: string, cost: string): Promise<void> {
    const response = await fetch("http://localhost:8080/api/v1/transaction/insertTransaction", {
      method: 'POST',
      body: JSON.stringify({"merchant": merchant,  "cost": cost}),
      headers: {'Content-Type': 'application/json'} //TODO: Do we need authorization header here? 'Authorization': 'key='+API_KEY 
    });
    
    if (!response.ok) { 
        console.error("Error");
    }
    // else if (response.statusCode >= 400) {
    //     console.error('HTTP Error: '+response.statusCode+' - '+response.statusMessage);
    // }
    else{
      this.merchantValue = '';
      this.costValue = '';
    }
  }



}
