package com.fnmc.backend.controleur;

import com.fnmc.backend.entity.Comps;
import com.fnmc.backend.entity.UserMc;
import com.fnmc.backend.service.StructService;
import com.fnmc.backend.service.UserMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("siteUser")
@CrossOrigin(origins = "http://localhost:3000")
public class SiteContoleur {
//todo refreche  + if doublons rename to (1)= requette avc juste les noms et ID, mis  a jours, puis renvoyé
    @Autowired
    UserMcService userMcService;

    @Autowired
    StructService service;



    @GetMapping("me")
    @PreAuthorize("hasAuthority('PLAYER')")//todo use same for server;(mod prod/admin)
    public ResponseEntity getUserMc(Authentication authentication){
        return new ResponseEntity<>( userMcService.getUserByName(authentication.getName()), HttpStatus.OK);
    }


    @GetMapping("myStr")
   // @CrossOrigin(origins = "*")
  @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity getMyStruct(Authentication authentication){
      UserMc u =userMcService.getUserByName(authentication.getName());
      if (u!=null){
          userMcService.reOrgenize(u);
          return new ResponseEntity<>(u.getStructIn(),HttpStatus.OK);
      }
        else
          return new ResponseEntity<>("something wrong with this request",HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @PostMapping("reName/{n}")//doit être un path
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity reNameStruct(@PathVariable String n,@RequestBody Map<String,String> body, Authentication authentication){

      if(service.renameComp(authentication.getName(),n,body.get("named"))){
         if (userMcService.updateStrucIn(authentication.getName(),body.get("id"),body.get("named")))
              return new ResponseEntity("le nom est bien modifier !",HttpStatus.OK);
      }
        return new ResponseEntity("someThing reong is appending", HttpStatus.INTERNAL_SERVER_ERROR);
    }


@DeleteMapping("delete/{n}/{id}")
@PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity deleteStruct(@PathVariable String n,@PathVariable Integer id,  Authentication authentication){
    if(userMcService.removeOne(authentication.getName(), id)) {
        if (service.removeComp(authentication.getName(), n))
            return ResponseEntity.ok(n + " est bien supprimer");
        return new ResponseEntity("something not good is appening or not found comp",HttpStatus.OK);
    }
    return new ResponseEntity("not worcks ",HttpStatus.NOT_FOUND);
}

    @PostMapping("public/{n}")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity pubStruct(@PathVariable String id, Authentication authentication){
    try{
        Comps comps= service.Publich(authentication.getName(), id);
        return ResponseEntity.ok(id+"est bien publier");
        }catch (Exception e){
        System.out.println("redef : "+e);
        return ResponseEntity.badRequest().build();
    }


        }

    @GetMapping("getMyStructByName/{name}")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity getMyStructByName(@PathVariable String name,Authentication authentication){
        Comps op=service.getuniqueC(authentication.getName(), name);//.GreyCactus67220
        if (op==null)
            return ResponseEntity.ofNullable("not found user/struct");
        return ResponseEntity.ok(op);
    }

}
