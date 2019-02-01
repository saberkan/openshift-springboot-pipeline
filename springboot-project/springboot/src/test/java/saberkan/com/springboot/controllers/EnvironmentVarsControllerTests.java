package saberkan.com.springboot.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnvironmentVarsControllerTests {

	@Autowired
	EnvironmentVarsController controller;
	
	private MockMvc mockMvc;
	    
	@Before
	public void setup(){
	    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getVarsOK() throws Exception {
		this.mockMvc.perform(get("/rest/vars")).andExpect(status().isOk());
	}

	@Test
	public void getVarOK() throws Exception {
		this.mockMvc.perform(get("/rest/vars/environment"))
		.andExpect(status().isOk())
		.andExpect(content().string("test"));
	}
	
	@Test
	public void getVarNOT_FOUND() throws Exception {
		this.mockMvc.perform(get("/rest/vars/versionX"))
		.andExpect(status().isNotFound());
	}
}
