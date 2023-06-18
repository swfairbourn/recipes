import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { OverlayComponent } from './overlay.component';
import { OverlayService } from './overlay.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [
    OverlayComponent
  ],
  providers: [
    OverlayService
  ],
  exports: [
    OverlayComponent
  ]
})
export class OverlayModule { }
