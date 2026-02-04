package com.fnmc.backend.controleur;

import com.fnmc.backend.dao.CompsDao;
import com.fnmc.backend.entity.Comps;
import com.fnmc.backend.entity.UserMc;
import com.fnmc.backend.service.StructService;
import com.fnmc.backend.service.UserMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("opi")
public class McControleur {//todo all ready coo


    @Autowired
    UserMcService userMcService;

    @Autowired
    StructService structService;

    @GetMapping("/Users")
    public ResponseEntity getAllUserMc() {
        Optional<List<String>> mc = userMcService.getAllUser();
        if (mc.isPresent()) {
            return ResponseEntity.ok(mc);
        }
        return new ResponseEntity("Nobody Was here before 24h", HttpStatus.NOT_FOUND);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('SERVER')")
    public ResponseEntity addUser(@RequestBody UserMc userMc) {
         return ResponseEntity.ok(userMcService.createUser(userMc));
    }


    @PreAuthorize("hasAuthority('SERVER')")
    @PostMapping("struct")
    public ResponseEntity addStructComps(@RequestBody CompsDao struct) {

        Optional<Comps> res= structService.updateStruct(struct);
        if (res.isEmpty()){
            return  ResponseEntity.ofNullable("une erreur interne");
        }
        userMcService.addStr(struct.getPlayer(), struct.getNamed(),res.get().getId());
        return ResponseEntity.ok(res.get() );
    }

    //todo if exsite rename? getByUserCooOnServer plus tard
    // cread anew map
}
