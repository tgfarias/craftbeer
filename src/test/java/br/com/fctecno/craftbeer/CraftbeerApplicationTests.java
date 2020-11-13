package br.com.fctecno.craftbeer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
		Beer b = new Beer();
		b.setName("Heineken");
		b.setCategory("Premium");
		b.setIngredients("Água, lúpulo e malte");
		b.setPrice(new BigDecimal(15.00));
		b.setAlcoholContent("5.0% vol.");

        mockMvc.perform(
        		MockMvcRequestBuilders.post("/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(b)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testDeleteBeerController() throws Exception {
	    
	    mockMvc.perform(
	    		MockMvcRequestBuilders.delete("/users/{id}", 9))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	    
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
