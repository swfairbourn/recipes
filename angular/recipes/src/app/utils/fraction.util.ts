export function decimalToFraction(decimal: number): string {
  if (decimal === 0) return '0';

  const wholePart = Math.floor(decimal);
  const decimalPart = decimal - wholePart;

  if (decimalPart === 0) return `${wholePart}`;

  const tolerance = 1.0E-6;
  let numerator = 1;
  let denominator = 1;
  let bestNumerator = 1;
  let bestDenominator = 1;
  let bestError = Math.abs(decimalPart - numerator / denominator);

  for (denominator = 1; denominator <= 64; denominator++) {
    numerator = Math.round(decimalPart * denominator);
    const error = Math.abs(decimalPart - numerator / denominator);
    if (error < bestError) {
      bestError = error;
      bestNumerator = numerator;
      bestDenominator = denominator;
    }
    if (error < tolerance) break;
  }

  const fractionStr = `${bestNumerator}/${bestDenominator}`;
  return wholePart > 0 ? `${wholePart} ${fractionStr}` : fractionStr;
}

export function fractionToDecimal(input: string): number | null {
  if (!input || !input.trim()) return null;

  const trimmed = input.trim();

  // Plain number (integer or decimal)
  if (!isNaN(Number(trimmed))) return Number(trimmed);

  // Mixed number e.g. "1 1/2"
  const mixedMatch = trimmed.match(/^(\d+)\s+(\d+)\/(\d+)$/);
  if (mixedMatch) {
    const whole = parseInt(mixedMatch[1]);
    const numerator = parseInt(mixedMatch[2]);
    const denominator = parseInt(mixedMatch[3]);
    if (denominator === 0) return null;
    return whole + numerator / denominator;
  }

  // Simple fraction e.g. "3/4"
  const fractionMatch = trimmed.match(/^(\d+)\/(\d+)$/);
  if (fractionMatch) {
    const numerator = parseInt(fractionMatch[1]);
    const denominator = parseInt(fractionMatch[2]);
    if (denominator === 0) return null;
    return numerator / denominator;
  }

  return null;
}
