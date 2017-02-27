package com.epam.spring.core.domain.user;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang.StringUtils;

@Converter(autoApply = true)
public class RoleSetConverter implements AttributeConverter<Set<Role>, String> {	
	
	private static final String SEPERATOR = ",";
	
	@Override
	public String convertToDatabaseColumn(Set<Role> attribute) { 
		List<String> roles = attribute.stream().map(role -> role.getRole()).collect(Collectors.toList());
		return StringUtils.join(roles, SEPERATOR);   
	}
	  
	@Override
	public Set<Role> convertToEntityAttribute(String dbData) {
		List<String> roles = Arrays.asList(dbData.split(SEPERATOR));
		return roles.stream().map(role -> Role.valueOf(role)).collect(Collectors.toSet());
	}
  
}