package pro.sky.hwdb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hwdb.service.InfoService.InfoService;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public ResponseEntity<Integer> info() {
        return ResponseEntity.ok(infoService.getPort());
    }
}
