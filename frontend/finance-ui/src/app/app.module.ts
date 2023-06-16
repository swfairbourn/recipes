import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { TransactionModule } from './transaction/transaction.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    TransactionModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
