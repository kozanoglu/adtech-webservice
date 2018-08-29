package com.kozanoglu.adtech.controller;

import com.kozanoglu.adtech.dto.request.ClickRequestDTO;
import com.kozanoglu.adtech.dto.request.DeliveryRequestDTO;
import com.kozanoglu.adtech.dto.request.InstallRequestDTO;
import com.kozanoglu.adtech.service.IngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
public class IngestionController {

    private IngestionService ingestionService;

    @Autowired
    public
    IngestionController(final IngestionService ingestionService)
    {
        this.ingestionService = ingestionService;
    }

    /**
     *
     * @param deliveryRequestDTO
     * @return
     */
    @PostMapping(value = "/delivery", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity postDelivery(@RequestBody final DeliveryRequestDTO deliveryRequestDTO) {
        ingestionService.saveDelivery(deliveryRequestDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @param clickRequestDTO
     * @return
     */
    @PostMapping(value = "/click", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity postClick(@RequestBody final ClickRequestDTO clickRequestDTO) {
        ingestionService.saveClick(clickRequestDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @param installRequestDTO
     * @return
     */
    @PostMapping(value = "/install", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity postInstall(@RequestBody final InstallRequestDTO installRequestDTO) {
        ingestionService.saveInstall(installRequestDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}