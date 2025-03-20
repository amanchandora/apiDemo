package com.apidemo.service;

import com.apidemo.entity.Registration;
import com.apidemo.exceptions.ResourceNotFound;
import com.apidemo.payload.RegistrationDto;
import com.apidemo.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
    private RegistrationRepository registrationRepository;

    private ModelMapper modelMapper;

    public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper){
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    public RegistrationDto createRegistration(RegistrationDto registrationDto ){
        Registration registration = convertToEntity(registrationDto);
        Registration savedRegistration = registrationRepository.save(registration);
        return convertToDto(savedRegistration);
    }

    Registration convertToEntity(RegistrationDto registrationDto){
        Registration reg =modelMapper.map(registrationDto, Registration.class);
        //Registration registration = new Registration();
        //registration.setId(registrationDto.getId());
        //registration.setName(registrationDto.getName());
        //registration.setEmailId(registrationDto.getEmailId());
        //registration.setMobile(registrationDto.getMobile());
        return reg;
    }

    RegistrationDto convertToDto(Registration registration){
       //copy registration to dto
        RegistrationDto dto = modelMapper.map(registration, RegistrationDto.class);
        //RegistrationDto registrationDto = new RegistrationDto();
        //registrationDto.setId(registration.getId());
        //registrationDto.setName(registration.getName());
        //registrationDto.setEmailId(registration.getEmailId());
        //registrationDto.setMobile(registration.getMobile());
        return dto;
    }

    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }


    public void uppdatedRegistration(long id, RegistrationDto registrationDto) {
        Optional<Registration> opReg = registrationRepository.findById(id);
        if (opReg.isPresent()) {
            Registration reg = opReg.get();
            reg.setName(registrationDto.getName());
            reg.setEmailId(registrationDto.getEmailId());
            reg.setMobile(registrationDto.getMobile());
            registrationRepository.save(reg);
        }

    }

    public List<RegistrationDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort =sortDir.equals("asc")?Sort.by(Sort.Order.asc(sortBy)) :Sort.by(Sort.Order.desc(sortBy));
        Pageable page = PageRequest.of(pageNo,pageSize,sort);//pagination dont work with curdRepository it works with JpaRepository
        Page<Registration> all =  registrationRepository.findAll(page);
        List<Registration> registrations = all.getContent();
        System.out.println(page.getPageNumber());
        System.out.println(page.getPageSize());
        System.out.println(all.getTotalPages());
        System.out.println(all.isLast());
        System.out.println(all.isFirst());
        return registrations.stream().map((element) -> modelMapper.map(element, RegistrationDto.class)).collect(Collectors.toList());
       // return registrations.stream().map(registrationMapper::userToUserDTO).collect(Collectors.toList());
    }

    public Registration getRegistrationById(long  id) {
        Registration reg = registrationRepository.findById(id).orElseThrow(()->new ResourceNotFound("Record not found"));
        return reg;
    }

}
