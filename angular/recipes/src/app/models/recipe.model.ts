import { IIngredient } from "./ingredient.model";

export interface IRecipe {
  recipeId: string;
  title: string;
  rating: number;
  directions: string;
  nationality: string;
  tags: string[];
  ingredients: IIngredient[];
}