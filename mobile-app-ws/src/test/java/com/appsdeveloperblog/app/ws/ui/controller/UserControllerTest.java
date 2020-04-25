package com.appsdeveloperblog.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userServiceImpl;
	
	UserDto userdto = new UserDto();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userdto.setId(3L);
		userdto.setFirstName("Shubham");
		userdto.setLastName("Raj");
		userdto.setUserId("jsgsghj54xahaj64554kgkhbbj");
		userdto.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userdto.setEmail("test@test.com");
		userdto.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userdto.setAddresses(getAddressDto());

	}

	@Test
	void testGetUser() {
		when(userServiceImpl.getUserByUserID(anyString())).thenReturn(userdto);
		
		UserRest userRest = userController.getUser("jsgsghj54xahaj64554kgkhbbj");
		
		assertNotNull(userRest);
		assertEquals(userdto.getFirstName(), userRest.getFirstName());
		assertTrue(userdto.getAddresses().size() == userRest.getAddresses().size());
	}
	
	
	private List<AddressDTO> getAddressDto()
	{
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setType("billing");
		addressDTO.setCity("Patna");
		addressDTO.setCountry("India");
		addressDTO.setPostalCode("800sa5");
		addressDTO.setStreetName("scjhsj");
		
		AddressDTO billingAddressDTO = new AddressDTO();
		billingAddressDTO.setType("billing");
		billingAddressDTO.setCity("Patna");
		billingAddressDTO.setCountry("India");
		billingAddressDTO.setPostalCode("800sa5");
		billingAddressDTO.setStreetName("scjhsj");
		
		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDTO);
		addresses.add(billingAddressDTO);
		
		return addresses;
	}
	

}
