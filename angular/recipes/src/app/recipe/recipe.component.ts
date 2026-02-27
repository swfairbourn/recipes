import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { IRecipe } from '../models/recipe.model';
import { RecipesService } from '../services/recipes.service';
import { Pipe, PipeTransform } from '@angular/core';
import { FractionPipe } from '../pipes/fraction.pipe';

@Pipe({
  name: 'formatUnit',
  standalone: true
})
export class FormatUnitPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return value
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }
}

@Component({
  selector: 'app-recipe',
  standalone: true,
  imports: [CommonModule, FormatUnitPipe, FractionPipe],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.css'
})
export class RecipeComponent implements OnInit {

  recipe: IRecipe = {
    recipeId: '',
    title: '',
    rating: 0,
    directions: '',
    nationality: '',
    tags: [],
    ingredients: []
  };

  showDeleteConfirm: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private recipesService: RecipesService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');

      if (id !== null) {
        this.recipesService.getRecipeById(id).subscribe(
          (result: IRecipe) => {
            this.recipe = result;
            console.log('Recipe:', this.recipe);
          },
          (error) => {
            console.error('Error:', error);
          }
        );
      } else {
        console.error("Failed to get the recipe ID");
      }
    });
  }

  editRecipe(): void {
    this.router.navigate(['/add-recipe'], { state: { recipe: this.recipe } });
  }

  confirmDelete(): void {
    this.showDeleteConfirm = true;
  }

  deleteRecipe(): void {
    this.recipesService.deleteRecipeById(this.recipe.recipeId).subscribe({
      next: () => {
        this.showDeleteConfirm = false;
        this.router.navigate(['/recipes']);
      },
      error: (error) => {
        console.error('Error deleting recipe', error);
      }
    });
  }

  generatePdf(): void {
    const ingredientItems = this.recipe.ingredients
      .map(ing => {
        const amount = this.formatFraction(ing.amount);
        const unit = this.formatUnit(ing.unitOfMeasurement);
        return `<li>${amount} ${unit} ${ing.ingredient}</li>`;
      })
      .join('');

    const html = `
      <!DOCTYPE html>
      <html>
        <head>
          <title>${this.recipe.title}</title>
          <style>
            body {
              font-family: Arial, sans-serif;
              padding: 40px;
              color: #111;
              max-width: 800px;
              margin: 0 auto;
            }
            h1 {
              font-size: 28px;
              margin-bottom: 8px;
            }
            h2 {
              font-size: 16px;
              font-weight: bold;
              margin: 0 0 10px 0;
            }
            hr {
              border: none;
              border-top: 1px solid #ccc;
              margin: 16px 0;
            }
            ul {
              list-style: none;
              padding: 0;
              margin: 0;
            }
            ul li {
              font-size: 13px;
              padding: 3px 0;
            }
            p {
              font-size: 13px;
              line-height: 1.6;
              margin: 0;
              white-space: pre-wrap;
            }
            @media print {
              body { padding: 20px; }
            }
          </style>
        </head>
        <body>
          <h1>${this.recipe.title}</h1>
          <hr>

          <h2>Ingredients</h2>
          <ul>
            ${ingredientItems}
          </ul>
          <hr>

          <h2>Directions</h2>
          <p>${this.recipe.directions}</p>

          <script>
            window.onload = function() { window.print(); }
          </script>
        </body>
      </html>
    `;

    const tab = window.open('', '_blank');
    if (tab) {
      tab.document.write(html);
      tab.document.close();
    }
  }

  private formatFraction(value: number): string {
    if (!value) return '';
    const whole = Math.floor(value);
    const decimal = value - whole;

    const fractions: { [key: number]: string } = {
      0.125: '⅛', 0.25: '¼', 0.333: '⅓',
      0.375: '⅜', 0.5: '½', 0.625: '⅝',
      0.667: '⅔', 0.75: '¾', 0.875: '⅞'
    };

    const tolerance = 0.01;
    let fractionStr = '';
    for (const [decVal, symbol] of Object.entries(fractions)) {
      if (Math.abs(decimal - Number(decVal)) < tolerance) {
        fractionStr = symbol;
        break;
      }
    }

    if (fractionStr) {
      return whole > 0 ? `${whole} ${fractionStr}` : fractionStr;
    }
    return value % 1 === 0 ? `${whole}` : `${value}`;
  }

  private formatUnit(value: string): string {
    if (!value) return '';
    return value
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

}