import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { FiltersComponent } from './filters.component';
import { TagsService } from '../../services/tags.service';
import { NationalitiesService } from '../../services/nationalities.service';
import { RecipeCriteria } from '../../models/recipe-criteria.model';

describe('FiltersComponent', () => {
  let component: FiltersComponent;
  let fixture: ComponentFixture<FiltersComponent>;
  let tagsServiceSpy: jasmine.SpyObj<TagsService>;
  let nationalitiesServiceSpy: jasmine.SpyObj<NationalitiesService>;

  beforeEach(async () => {
    tagsServiceSpy = jasmine.createSpyObj('TagsService', ['getAllTags']);
    nationalitiesServiceSpy = jasmine.createSpyObj('NationalitiesService', ['getAllNationalities']);

    tagsServiceSpy.getAllTags.and.returnValue(of(['pasta', 'quick', 'vegetarian']));
    nationalitiesServiceSpy.getAllNationalities.and.returnValue(of(['Italian', 'Mexican', 'Japanese']));

    await TestBed.configureTestingModule({
      imports: [FiltersComponent],
      providers: [
        { provide: TagsService, useValue: tagsServiceSpy },
        { provide: NationalitiesService, useValue: nationalitiesServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load tags on init', () => {
    expect(tagsServiceSpy.getAllTags).toHaveBeenCalled();
    expect(component.tags).toEqual(['pasta', 'quick', 'vegetarian']);
  });

  it('should load nationalities on init', () => {
    expect(nationalitiesServiceSpy.getAllNationalities).toHaveBeenCalled();
    expect(component.nationalities).toEqual(['Italian', 'Mexican', 'Japanese']);
  });

  it('should populate rating criteria on init', () => {
    expect(component.recipeCriteria.ratings.length).toBe(5);
    expect(component.recipeCriteria.ratings[0].value).toBe(5);
    expect(component.recipeCriteria.ratings[4].value).toBe(1);
  });

  it('should populate nationality criteria from loaded nationalities', () => {
    expect(component.recipeCriteria.nationalities.length).toBe(3);
    expect(component.recipeCriteria.nationalities[0].value).toBe('Italian');
  });

  it('should populate tag criteria from loaded tags', () => {
    expect(component.recipeCriteria.tags.length).toBe(3);
    expect(component.recipeCriteria.tags[0].value).toBe('pasta');
  });

  it('should emit updated criteria when a rating changes', () => {
    spyOn(component.updateFiltersEvent, 'emit');
    component.loadRatingCriteria();
    const event = { target: { value: 5, checked: true } };
    component.onRatingChange(event);
    expect(component.updateFiltersEvent.emit).toHaveBeenCalledWith(component.recipeCriteria);
  });

  it('should emit updated criteria when a nationality changes', () => {
    spyOn(component.updateFiltersEvent, 'emit');
    component.loadNationalitiesCriteria();
    const event = { target: { value: 0, checked: true } };
    component.onNationalityChange(event);
    expect(component.updateFiltersEvent.emit).toHaveBeenCalledWith(component.recipeCriteria);
  });

  it('should emit updated criteria when a tag changes', () => {
    spyOn(component.updateFiltersEvent, 'emit');
    component.loadTagsCriteria();
    const event = { target: { value: 0, checked: true } };
    component.onTagChange(event);
    expect(component.updateFiltersEvent.emit).toHaveBeenCalledWith(component.recipeCriteria);
  });

  it('should emit updated criteria when updateFilters() is called directly', () => {
    spyOn(component.updateFiltersEvent, 'emit');
    component.updateFilters();
    expect(component.updateFiltersEvent.emit).toHaveBeenCalledWith(component.recipeCriteria);
  });
});