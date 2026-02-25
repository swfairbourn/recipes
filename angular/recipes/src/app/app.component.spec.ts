import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { Component } from '@angular/core';

import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { RecipesComponent } from './recipes/recipes.component';

@Component({ selector: 'app-sidebar', standalone: true, template: '' })
class MockSidebarComponent {}

@Component({ selector: 'app-recipes', standalone: true, template: '' })
class MockRecipesComponent {}

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, RouterModule.forRoot([])]
    })
    .overrideComponent(AppComponent, {
      remove: { imports: [SidebarComponent, RecipesComponent] },
      add: { imports: [MockSidebarComponent, MockRecipesComponent] }
    })
    .compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'recipes' title`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('recipes');
  });
});