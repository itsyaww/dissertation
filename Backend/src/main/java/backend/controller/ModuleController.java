package backend.controller;

import backend.model.Module;
import backend.model.Regulation;
import backend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@CrossOrigin//(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private final ModuleRepository moduleRepository;

    private CountDownLatch latch;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
        this.latch = new CountDownLatch(1);
    }

    //KAFKA OPERATIONS

    /*public CountDownLatch getLatch() {
        return latch;
    }*/

    /*@KafkaListener(topics = "regulation")
    public void receive(Regulation regulation) {
        System.out.println(regulation.toString());
        //processRegulation(regulation);
        latch.countDown();
    }

    @KafkaListener(topics = "message")
    public void receive(Message message) {
        System.out.println(message.toString());
        processMessage(message);
        latch.countDown();
    }

    private void processMessage(Message message) {

        String moduleCode = message.getModuleCode();

        if(moduleRepository.existsById(moduleCode)) {
            Module module = moduleRepository.findByModuleCode(moduleCode).get();
            module.addRegulation(message.getRegulation());
            moduleRepository.save(module);
        }else
        {
            Module module = new Module();
            module.setModuleCode(message.getModuleCode());
            module.setModuleName("Test");
            module.setSupervisoryCountry("United Kingdom");
            module.setSupervisoryBody("FCA");
            module.addRegulation(message.getRegulation());
            moduleRepository.save(module);
        }

    }*/

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Module newModule) {
        Module createdModule = moduleRepository.save(newModule);
        if (createdModule == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdModule.getModuleCode())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdModule);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneModule(@PathVariable String id) {
        Optional<Module> Module = moduleRepository.findById(id);
        if (Module.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Module.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllModules() {
        return new ResponseEntity<>(moduleRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateModule(@PathVariable String id, @RequestBody Module updatedModule) {

        if (moduleRepository.existsById(id)) {
            moduleRepository.findById(id).map(existingModule -> {

                existingModule.setModuleCode(updatedModule.getModuleCode());
                existingModule.setModuleName(updatedModule.getModuleName());
                existingModule.setRegulations(updatedModule.getRegulations());
                existingModule.setSupervisoryBody(updatedModule.getSupervisoryBody());
                existingModule.setSupervisoryCountry(updatedModule.getSupervisoryCountry());

                return moduleRepository.save(existingModule);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        if (moduleRepository.existsById(id)) {
            moduleRepository.deleteById(id);
        }
    }

    @PutMapping(value = "/add/regulation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  addRegulation(@RequestBody String moduleCode, @RequestBody Regulation regulation)
    {

        moduleRepository.findById(moduleCode).ifPresent(module -> {
            module.addRegulation(regulation);
            moduleRepository.save(module);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "{moduleCode}/delete/regulation/{regulationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeRegulation(@PathVariable String moduleCode, @PathVariable Long regulationId)
    {

        moduleRepository.findById(moduleCode).ifPresent(module -> {
            module.removeRegulation(regulationId);
            moduleRepository.save(module);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
