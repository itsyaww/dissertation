package backend.controller;

import backend.model.Regulation;
import backend.repository.RegulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/regulation")
public class RegulationController {

    @Autowired
    private final RegulationRepository regulationRepository;

    public RegulationController(RegulationRepository regulationRepository){
        this.regulationRepository = regulationRepository;
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Regulation newRegulation){

        Regulation createdRegulation = regulationRepository.save(newRegulation);
        if (createdRegulation == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdRegulation.getRegulationID())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdRegulation);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneRegulation(@PathVariable Long id){
        Optional<Regulation> regulation = regulationRepository.findById(id);
        if(regulation.isPresent())
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(regulation.get());
        }else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = { "", "/" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRegulation() {
        return new ResponseEntity<>(regulationRepository.findAll(),  HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRegulation(@PathVariable Long id,@RequestBody Regulation updatedRegulation){

        if(regulationRepository.existsById(id)) {
            regulationRepository.findById(id).map(existingRegulation -> {
                        existingRegulation.setSupervisoryBody(updatedRegulation.getSupervisoryBody());
                        existingRegulation.setSupervisoryCountry(updatedRegulation.getSupervisoryCountry());

                        return regulationRepository.save(existingRegulation);
                    });
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        if (regulationRepository.existsById(id))
        {
            regulationRepository.deleteById(id);
        }
    }

}