import { IIngredient } from "./ingredient.model";

export interface IRecipe {
  title: string;
  rating: number;
  directions: string;
  nationality: string;
  tags: string[];
  ingredients: IIngredient[];
}