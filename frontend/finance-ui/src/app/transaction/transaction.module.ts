import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TransactionComponent } from './transaction.component';

@NgModule({
  declarations: [
    TransactionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  exports: [
    TransactionComponent
  ]
})
export class TransactionModule { }
