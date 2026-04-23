package com.example.njug_spring_crud_project.mappers;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import com.example.njug_spring_crud_project.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmployeeMapper {
    EmployeeResponseDto toDto(Employee employee);

    Employee toEntity(EmployeeRequestDto requestDto);

    void update(EmployeeRequestDto requestDto, @MappingTarget Employee employee);
}
