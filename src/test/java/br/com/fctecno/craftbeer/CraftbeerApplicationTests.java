package br.com.fctecno.craftbeer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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

import br.com.fctecno.craftbeer.api.controller.BeerController;
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
	public void testExistsBeer() {
		
		long generatedLong = new Random().nextLong();
		assertThat(repo.existsById(generatedLong)).isFalse();
		
	}
	
}
