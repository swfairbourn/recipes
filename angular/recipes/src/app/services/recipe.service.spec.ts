import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { RecipeService } from './recipe.service';

describe('RecipeService', () => {
  let service: RecipeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(RecipeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all units of measurement via GET', () => {
    const mockUnits: Record<string, string> = { g: 'Grams', kg: 'Kilograms', ml: 'Millilitres' };

    service.getAllUnitsOfMeasurement().subscribe(units => {
      expect(units as unknown).toEqual(mockUnits);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/recipe/getAllUnitsOfMeasurement');
    expect(req.request.method).toBe('GET');
    req.flush(mockUnits);
  });
});