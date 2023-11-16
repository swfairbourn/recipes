package com.fairbourn.recipes.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fairbourn.recipes.services.RecipesService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RecipesControllerTest {
	
	private MockMvc mockMvc;
	private RecipesController recipesController;
	@Mock private RecipesService recipesService;
	
	@Before
	public void before() {
		Mockito.when(recipesService.insertRecipe(Mockito.any())).thenReturn(true);
		recipesController = new RecipesController(recipesService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void insertRecipe_sucess() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/recipes/insertRecipe")
				.contentType(MediaType.APPLICATION_JSON);
//	            .content("{\"title\":\"Brownies\",\"rating\":5}");
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isCreated());
	}

}
