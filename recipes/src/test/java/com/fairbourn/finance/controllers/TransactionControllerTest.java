package com.fairbourn.finance.controllers;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fairbourn.finance.services.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
	
	private MockMvc mockMvc;
	private TransactionController transactionController;
	@Mock private TransactionService transactionService;
	
	@Before
	public void before() {
		Mockito.when(transactionService.insertTransaction(Mockito.any())).thenReturn(true);
		transactionController = new TransactionController(transactionService);
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void insertTransaction_sucess() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/transaction/insertTransaction")
				.contentType(MediaType.APPLICATION_JSON)
	            .content("{\"merchant\":\"costco\",\"cost\":12.12}");
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isCreated());
	}

}
