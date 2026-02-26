import { Pipe, PipeTransform } from '@angular/core';
import { decimalToFraction } from '../utils/fraction.util';

@Pipe({
  name: 'fraction',
  standalone: true
})
export class FractionPipe implements PipeTransform {
  transform(value: number): string {
    if (value == null) return '';
    return decimalToFraction(value);
  }
}
