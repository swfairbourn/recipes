import { NationalityModel } from "./nationality.model";
import { RatingModel } from "./rating.model";
import { TagModel } from "./tag.model";

export class RecipeCriteria {
    ratings: RatingModel[];
    nationalities: NationalityModel[];
    tags: TagModel[];

    constructor(ratings: RatingModel[], nationalities: NationalityModel[], tags: TagModel[]) {
        this.ratings = ratings;
        this.nationalities = nationalities;
        this.tags = tags;
      }
  }