package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.AgencyDto;
import ma.tna.ebanking.userservice.model.Agency;
import ma.tna.ebanking.userservice.services.AgencyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agency")
public class AgencyController {
    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }
    @GetMapping("")
    public HttpEntity<List<AgencyDto>> getAllAgencies(){
        return ResponseEntity.ok(agencyService.getAllAgencies().stream().map(AgencyDto::new).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public HttpEntity<AgencyDto> getAgencyById(@PathVariable("id") String agenyId){
        return ResponseEntity.ok(new AgencyDto(agencyService.getAgencyById(agenyId)));
    }
}
