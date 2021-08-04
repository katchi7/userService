package ma.tna.ebanking.userservice.controllers;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.dtos.AgencyDto;
import ma.tna.ebanking.userservice.exceptions.AgencyServiceException;
import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.services.AgencyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agency")
@Log4j2
public class AgencyController {
    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }
    @GetMapping("")
    public HttpEntity<List<AgencyDto>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgenciesInfos().stream().map(AgencyDto::new).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public HttpEntity<AgencyDto> getAgencyById(@PathVariable("id") String agenyId){
        return ResponseEntity.ok(new AgencyDto(agencyService.getAgencyById(agenyId)));
    }
    @PostMapping("")
    public HttpEntity<AgencyDto> updateAgency(@RequestBody @Valid AgencyDto agencyDto, Errors errors) {
        log.info(agencyDto);
        if(errors.hasErrors()) throw new InvalidParameterException("Body is not valid");
        return ResponseEntity.ok(new AgencyDto(agencyService.updateAgency(agencyDto.asAgency())));
    }
}
