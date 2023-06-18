import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OverlayService {
  private overlayOpenSubject = new Subject<void>();

  overlayOpen$ = this.overlayOpenSubject.asObservable();

  openOverlay() {
    this.overlayOpenSubject.next();
  }
}
