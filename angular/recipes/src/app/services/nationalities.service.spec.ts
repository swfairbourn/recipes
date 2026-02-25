import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { NationalitiesService } from './nationalities.service';

describe('NationalitiesService', () => {
  let service: NationalitiesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(NationalitiesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all nationalities via GET', () => {
    const mockNationalities = ['Italian', 'Mexican', 'Japanese'];

    service.getAllNationalities().subscribe(nationalities => {
      expect(nationalities).toEqual(mockNationalities);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/nationalities/getAllNationalities');
    expect(req.request.method).toBe('GET');
    req.flush(mockNationalities);
  });
});