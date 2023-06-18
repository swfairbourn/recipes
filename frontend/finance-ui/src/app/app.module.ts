import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { TransactionModule } from './transaction/transaction.module';
import { OverlayModule } from './overlay/overlay.module';
import { OverlayService } from './overlay/overlay.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CommonModule,
    OverlayModule,
    TransactionModule,
  ],
  providers: [
    OverlayService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
