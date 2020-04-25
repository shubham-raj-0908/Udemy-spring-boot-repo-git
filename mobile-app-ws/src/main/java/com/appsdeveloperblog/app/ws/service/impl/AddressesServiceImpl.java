package com.appsdeveloperblog.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressesService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;

@Service
public class AddressesServiceImpl implements AddressesService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;


	@Override
	public List<AddressDTO> getAddresses(String userId) {
		
		List<AddressDTO> returnValue = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();

		UserEntity storedUserDetails = userRepository.findByUserId(userId);
		if (storedUserDetails == null)
			return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(storedUserDetails);
		for(AddressEntity addressEntity : addresses)
		{
			returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
		}
		
		return returnValue;
	}


	@Override
	public AddressDTO getAddress(String addressId) {
		
		AddressDTO returnValue = new AddressDTO();
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity != null) 
		{
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(addressEntity, AddressDTO.class);
		}
		
		return returnValue;
	}

}
