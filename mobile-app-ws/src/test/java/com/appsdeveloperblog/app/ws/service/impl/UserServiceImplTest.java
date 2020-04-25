package com.appsdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;


	UserEntity userEntity = new UserEntity();
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	
		userEntity.setId(3L);
		userEntity.setFirstName("Shubham");
		userEntity.setLastName("Raj");
		userEntity.setUserId("jsgsghj54xahaj");
		userEntity.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userEntity.setEmail("test@test.com");
		userEntity.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userEntity.setAddresses(getAddressEntity());

	}

	@Test
	void testGetUser() {
		
		
		when(userRepository.findByEmail( anyString() ) ).thenReturn(userEntity);
		
		UserDto userDto = userServiceImpl.getUser("test2@test.com");
		
		assertNotNull(userDto);
		assertEquals("Shubham", userDto.getFirstName());
	}
	
	
	
	@Test
	void testGetUser_UsernameNotFoundException() {
		
		when(userRepository.findByEmail( anyString() ) ).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class,
				
				() -> {
					userServiceImpl.getUser("test211@test.com");
				}
				
				);
	}
	
	
	@Test
	void testCreateUser_CreateServiceException() {
		
		when(userRepository.findByEmail( anyString() ) ).thenReturn(userEntity);
		
		UserDto userdto = new UserDto();
		userdto.setAddresses(getAddressDto());
		userdto.setFirstName("Shubham");
		userdto.setLastName("Raj");
		userdto.setPassword("123456789");
		userdto.setEmail("test@test.com");
		
		assertThrows(UserServiceException.class,
				
				() -> {
					userServiceImpl.createUser(userdto);
				}
				
				);
	}
	
	
	@Test
	void testCreateUser() {
		
		UserEntity userEntity = new UserEntity();
		userEntity.setId(3L);
		userEntity.setFirstName("Shubham");
		userEntity.setLastName("Raj");
		userEntity.setUserId("jsgsghj54xahaj");
		userEntity.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userEntity.setEmail("test@test.com");
		userEntity.setEncryptedPassword("jhgdasgdasdkaufh6845s4sah");
		userEntity.setAddresses(getAddressEntity());

		
		when(userRepository.findByEmail( anyString() ) ).thenReturn(null);
		when(utils.generateAddressId( anyInt() )).thenReturn("kDNNanajn868x4");
		when(utils.generateUserId( anyInt() )).thenReturn("jsgsghj54xahaj");
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("jhgdasg65456ah");
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		
		
		UserDto userdto = new UserDto();
		userdto.setAddresses(getAddressDto());
		userdto.setFirstName("Shubham");
		userdto.setLastName("Raj");
		userdto.setPassword("123456789");
		userdto.setEmail("test@test.com");
		
		
		UserDto storedUserDetails = userServiceImpl.createUser(userdto);
		
		
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
		verify(bCryptPasswordEncoder, times(1)).encode("123456789");
		verify(utils,times(storedUserDetails.getAddresses().size())).generateAddressId(30);
		verify(userRepository, times(1)).save(any(UserEntity.class));
		
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
	
	
	private List<AddressEntity> getAddressEntity()
	{
		List<AddressDTO> addressesDto = getAddressDto();
		
		java.lang.reflect.Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
		return new ModelMapper().map(addressesDto, listType);
	}

}
