package br.com.fctecno.craftbeer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fctecno.craftbeer.api.controller.BeerController;
import br.com.fctecno.craftbeer.domain.model.Beer;
import br.com.fctecno.craftbeer.domain.repository.BeerRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CraftbeerApplicationTests {
	
	private Beer beer;
	
	@Autowired
	private BeerRepository repo;
	
	@Autowired
	private BeerController beerController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();
	}
	
	@Test
	public void testGETBeerController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beers"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void testGetByIdBeerController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beers/{id}", 2))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void testGetByIdNotFoundBeerController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beers/{id}", 1))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void testPostBeerController() throws Exception {
		beer = new Beer();
		beer.setName("Heineken");
		beer.setCategory("Premium");
		beer.setIngredients("Água, lúpulo e malte");
		beer.setPrice(new BigDecimal(15.00));
		beer.setAlcoholContent("5.0% vol.");
		
		MvcResult resultPost = mockMvc.perform(
        		MockMvcRequestBuilders.post("/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(beer)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
		
		
		Beer b = new ObjectMapper().readValue(resultPost.getResponse().getContentAsString(), Beer.class);
		beer.setId(b.getId());
	}
	
	@Test
	public void testPutBeerController() throws Exception {
		
		Optional<Beer> beerFind = repo.findById((long) 20);
		
		if (beerFind.isPresent()) {
			Beer beer = beerFind.get();
			beer.setCategory("Pilsen");
			beer.setAlcoholContent("Água, cerais diversos, malte");
			mockMvc.perform(
					MockMvcRequestBuilders.put("/beers/{id}", beer.getId())
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(asJsonString(beer)))
		            .andExpect(MockMvcResultMatchers.status().isOk());
		}
	}
	
	@Test
	public void testDeleteBeerController() throws Exception {
		mockMvc.perform(
	    		MockMvcRequestBuilders.delete("/beers/{id}", 20))
	            .andExpect(MockMvcResultMatchers.status().isNoContent());
	    
	}
	
	@Test
	public void testDeleteNotFoundBeerController() throws Exception {
		mockMvc.perform(
	    		MockMvcRequestBuilders.delete("/beers/{id}", 200))
	            .andExpect(MockMvcResultMatchers.status().isNotFound());
	    
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
