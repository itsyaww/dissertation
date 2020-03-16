package backend.controller;

import backend.model.*;
import backend.repository.HandbookRepository;
import backend.repository.ModuleRepository;
import backend.repository.RegulationRepository;
import backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class MessageController {

    @Autowired
    private final ModuleRepository moduleRepository;

    @Autowired
    private final RegulationRepository regulationRepository;

    @Autowired
    private final HandbookRepository handbookRepository;

    @Autowired
    private final TeamRepository teamRepository;

    private CountDownLatch latch;

    public MessageController(ModuleRepository moduleRepository, HandbookRepository handbookRepository, RegulationRepository regulationRepository, TeamRepository teamRepository) {
        this.moduleRepository = moduleRepository;
        this.handbookRepository = handbookRepository;
        this.regulationRepository = regulationRepository;
        this.teamRepository = teamRepository;
        this.latch = new CountDownLatch(1);
    }

    //KAFKA OPERATIONS

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "message")
    public void receive(Message message) {
        System.out.println(message.toString());
        processMessage(message);
        latch.countDown();
    }

    private void processMessage(Message message) {

        String moduleCode = message.getModuleCode();

        Handbook handbook = getOrCreateHandbook();
        Module module = getOrCreateModule(handbook, moduleCode);
        addRegulations(module, message.getRegulation());
        updateTeams(module);

    }

    private Handbook getOrCreateHandbook() {
        String fcaHandbook = "FCA Handbook";
        if(handbookRepository.existsById(fcaHandbook)) {
            return handbookRepository.findById(fcaHandbook).get();
        }else
        {
            Handbook handbook = new Handbook();
            handbook.setRegulator(fcaHandbook);
            return handbookRepository.save(handbook);
        }
    }

    private Module getOrCreateModule(Handbook handbook, String moduleCode) {
        if(moduleRepository.existsById(moduleCode)) {
            return moduleRepository.findByModuleCode(moduleCode).get();
        }else
        {
            Module module = new Module();
            module.setModuleCode(moduleCode);
            module.setModuleName(moduleCode);
            module.setSupervisoryCountry("United Kingdom");
            module.setSupervisoryBody("FCA");

            moduleRepository.save(module);
            handbookRepository.save(handbook.addModule(module));

            return module;
        }
    }

    private void addRegulations(Module module, Regulation regulation) {
        module.addRegulation(regulation);
        moduleRepository.save(module);
    }

    private void updateTeams(Module module)
    {
        for (Team team: teamRepository.findAll()) {
            for (Module existingModule:team.getModules()) {
                if(existingModule.getModuleCode().equals(module.getModuleCode()))
                {
                    existingModule.setRegulations(module.getRegulations());
                    moduleRepository.save(existingModule);
                }
            }
        }
    }
}
