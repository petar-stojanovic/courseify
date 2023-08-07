import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandleService {
  private errorMessageSource = new BehaviorSubject<string | null>(null);
  errorMessage$ = this.errorMessageSource.asObservable();

  setErrorMessage(message: string) {
    this.errorMessageSource.next(message);
  }

  clearErrorMessage() {
    this.errorMessageSource.next(null);
  }
}
