package Pegas.mapper;

import Pegas.dto.CompanyReadDTO;
import Pegas.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDTO>{
    @Override
    public CompanyReadDTO mapFrom(Company object) {
        return new CompanyReadDTO(object.getId(), object.getNameCompany());
    }
}
