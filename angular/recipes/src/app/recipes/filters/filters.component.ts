import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TagsService } from '../../services/tags.service';
import { RecipeCriteria } from '../../models/recipe-criteria.model';
import { TagModel } from '../../models/tag.model';
import { NationalityModel } from '../../models/nationality.model';
import { RatingModel } from '../../models/rating.model';
import { NationalitiesService } from '../../services/nationalities.service';

@Component({
  selector: 'app-filters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filters.component.html',
  styleUrl: './filters.component.css'
})
export class FiltersComponent {

  @Input() recipeCriteria: RecipeCriteria = new RecipeCriteria([], [], []);
  @Output() updateFiltersEvent = new EventEmitter<RecipeCriteria>();
  nationalities: String[] = [];
  tags: String[] = [];


  constructor(private tagsService: TagsService, private nationalitiesService: NationalitiesService) {}

  ngOnInit() {
    this.getNationalities();
    this.getTags();
    this.loadRatingCriteria();
  }

  loadRatingCriteria() {
    for (var i = 5; i > 0; i--) {
      this.recipeCriteria.ratings.push(new RatingModel(i, true, i))
    }
  }

  loadNationalitiesCriteria() {
    this.nationalities.forEach((value, index) => {
      this.recipeCriteria.nationalities.push(new NationalityModel(index, true, value))
    });
  }

  loadTagsCriteria() {
    this.tags.forEach((value, index) => {
      this.recipeCriteria.tags.push(new TagModel(index, true, value))
    });
  }

  onRatingChange($event:any) {
    const id = $event.target.value;
    const isSelected = $event.target.checked;
    this.recipeCriteria.ratings.map((r) => {
      if (r.id == id) {
        r.selected = isSelected;
        return r;
      }
      return r;
    });
  }

  onNationalityChange($event:any) {
    const id = $event.target.value;
    const isSelected = $event.target.checked;
    this.recipeCriteria.nationalities.map((n) => {
      if (n.id == id) {
        n.selected = isSelected;
        return n;
      }
      return n;
    });
  }

  onTagChange($event:any) {
    const id = $event.target.value;
    const isSelected = $event.target.checked;
    this.recipeCriteria.tags.map((t) => {
      if (t.id == id) {
        t.selected = isSelected;
        return t;
      }
      return t;
    });
  }

  updateFilters() {
    this.updateFiltersEvent.emit(this.recipeCriteria);
  }

  getNationalities() {
    this.nationalitiesService.getAllNationalities().subscribe(
      (data: any[]) => {
        this.nationalities = data;
        this.loadNationalitiesCriteria();
      },
      (error) => {
        console.error('Error fetching tags:', error);
      }
    );
  }

  getTags() {
    this.tagsService.getAllTags().subscribe(
      (data: any[]) => {
        this.tags = data;
        this.loadTagsCriteria();
      },
      (error) => {
        console.error('Error fetching tags:', error);
      }
    );
  }

}
