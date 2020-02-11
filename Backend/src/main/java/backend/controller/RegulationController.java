package backend.controller;

import backend.model.Regulation;
import backend.repository.RegulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/regulation")
public class RegulationController {

    @Autowired
    private final RegulationRepository regulationRepository;

    public RegulationController(RegulationRepository regulationRepository){
        this.regulationRepository = regulationRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Regulation newRegulation){

        System.out.println(newRegulation.getRegulationID().toString() + " " + newRegulation.getSupervisoryBody() + " " + newRegulation.getSupervisoryCountry());
        Regulation createdRegulation = regulationRepository.save(newRegulation);
        if (createdRegulation == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdRegulation.getRegulationID())
                    .toUri();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Access-Control-Allow-Origin",
                    "*");
            responseHeaders.set("Access-Control-Allow-Methods",
                    "POST");
            responseHeaders.set("Access-Control-Allow-Headers",
                    "Content-Type, Authorization, Content-Length, X-Requested-With");

            return ResponseEntity.created(uri)
                    .headers(responseHeaders)
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
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Methods",
                "GET");
        responseHeaders.set("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");
        return new ResponseEntity<>(regulationRepository.findAll(), responseHeaders, HttpStatus.OK);
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

    //WEB OPERATIONS FOR UI
    @GetMapping(value = { "/incoming/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> countIncomingRegulationAPI(@PathVariable Long id) {
        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Methods",
                "GET");
        responseHeaders.set("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");*/
        return new ResponseEntity<>(regulationRepository.countDistinctByRegulationID(id),/* responseHeaders,*/ HttpStatus.OK);
    }

    @GetMapping(value = { "/no-supervisory-bodies/{supervisoryBody}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> countSupervisoryBodiesAPI(@PathVariable String supervisoryBody) {
        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Methods",
                "GET");
        responseHeaders.set("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");*/
        return new ResponseEntity<>(regulationRepository.countDistinctBySupervisoryBody(supervisoryBody),/* responseHeaders,*/ HttpStatus.OK);
    }

    @GetMapping(value = { "/go-live/{country}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> countGoLiveRegulationAPI(@PathVariable String country) {
        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Methods",
                "GET");
        responseHeaders.set("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Content-Length, X-Requested-With");*/
        return new ResponseEntity<>(regulationRepository.countDistinctBySupervisoryCountry(country),/* responseHeaders,*/ HttpStatus.OK);
    }

}