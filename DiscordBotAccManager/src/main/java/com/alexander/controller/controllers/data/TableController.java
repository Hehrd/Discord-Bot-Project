package com.alexander.controller.controllers.data;

import com.alexander.controller.model.request.CreateDatabaseRequestDTO;
import com.alexander.controller.model.request.CreateTableRequestDTO;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.controller.model.response.TableResponseDTO;
import com.alexander.service.data.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResponse> getTables(@RequestHeader("Authorization") String accessToken, Pageable pageable) {
        PagedResponse<TableResponseDTO> tablePage = tableService.getUserTables(accessToken, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tablePage);
    }

    @RequestMapping(value = "/rows", method = RequestMethod.GET)
    public ResponseEntity<String> getRows(@RequestHeader("Authorization") String accessToken,
                                          @RequestParam("container") String container,
                                          @RequestParam("database") String database,
                                          @RequestParam("table") String table,
                                          Pageable pageable) {
        String rows = tableService.getRows(accessToken, container, database, table, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rows);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTable(@RequestBody CreateTableRequestDTO createTableRequestDTO, @RequestHeader("Authorization") String jwt) {
        tableService.addTable(createTableRequestDTO, jwt);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully created container!");
    }
}
