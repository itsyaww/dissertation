package regulationService.fileUploader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import regulationService.storage.FileSystemStorageService;
import regulationService.storage.StorageFileNotFoundException;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@RestController
//@RequestMapping("/files")
@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
public class FileUploadController {

    private final FileSystemStorageService storageService;

    @Autowired
    public FileUploadController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadProcessedFiles().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveProcessedFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/root/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveProcessedFile(@PathVariable String filename) {

        Resource file = storageService.loadProcessedFileAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listProcessedFiles() throws IOException {

        List<String> list =  storageService.loadProcessedFiles()
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/")
    public String handleFileUploadUI(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        String newFileName = storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @PostMapping(value="/files/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> handleFileUploadAPI(@RequestParam("files[]") MultipartFile[] files) {
        ArrayList<String> diskFileNames = new ArrayList<>();
        for(MultipartFile file: files)
        {
            System.out.println("Saving " + file.getName() + " ...");
            String newFileName = storageService.store(file);
            diskFileNames.add(newFileName);
        }
        return ResponseEntity.status(HttpStatus.OK).body(diskFileNames);

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}