import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TagsService } from './tags.service';

describe('TagsService', () => {
  let service: TagsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(TagsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Ensures no unexpected HTTP requests were made
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all tags via GET', () => {
    const mockTags = ['italian', 'quick', 'vegetarian'];

    service.getAllTags().subscribe(tags => {
      expect(tags).toEqual(mockTags);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/tags/getAllTags');
    expect(req.request.method).toBe('GET');
    req.flush(mockTags);
  });
});