package backend.controller;

import backend.model.Division;
import backend.model.Firm;
import backend.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin//(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
@RequestMapping("/firm")
public class FirmController {
    @Autowired
    private final FirmRepository firmRepository;

    public FirmController(FirmRepository firmRepository) {
        this.firmRepository = firmRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Firm newFirm) {
        Firm createdFirm = firmRepository.save(newFirm);
        if (createdFirm == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdFirm.getClass())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdFirm);
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirm() {
        return new ResponseEntity<>(firmRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFirm(@PathVariable String id, @RequestBody Firm updatedFirm) {

        if (firmRepository.existsById(id)) {
            firmRepository.findById(id).map(existingFirm -> {

                existingFirm.setDivisions(updatedFirm.getDivisions());
                existingFirm.setFirmName(updatedFirm.getFirmName());

                return firmRepository.save(existingFirm);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        if (firmRepository.existsById(id)) {
            firmRepository.deleteById(id);
        }
    }

    @PutMapping(value = "/add/division", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  addDivision(@RequestBody Division division)
    {
        String firmId = "Goldman Sachs International";
        if(firmRepository.findByFirmName(firmId).isPresent()){
            Firm firm = firmRepository.findByFirmName(firmId).get();
            firm.addDivision(division);
            firmRepository.save(firm);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "{firmId}/delete/division/{buId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeDivision(@PathVariable String firmId, @PathVariable Long buId)
    {

        firmRepository.findById(firmId).ifPresent(firm -> {
            firm.removeDivision(buId);
            firmRepository.save(firm);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
